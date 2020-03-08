
import database.PostgreSQLJDBC;
import gui.MainApp;
import java.io.IOException;



public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        PostgreSQLJDBC.main();
        MainApp.main(args);
      // PostgreSQLJDBC.populatePredari();
       // PostgreSQLJDBC.populateTeme();
    //    PostgreSQLJDBC.populateStudents();
    }
}
