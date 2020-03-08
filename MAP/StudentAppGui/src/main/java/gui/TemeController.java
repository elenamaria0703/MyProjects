package gui;


import domain.MailSender;
import domain.Student;
import domain.Tema;
import exceptions.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.TemaService;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static service.StudentService.studentRepo;


public class TemeController implements PropertyChangeListener {
    TemaService temaService;
    Stage stageTeme;
    private static Integer studId;
    private static Integer temaId;
    private Boolean student=false;
    ObservableList<Tema> modelTeme= FXCollections.observableArrayList();

    @FXML
    TableView<Tema> tableTeme;
    @FXML
    TableColumn<Tema,Integer> columnStartWeek;
    @FXML
    TableColumn<Tema,Integer> columnDeadline;
    @FXML
    TableColumn<Tema,Integer> columnId;
    @FXML
    TableColumn<Tema,String> columnDescription;
    @FXML
    TextField txtDescription;
    @FXML
    TextField txtDeadline;
    @FXML
    TextField txtSearch;
    @FXML
    ProgressBar progressBar;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    Text stateTxt;

    public void setService(TemaService temaService,Stage dialogStage) {
        this.temaService = temaService;
        this.stageTeme = dialogStage;
        initModel();
        temaService.addChangeListener(this);
        txtDeadline.setEditable(false);
        loadSelected();
    }
    @FXML
    public void initialize(){
        columnDeadline.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("deadlineWeek"));
        columnId.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("id"));
        columnStartWeek.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("startWeek"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<Tema,String>("descriere"));
        txtSearch.textProperty().addListener((observable,oldvalue,newvalue)->handleFilter());
        tableTeme.setItems(modelTeme);
    }
    public void handleFilter(){
        Predicate<Tema> filterDescription=t->t.getDescriere().startsWith(txtSearch.getText());
        modelTeme.setAll(StreamSupport.stream(temaService.getAll().spliterator(),false)
                .filter(filterDescription)
                .collect(Collectors.toList()));
    }
    public void handleBack(ActionEvent actionEvent){
        Stage owner= (Stage) stageTeme.getOwner();
        owner.show();
        this.stageTeme.close();
    }
    private void initializeId(){
        try {
            BufferedReader reader= Files.newBufferedReader(Paths.get("./src/main/resources/files/getId.txt"));
            String id=reader.readLine();
            Integer idStud=Integer.parseInt(id.split(" ")[0]);
            Integer idTema=Integer.parseInt(id.split(" ")[1]);
            this.studId=idStud;
            this.temaId=idTema;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadSelected(){
        tableTeme.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection,newSelection)->{
            if(newSelection==null) {
                tableTeme.getSelectionModel().clearSelection();
            }
            else{
                txtDeadline.setEditable(true);
                txtDeadline.setText(newSelection.getDeadlineWeek().toString());
                txtDescription.setText(newSelection.getDescriere());
            }
        });
    }
    private void retainId()  {
        try {
            BufferedWriter writer=Files.newBufferedWriter(Paths.get("./src/main/resources/files/getId.txt"));
            String str=this.studId.toString()+" "+this.temaId.toString();
            writer.write(str);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initModel(){
        List<Tema> listTeme= StreamSupport.stream(temaService.getAll().spliterator(),false).collect(Collectors.toList());
        modelTeme.clear();
        modelTeme.setAll(listTeme);

    }
    public void handleUpdateTema(ActionEvent actionEvent){
        if(student.equals(true)) {
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        Tema toUpdate=tableTeme.getSelectionModel().getSelectedItem();
        try{
            if(txtDeadline.getText().equals("") || txtDeadline.getText().equals("")) MessageAlert.showErrorMessage(null,"Campurile nu sunt completate!");
            String descriere=txtDescription.getText();
            Integer deadline=Integer.parseInt(txtDeadline.getText());
            temaService.update(toUpdate,descriere,deadline);
            String text="\nS-a modificat o tema: "+" "+descriere+" are termenul de predare "+deadline.toString();
            String subject="Update Tema";
            sendEmails(subject,text);
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMsg());
        }

    }
    private void sendEmails(String subject,String message){
        progressBar.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(t1){
                    stateTxt.setText("Loading...");
                }
                else{
                    stateTxt.setText("Sending emails...");
                }
            }
        });
        progressBar.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(t1.doubleValue()==1){
                    stateTxt.setText("Sent");
                }
            }
        });
        taskSendMail = createTask(subject,message);
        progressBar.progressProperty().bind(taskSendMail.progressProperty());
        progressIndicator.progressProperty().bind(taskSendMail.progressProperty());
        Thread th=new Thread(taskSendMail);
        th.start();
    }
    private Task<List<Student>> taskSendMail;

    private Task<List<Student>> createTask(String subject,String message) {
        return new Task<List<Student>>() {
            @Override
            protected List<Student> call() throws Exception {
                ExecutorService executor= Executors.newFixedThreadPool(5);
                List<Student> students =(List<Student>) StreamSupport.stream(studentRepo.findAll().spliterator(),false)
                        .collect(Collectors.toList());
                int index;
                List<Student> result=new ArrayList<>();
                for (index = 0; index < students.size(); index++) {
                    if (this.isCancelled() == true) {
                        return null;
                    } else {
                        int finalIndex = index;
                        Callable<Void> callable=()->{
                            String text="Notificare "+ students.get(finalIndex).getNume()+" "+students.get(finalIndex).getPrenume()+" "+students.get(finalIndex).getEmail()
                                    +message;
                            MailSender.sendMail(subject,text);
                            updateProgress(finalIndex, students.size() - 1);
                            return null;
                        };
                        executor.submit(callable);
                        result.add(students.get(index));

                    }
                }
                executor.shutdown();
                return result;
            }

        };
    }

    public void handleAddTema(ActionEvent actionEvent){
        if(student.equals(true)) {
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        String description=txtDescription.getText();
        if(description.equals("")) {
            MessageAlert.showErrorMessage(null, "Adaugati o descriere!");
            return;
        }
        Tema temaSave=new Tema(description);
        initializeId();
        temaSave.setId(temaId);
        try{
            Tema result=temaService.add(temaSave);
            if(result==null){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Save","Tema a fost adaugat cu succes!");
            }
            temaId++;
            retainId();
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMsg());
        }
        String text="\nS-a adaugat o tema"+" "+temaSave.getDescriere()+" are termenul de predare "+temaSave.getDeadlineWeek().toString();
        String subject="Add Tema";
        sendEmails(subject,text);
        clearFields();
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        initModel();
    }
    public void clearFields(){
        txtDescription.setText("");
        txtDeadline.setText("");
    }

    public void setStudent() {
        this.student = true;
    }
}
