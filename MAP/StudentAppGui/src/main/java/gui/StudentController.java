package gui;

import domain.Student;
import domain.ThemeChanger;
import exceptions.ValidationException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.NotaService;
import service.StudentService;
import javafx.event.ActionEvent;
import service.TemaService;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements PropertyChangeListener {
    ObservableList<Student> modelStudent= FXCollections.observableArrayList();
    private StudentService service;
    private Stage stage;
    private Stage parent;
    private static Integer studId;
    private static Integer temaId;
    private Boolean student=false;
    private Boolean admin=false;
    private Boolean teacher=false;
    private String studentLogat;

    private Scene scene;

    @FXML
    TableView<Student> tableStudent;
    @FXML
    TableColumn<Student,Integer> tableColumnId;
    @FXML
    TableColumn<Student,String> tableColumnNume;
    @FXML
    TableColumn<Student, String> tableColumnPrenume;
    @FXML
    TableColumn<Student,Integer> tableColumnGrupa;
    @FXML
    TableColumn<Student,String> tableColumnEmail;
    @FXML
    TableColumn<Student,String> tableColumnCadru;
    @FXML
    TextField txtNume;
    @FXML
    TextField txtPrenume;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtGrupa;
    @FXML
    TextField txtProfesor;
    @FXML
    TextField txtId;
    @FXML
    TextField txtSearch;


    @FXML
    public void initialize(){
        tableColumnNume.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        tableColumnId.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Student, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Student, Integer> st) {
                return new SimpleIntegerProperty(st.getValue().getId()).asObject();
            }
        });
        tableColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student,String>("prenume"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        tableColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student,Integer>("grupa"));
        tableColumnCadru.setCellValueFactory(new PropertyValueFactory<Student,String>("cadruDidacticIndrumatorLab"));

        txtSearch.textProperty().addListener((observable,oldvalue,newvalue)->handleFilter());
        txtGrupa.textProperty().addListener((observable,oldvalue,newvalue)->handleFilterGr());
        tableStudent.setItems(modelStudent);

    }
    public void setService(StudentService service,Stage parent) {
        this.service = service;
        this.service.addChangeListener(this);
        this.parent=parent;
        initModel(this.service.getAll());
        loadSelected();
        txtId.setEditable(false);

    }
    private void initModel(Iterable<Student> itStudents){
        List<Student> listStudents= StreamSupport.stream(itStudents.spliterator(),false)
                .collect(Collectors.toList());
        modelStudent.clear();
       modelStudent.setAll(listStudents);
    }

    private void loadSelected(){
        tableStudent.getSelectionModel().selectedItemProperty().addListener(((obs, oldSelection, newSelection) ->
        {
            if(newSelection == null)
                tableStudent.getSelectionModel().clearSelection();
            else {
                txtId.setText(newSelection.getId().toString());
                txtEmail.setText(newSelection.getEmail());
                txtGrupa.setText(newSelection.getGrupa().toString());
                txtNume.setText(newSelection.getNume());
                txtPrenume.setText(newSelection.getPrenume());
                txtProfesor.setText(newSelection.getCadruDidacticIndrumatorLab());
            }
        }));

    }

    public void handleDeleteStudent(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        Student student=tableStudent.getSelectionModel().getSelectedItem();
        if(student!=null){
            Student deleted=service.remove(student);
            if(deleted!=null){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Delete","Studentul a fost sters cu succes!");
                clearTextFields();
            }
        }else MessageAlert.showErrorMessage(null,"Nu ati selectat nici un student!");
    }

    public void handleClearAll(ActionEvent actionEvent){
        clearTextFields();
    }

    public void handleSaveStudent(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        Student toSave=createStudentFromFields();
        initializeId();
        toSave.setId(studId);
        try{
            Student result=service.add(toSave);
            if(result==null){
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Save","Studentul a fost adaugat cu succes!");
            }
            studId++;
            retainId();
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMsg());
        }
        clearTextFields();
    }

    public void handleUpdateStudent(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        Student toUpdate=createStudentFromFields();
        Integer idStudent=Integer.parseInt(txtId.getText());
        toUpdate.setId(idStudent);
        try{
            service.update(toUpdate);
        }catch (ValidationException e){
            MessageAlert.showErrorMessage(null,e.getMsg());
        }
        clearTextFields();
    }

    private Student createStudentFromFields(){
        Integer grupa=null;
        try{
            grupa=Integer.parseInt(txtGrupa.getText());
        }catch (Exception e){
            MessageAlert.showErrorMessage(null,"Tipul de date incorect!");
        }
        String nume=txtNume.getText();
        String prenume=txtPrenume.getText();
        String email=txtEmail.getText();
        String profesor=txtProfesor.getText();
        Student student=new Student(nume,prenume,grupa,email,profesor);
        return  student;
    }

    public void clearTextFields(){
        txtId.setText("");
        txtEmail.setText("");
        txtGrupa.setText("");
        txtNume.setText("");
        txtPrenume.setText("");
        txtProfesor.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        initModel((Iterable<Student>) propertyChangeEvent.getNewValue());
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
    public void handleFilterGr(){
        Predicate<Student> filtrareGrupa=n->n.getGrupa().toString().startsWith(txtGrupa.getText());
        modelStudent.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                    .filter(filtrareGrupa)
                    .collect(Collectors.toList()));
    }
    public void handleFilter(){
        Predicate<Student> filtrareNume=n->n.getNume().startsWith(txtSearch.getText());
        modelStudent.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .filter(filtrareNume)
                .collect(Collectors.toList()));
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void handleClose(){
        this.stage.close();
        setTheme(parent.getScene(),"/css/security.css","/css/changeThemeSecurity.css");
        parent.show();
    }

    public void handleAddGrade(ActionEvent actionEvent) throws IOException {
        if(admin==true){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewNote.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageNote = new Stage();
            dialogStageNote.initOwner(this.stage);
            Scene scene = new Scene(root,900,700);
            setTheme(scene,"/css/note.css","/css/changeThemeNote.css");
            dialogStageNote.setScene(scene);

            NoteController noteCtrl = loader.getController();
            Student student=tableStudent.getSelectionModel().getSelectedItem();
            if(student==null){
                MessageAlert.showErrorMessage(null,"Nu ati selectat studentul!");
            }

            if(this.student.equals(true) && !checkAddGradeStudent(student)) {
                MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
                return;
            }
            if(this.student.equals(true)) noteCtrl.setStudentLog();
            dialogStageNote.setTitle("Grades "+student.getNume()+" "+student.getPrenume());
            if(student!=null)
            {
                this.stage.hide();
                noteCtrl.setService(new NotaService(),new TemaService(), this.service,student,dialogStageNote);
                dialogStageNote.setResizable(false);
                dialogStageNote.show();
            }
            else
                MessageAlert.showErrorMessage(null,"Nu este niciun student selectat!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAddGradeStudent(Student student) {
        if(student.getEmail().substring(0,4).equals(studentLogat))
            return true;
        return false;
    }

    public void handleShowHomeworks(ActionEvent actionEvent){
        if(admin==true){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        this.stage.hide();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewTeme.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageTeme = new Stage();
            dialogStageTeme.setTitle("Homeworks");
            dialogStageTeme.initOwner(this.stage);
            Scene scene = new Scene(root,900,700);
            setTheme(scene,"/css/teme.css","/css/changeThemeStud.css");
            dialogStageTeme.setScene(scene);
            TemeController temeCtrl = loader.getController();

            temeCtrl.setService(new TemaService(), dialogStageTeme);
            dialogStageTeme.setResizable(false);
            if(student.equals(true)) temeCtrl.setStudent();
            dialogStageTeme.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleShowStudentReports(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        this.stage.hide();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewReports.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageReports = new Stage();
            dialogStageReports.setTitle("Reports");
            dialogStageReports.initOwner(this.stage);
            Scene scene = new Scene(root,900,700);
            setTheme(scene,"/css/reports.css","/css/changeThemeReports.css");
            dialogStageReports.setScene(scene);
            StudentReportsController reportsStudCtrl=loader.getController();
            reportsStudCtrl.setService(new NotaService(),new StudentService(),new TemaService(),dialogStageReports);
            dialogStageReports.setResizable(false);
            dialogStageReports.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void handleShowHomeworkReports(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        if(student.equals(true)) {
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        this.stage.hide();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewReportsHomework.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageReports = new Stage();
            dialogStageReports.setTitle("Reports");
            dialogStageReports.initOwner(this.stage);
            Scene scene = new Scene(root,900,700);
            setTheme(scene,"/css/reports.css","/css/changeThemeReports.css");
            dialogStageReports.setScene(scene);
            HomeworkReportsController reportsHomeCtrl=loader.getController();
            reportsHomeCtrl.setService(new NotaService(),new TemaService(),dialogStageReports);
            dialogStageReports.setResizable(false);
            dialogStageReports.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setStudent(String email){
        this.student=true;
        studentLogat=email;
    }
    public void setAdmin(){
        this.admin=true;
    }
    public  void setTeacher(){
        this.teacher=true;
    }
    public void setScene(Scene scene){
        this.scene=scene;
    }

    public void handleChangeTheme(ActionEvent actionEvent){
        if(admin==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        String currentTheme=ThemeChanger.getTheme();
        if(currentTheme.equals("blue"))
        {
            this.scene.getStylesheets().add("/css/changeThemeStud.css");
            ThemeChanger.setTheme("yellow");
        }
        else {
            this.scene.getStylesheets().add("/css/style.css");
            ThemeChanger.setTheme("blue");
        }

    }
    public void  handleSettings(ActionEvent actionEvent){
        if(admin==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewSettings.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageSettings = new Stage();
            dialogStageSettings.setTitle("Settings");
            Scene scene = new Scene(root,400,600);
            setTheme(scene,"/css/settings.css","/css/changeThemeSett.css");
            dialogStageSettings.setScene(scene);
            SettingsController setCtrl=loader.getController();
            setCtrl.setStage(dialogStageSettings);
            dialogStageSettings.setResizable(false);
            dialogStageSettings.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setTheme(Scene scene,String blueTheme,String yellowTheme){
        if(ThemeChanger.getTheme().equals("blue"))
            scene.getStylesheets().add(blueTheme);
        else
            scene.getStylesheets().add(yellowTheme);
    }
    public void  handleAllGrades(ActionEvent actionEvent){
        if(teacher==false){
            MessageAlert.showErrorMessage(null,"Nu aveti permisiune sa accesati aceasta functionalitate!");
            return;
        }
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/viewAllGrades.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            Stage dialogStageAllGrades = new Stage();
            dialogStageAllGrades.setTitle("All Grades");
            Scene scene = new Scene(root,900,700);
            setTheme(scene,"/css/style.css","/css/changeThemeStud.css");
            //scene.getStylesheets().add("/css/style.css");
            dialogStageAllGrades.setScene(scene);
            AllGradesController grdCtrl=loader.getController();
            grdCtrl.setStage(dialogStageAllGrades);
            grdCtrl.setService(new NotaService());
            dialogStageAllGrades.initOwner(this.stage);
            dialogStageAllGrades.setResizable(false);
            this.stage.hide();
            dialogStageAllGrades.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
