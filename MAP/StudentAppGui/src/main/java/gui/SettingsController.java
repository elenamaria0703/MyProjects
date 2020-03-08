package gui;

import config.IOHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class SettingsController {
    private Stage stage;
    @FXML
    DatePicker firstSemesterStart;
    @FXML
    DatePicker firstSemesterEnd;
    @FXML
    DatePicker secondSemesterStart;
    @FXML
    DatePicker secondSemesterEnd;
    @FXML
    DatePicker startVacFirstSem;
    @FXML
    DatePicker endVacFirstSem;
    @FXML
    DatePicker startVacSecSem;
    @FXML
    DatePicker endVacSecSem;
    private Properties addProperties(){
        Properties props= null;
        try {
            props = IOHandler.getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
        props.put("studentArchiveFile","./src/main/resources/files/student-archive.txt");
        props.put("sem1Start",getDate(firstSemesterStart));
        props.put("sem1End",getDate(firstSemesterEnd));
        props.put("sem2Start",getDate(secondSemesterStart));
        props.put("sem2End",getDate(secondSemesterEnd));
        props.put("sem1VacationStart",getDate(startVacFirstSem));
        props.put("sem1VacationEnd",getDate(endVacFirstSem));
        props.put("sem2VacationStart",getDate(startVacSecSem));
        props.put("sem2VacationEnd",getDate(endVacSecSem));
        return props;
    }
    private void setSettings(){
        try {
            File configFile = new File("./src/main/resources/files/config.properties");
            FileWriter writer =  new FileWriter(configFile);
            Properties props=addProperties();
            props.forEach((x,y)->{
                try {
                    writer.write(x.toString()+"="+y.toString()+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String getDate(DatePicker datePicker){
        LocalDate date=datePicker.getValue();
        String dateStr=date.getYear()+"/"+date.getMonthValue()+"/"+date.getDayOfMonth();
        return dateStr;
    }
    private boolean validData(){
        if(firstSemesterStart.getValue()==null || firstSemesterEnd.getValue()==null || secondSemesterStart.getValue()==null ||
                secondSemesterEnd.getValue()==null || startVacFirstSem.getValue()==null || endVacFirstSem.getValue()==null ||
                startVacSecSem.getValue()==null || endVacSecSem.getValue()==null)
            return false;
        return true;
    }

    public void handleLoadSettings(){
        if(!validData()){
            MessageAlert.showErrorMessage(null,"Sunt campuri necompletate!");
            return;
        }
        setSettings();
        stage.close();
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }


}
