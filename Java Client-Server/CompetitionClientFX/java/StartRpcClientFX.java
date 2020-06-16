import gui.ParticipantsController;
import gui.SecurityController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Participant;

import rpcprotocol.CompetitionServicesRpcProxy;
import services.ICompetitionServices;

import java.io.IOException;
import java.security.Security;
import java.util.List;
import java.util.Properties;

public class StartRpcClientFX extends Application {
    private Stage primaryStage;

    private static int defaultCompPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClientFX.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("comp.server.host", defaultServer);
        int serverPort = defaultCompPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("comp.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultCompPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        CompetitionServicesRpcProxy server = new CompetitionServicesRpcProxy(serverIP, serverPort);


        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("viewSecurity.fxml"));
        Parent root=loader.load();


        SecurityController ctrl =
                loader.<SecurityController>getController();
        ctrl.setServer(server,primaryStage);


//        FXMLLoader cloader = new FXMLLoader(
//                getClass().getClassLoader().getResource("viewParticipants.fxml"));
//        Parent croot=cloader.load();
//
//
//        ParticipantsController partCtrl =
//                cloader.<ParticipantsController>getController();
//
//        ctrl.setPartController(partCtrl);
//        ctrl.setParent(croot);

        primaryStage.setTitle("Competition");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();



    }
}
