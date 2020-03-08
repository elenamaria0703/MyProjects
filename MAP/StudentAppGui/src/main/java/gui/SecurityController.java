package gui;

import config.IOHandler;
import domain.AnUniversitar;
import domain.Semestru;
import domain.ThemeChanger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import service.StudentService;

import java.io.IOException;

public class SecurityController {
    private Stage security;
    private StudentService service;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label message;
    @FXML
    CheckBox checkShowPass;
    @FXML
    Label lblPass;

    public void showStudent(String cod,String password)  {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewStud.fxml"));
        Stage studentStage=new Stage();
        AnchorPane root= null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StudentController studCtrl=loader.getController();
        studCtrl.setService(this.service,security);
        checkCod(cod,studCtrl,password);
        studCtrl.setStage(studentStage);
        initializeVacation();
        Scene scene=new Scene(root,900,700);
        setTheme(scene);
        studentStage.setResizable(false);
        studentStage.setScene(scene);
        studCtrl.setScene(scene);
        studentStage.setTitle(cod);
        studentStage.show();
    }
    private void setTheme(Scene scene){
        if(ThemeChanger.getTheme().equals("blue"))
            scene.getStylesheets().add("/css/style.css");
        else
            scene.getStylesheets().add("/css/changeThemeStud.css");
    }
    private void initializeVacation(){
        try {
            String start,end;
            Semestru semestruUnu= AnUniversitar.INSTANCE.getSemestru1();
            if(AnUniversitar.INSTANCE.getSemestru().equals(semestruUnu)){
                start= IOHandler.getProperties().getProperty("sem1VacationStart");
                end=IOHandler.getProperties().getProperty("sem1VacationEnd");
            }
            else {
                start =IOHandler.getProperties().getProperty("sem2VacationStart");
                end=IOHandler.getProperties().getProperty("sem2VacationEnd");
            }
            Integer yearStart= Integer.parseInt(start.split("/")[0]);
            Integer monthStart=Integer.parseInt(start.split("/")[1]);
            Integer dayStart=Integer.parseInt(start.split("/")[2]);
            AnUniversitar.INSTANCE.getSemestru().setStartVacation(yearStart,monthStart,dayStart);
            Integer yearEnd=Integer.parseInt(end.split("/")[0]);
            Integer monthEnd=Integer.parseInt(end.split("/")[1]);
            Integer dayEnd=Integer.parseInt(end.split("/")[2]);
            AnUniversitar.INSTANCE.getSemestru().setEndVacation(yearEnd,monthEnd,dayEnd);
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
    public void initialize(){
        txtPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String cod=checkPassword(txtPassword.getText());
                if (cod==null) {
                    message.setText("Your password is incorrect!");
                    message.setTextFill(Color.rgb(21, 117, 84));
                } else {
                    showStudent(cod,txtPassword.getText());
                    security.hide();
                }
                txtPassword.clear();
        }
        });
        checkShowPass.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(checkShowPass.isSelected())
                    lblPass.setText(txtPassword.getText());
                else
                    lblPass.setText("");
            }
        });
    }
    public void setService(StudentService studentService,Stage parent){
        this.service=studentService;
        security=parent;
    }
    private String checkPassword(String password){
        if(service.getUsername().contains(password))
            return "Student";
        else if(password.equals("admin1919"))
            return "Administrator";
        else if(password.equals("teacher1919"))
            return "Teacher";
        else return null;
    }
    public void checkCod(String cod, StudentController studCtrl,String password){
        if(cod.equals("Student"))
            studCtrl.setStudent(password);
        else if(cod.equals("Teacher"))
            studCtrl.setTeacher();
        else if(cod.equals("Administrator"))
            studCtrl.setAdmin();
    }
}
