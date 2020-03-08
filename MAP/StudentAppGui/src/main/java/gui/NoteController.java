package gui;

import domain.*;
import exceptions.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import service.NotaService;

import javafx.event.ActionEvent;
import service.StudentService;
import service.TemaService;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;




public class NoteController implements PropertyChangeListener {
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;
    private Student student;
    private Stage stage;
    private Boolean studentLog=false;


    ObservableList<Tema> modelTeme= FXCollections.observableArrayList();
    ObservableList<StudentNota> modelNote=FXCollections.observableArrayList();
    @FXML
    ComboBox<Tema> comboHomeWork;
    @FXML
    TableColumn<StudentNota,Integer> tableColumnTema;
    @FXML
    TableColumn<StudentNota,Integer> tableColumnSapt;
    @FXML
    TableColumn<StudentNota,Integer> tableColumnDeadline;
    @FXML
    TableColumn<StudentNota,Integer> tableColumnNota;
    @FXML
    TableColumn<StudentNota,String> tableColumnFeedback;
    @FXML
    TableView<StudentNota> tableNote;
    @FXML
    DatePicker datePicker;
    @FXML
    TextArea txtFeedback;
    @FXML
    TextField txtGrade;
    @FXML
    ComboBox<String> comboProf;
    @FXML
    CheckBox checkMotivare;

    public void setService(NotaService serviceNota, TemaService serviceTema,StudentService serviceStudent,Student student,Stage stageNote){
        this.notaService=serviceNota;
        this.studentService=serviceStudent;
        this.temaService=serviceTema;
        this.student=student;
        this.stage=stageNote;
        this.notaService.addChangeListener(this);
        initModelTeme();
        initModelNote();
        comboHomeWork.getSelectionModel().selectFirst();
    }


    @FXML
    public void initialize(){
        comboHomeWork.setItems(modelTeme);
        comboHomeWork.setCellFactory(new Callback<ListView<Tema>, ListCell<Tema>>() {
            @Override
            public ListCell<Tema> call(ListView<Tema> temaListView) {
                return new ListCell<Tema>(){
                    @Override
                    protected void updateItem(Tema item,boolean empty){
                        super.updateItem(item,empty);
                        if(item==null || empty){
                            setText(null);
                        }else{
                            setText(item.getId().toString()+" "+item.getDescriere());
                        }
                    }
                };
            }
        });
        comboHomeWork.setConverter(new StringConverter<Tema>() {
            @Override
            public String toString(Tema tema) {
                if(tema==null) {
                    return null;
                }else{
                    return tema.getId().toString()+" "+tema.getDescriere();
                }
            }

            @Override
            public Tema fromString(String s) {
                return null;
            }
        });
        tableColumnTema.setCellValueFactory(new PropertyValueFactory<StudentNota,Integer>("tema"));
        tableColumnSapt.setCellValueFactory(new PropertyValueFactory<StudentNota,Integer>("saptPred"));
        tableColumnDeadline.setCellValueFactory(new PropertyValueFactory<StudentNota,Integer>("deadline"));
        tableColumnNota.setCellValueFactory(new PropertyValueFactory<StudentNota,Integer>("nota"));
        tableColumnFeedback.setCellValueFactory(new PropertyValueFactory<StudentNota,String>("feedback"));
        tableNote.setItems(modelNote);
        comboProf.getItems().setAll("Andrei","Gabi","Vlad","Sergiu");
        comboProf.setEditable(true);
        comboProf.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    }
                }
        );
        datePicker.setValue(LocalDate.now());

    }
    public void handleBack(ActionEvent actionEvent){
        Stage owner= (Stage) stage.getOwner();
        owner.show();
        this.stage.close();
    }

    public void initModelTeme(){
        int curentWeek=AnUniversitar.INSTANCE.getCurrentWeek();
        Predicate<Tema> filtrareDeadline=t->curentWeek-t.getDeadlineWeek()<=2;
        Predicate<Tema> filtrareDeadline1=t->curentWeek-t.getDeadlineWeek()>=0;
        List<StudentNota> note=notaService.getNoteStudent(student);
        Predicate<Tema> filterNote=t->note.stream().map(StudentNota::getTema).anyMatch(t.getId()::equals);
        List<Tema> teme= StreamSupport.stream(temaService.getAll().spliterator(),false)
                .filter(filtrareDeadline.and(filtrareDeadline1).and(Predicate.not(filterNote)))
                .sorted((t1,t2)-> t2.getDeadlineWeek().compareTo(t1.getDeadlineWeek()))
                .collect(Collectors.toList());
        modelTeme.clear();
        modelTeme.setAll(teme);
    }
    public void initModelNote(){
        modelNote.clear();
        modelNote.setAll(notaService.getNoteStudent(student));
    }

    public void handleAdd(ActionEvent actionEvent){
        if(studentLog.equals(true)) {
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        try {
            Tema tema = comboHomeWork.getValue();
            boolean motivare=false;
            if(datePicker.getValue()==null || comboProf.getValue()==null || txtGrade.getText()=="") throw new ValidationException("Lipsesc date pentru adaugarea unei note!");
            String profesor = comboProf.getValue();
            LocalDate date = datePicker.getValue();
            Integer nota = Integer.parseInt(txtGrade.getText());
            String feedback = txtFeedback.getText();
            Nota notaStud = new Nota(date, profesor, nota);
            IdNota idNota = new IdNota(student.getId(), tema.getId());
            notaStud.setId(idNota);
            if(checkMotivare.isSelected())
                motivare=true;
            notaService.add(notaStud,feedback,motivare);
            updateElements();
        }catch (ValidationException ve){
            MessageAlert.showErrorMessage(null,ve.getMsg());
        }
    }

    private void updateElements(){
        comboProf.getItems().set(0,"");
        txtGrade.setText("");
        datePicker.setValue(null);
        txtFeedback.setText("");
        initModelTeme();
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        initModelNote();
    }
    public void setStudentLog(){
        studentLog=true;
    }
}
