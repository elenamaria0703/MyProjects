package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Participant;
import model.SportEvent;
import model.User;
import services.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ParticipantsControllerRMI  implements ICompetitionObserverRMI,Serializable {
    private Stage stage;
    private Stage parent;
    private ICompetitionServicesRMI server;
    ObservableList<Participant> modelParticipant= FXCollections.observableArrayList();

    private User crtUser;

    @FXML
    CheckBox check50;
    @FXML
    CheckBox check100;
    @FXML
    CheckBox check1000;
    @FXML
    CheckBox check1500;
    @FXML
    TableColumn<Participant,String> columnName;
    @FXML
    TableView<Participant> tableParticipants;
    @FXML
    TableColumn<Participant,String> columnFEvent;
    @FXML
    TableColumn<Participant,String> columnSEvent;
    @FXML
    TableColumn<Participant,Integer> columnAge;
    @FXML
    ComboBox<String> comboAge;
    @FXML
    TextField txtName;
    @FXML
    TextField txtAge;

    public void setServer(User crtUser, Stage parent, Stage currentStage, ICompetitionServicesRMI server) {//Stage stage,
        this.stage = currentStage;
        this.server = server;
        this.parent=parent;
        this.crtUser=crtUser;
        try {
            UnicastRemoteObject.exportObject(this,0);
        } catch (RemoteException e) {
            System.out.println("Error exporting object "+e);
        }
        try {
            initModel(this.server.getAll());
        } catch (CompetitionException | RemoteException e) {
            e.printStackTrace();
        }
    }


    public void initialize(){
        columnName.setCellValueFactory(new PropertyValueFactory<Participant,String>("name"));
        columnFEvent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String > p) {
                if(p.getValue().getFirstEvent()==null) return new SimpleStringProperty(" ");
                return new SimpleStringProperty(p.getValue().getFirstEvent().getName());
            }
        });
        columnSEvent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Participant, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Participant, String > p) {
                if(p.getValue().getSecondEvent()==null) return new SimpleStringProperty(" ");
                return new SimpleStringProperty(p.getValue().getSecondEvent().getName());
            }
        });
        columnAge.setCellValueFactory(new PropertyValueFactory<Participant,Integer>("age"));
        tableParticipants.setItems(modelParticipant);
        check1000.setDisable(true);
        check1500.setDisable(true);
        comboAge.getItems().setAll("6-8 years","9-11 years","12-15 years");
        comboAge.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        checkComboBox();
                    }
                }
        );
    }

    private void checkComboBox(){
        if(comboAge.getValue().equals("6-8 years")){
            check1000.setDisable(true);
            check1500.setDisable(true);
            check100.setDisable(false);
            check50.setDisable(false);
        }
        else if(comboAge.getValue().equals("9-11 years")){
            check1500.setDisable(true);
            check1000.setDisable(false);
            check100.setDisable(false);
            check50.setDisable(false);
        }
        else if(comboAge.getValue().equals("12-15 years")){
            check100.setDisable(false);
            check50.setDisable(true);
            check1500.setDisable(false);
            check1000.setDisable(false);
        }
    }
    private void initModel(List<Participant> participants){
        modelParticipant.clear();
        modelParticipant.setAll(participants);
    }
    public void handleLogOut(ActionEvent actionEvent){
        try {
            server.logout(crtUser,this);
        } catch (CompetitionException | RemoteException e) {
            e.printStackTrace();
        }
        this.stage.close();
        parent.show();
    }

    public void handleAddParticipant(){
        String name=txtName.getText();
        if(name.equals("") || txtAge.getText().equals("") || selectedEvents().size()==0){
            MessageAlert.showErrorMessage(null,"Not all the fields are completed!");
            return;
        }
        if(selectedEvents().size()>2){
            MessageAlert.showErrorMessage(null,"One participant can compete in at most two events!");
            return;
        }
        Integer age=Integer.parseInt(txtAge.getText());
        SportEvent firstEvent=new SportEvent(selectedEvents().get(0));
        SportEvent secondEvent=null;
        if(selectedEvents().size()>1) secondEvent=new SportEvent(selectedEvents().get(1));
        Integer nrEvents=selectedEvents().size();
        Participant participant=new Participant(name,age,firstEvent,secondEvent,nrEvents);
        try {
            server.addParticipant(participant);
        } catch (CompetitionException | RemoteException e) {
            MessageAlert.showErrorMessage(null,e.getMessage());
        }
        txtAge.setText("");
        txtName.setText("");
    }
    private List<String> selectedEvents(){
        List<String> events=new ArrayList<>();
        if(check100.isSelected() && check100.isDisable()==false)
            events.add("100m");
        if(check50.isSelected() && check50.isDisable()==false)
            events.add("50m");
        if(check1000.isSelected() && check1000.isDisable()==false)
            events.add("1000m");
        if(check1500.isSelected() && check1500.isDisable()==false)
            events.add("1500m");
        return events;
    }

    @Override
    public void newParticipantAdded(Participant participant) throws CompetitionException {
        modelParticipant.add(participant);
    }
}
