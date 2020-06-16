import gui.SecurityController;
import gui.SecurityControllerRMI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.ICompetitionServicesRMI;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

public class StartRMIClient extends Application{
    private Stage primaryStage;
    private static String defaultServer="localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties clientProps=new Properties();
        try {
            clientProps.load(StartRMIClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties "+e);
            return;
        }


        String name=clientProps.getProperty("competition.rmi.serverID","Competition");
        String serverIP=clientProps.getProperty("comp.server.host",defaultServer);
        try {

            Registry registry = LocateRegistry.getRegistry(serverIP);
            ICompetitionServicesRMI server = (ICompetitionServicesRMI) registry.lookup(name);
            System.out.println("Obtained a reference to remote chat server");

            FXMLLoader loader = new FXMLLoader(
                    StartRMIClient.class.getClassLoader().getResource("viewSecurityRMI.fxml"));
            Parent root=loader.load();


            SecurityControllerRMI ctrl =
                    loader.<SecurityControllerRMI>getController();
            ctrl.setServer(server,primaryStage);


            primaryStage.setTitle("Competition");
            primaryStage.setScene(new Scene(root, 500, 400));
            primaryStage.show();




        } catch (Exception e) {
            System.err.println("Competition Initialization  exception:"+e);
            e.printStackTrace();
        }

    }
}
