package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Participant;
import model.User;

import services.CompetitionException;

import services.ICompetitionServices;
import services.ICompetitionServicesRMI;


import java.io.IOException;

public class SecurityControllerRMI {
    private Stage primaryStage;
    private ICompetitionServicesRMI server;
    private User crtUser;

    Parent mainPartParent;
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

    public void setServer(ICompetitionServicesRMI server, Stage stage){
        this.server=server;
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
                    FXMLLoader cloader = new FXMLLoader(
                            getClass().getClassLoader().getResource("viewParticipantsRMI.fxml"));
                    Parent croot=cloader.load();


                    ParticipantsControllerRMI partCtrl =
                            cloader.<ParticipantsControllerRMI>getController();
                    Stage stage = new Stage();
                    partCtrl.setServer(crtUser,primaryStage,stage,server);
                    server.login(crtUser, partCtrl);


                    stage.setTitle("Competition Window for " + crtUser.getId());
                    //stage.setScene(new Scene(mainPartParent));
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
