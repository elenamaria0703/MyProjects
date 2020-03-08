package gui;

import config.IOHandler;
import database.PostgreSQLJDBC;
import domain.AnUniversitar;
import domain.Semestru;
import domain.ThemeChanger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.StudentService;

import java.io.IOException;


public class MainApp extends Application {


    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewSecurity.fxml"));
        AnchorPane root=loader.load();
        SecurityController securityCtrl=loader.getController();
        securityCtrl.setService(new StudentService(),primaryStage);
        Scene scene=new Scene(root,900,700);
        setTheme(scene);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void setTheme(Scene scene){
        if(ThemeChanger.getTheme().equals("blue"))
            scene.getStylesheets().add("/css/security.css");
        else
            scene.getStylesheets().add("/css/changeThemeSecurity.css");
    }


}
