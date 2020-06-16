package ams;

import gui.MessageAlert;
import gui.ParticipantsController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.CompetitionException;
import services.ICompServicesAMS;
import services.ICompetitionServices;

import java.io.IOException;

public class SecurityControllerAMS {
    private Stage primaryStage;
    private CompClientCtrlAMS ctrl;
    private User crtUser;


    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label message;
    @FXML
    CheckBox checkShowPass;
    @FXML
    Label lblPass;

    public void setUser(User user) {
        this.crtUser = user;
    }

    public void setCtrl(CompClientCtrlAMS ctrl,Stage stage){
        this.ctrl=ctrl;
        this.primaryStage=stage;

    }

    public void initialize(){
        txtPassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(txtUsername.getText().equals("")) {
                    MessageAlert.showErrorMessage(null, "Add a username!");
                    return;
                }
                String nume = txtUsername.getText();
                String passwd = txtPassword.getText();
                crtUser = new User(nume, passwd);
                try {
                    ctrl.login(crtUser);
                    Stage stage = new Stage();
                    FXMLLoader cloader = new FXMLLoader(
                            getClass().getClassLoader().getResource("viewParticipants.fxml"));
                    Parent croot=cloader.load();
                    ParticipantsControllerAMS partCtrl=cloader.getController();
                    partCtrl.setCtrl(ctrl);
                    partCtrl.setStages(crtUser,primaryStage,stage);
                    ctrl.setPartCtrl(partCtrl);
                    stage.setTitle("Competition Window for " + crtUser.getId());
                    stage.setScene(new Scene(croot));
                    primaryStage.hide();
                    stage.show();

                } catch (CompetitionException ex) {
                    if(ex.getMessage().equals("User already logged in."))
                        message.setText("User already logged in.");
                    else
                        message.setText("Your password is incorrect!");
                    message.setTextFill(Color.rgb(21, 117, 84));
                }catch (IOException ex){
                    ex.printStackTrace();
                }
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
}
