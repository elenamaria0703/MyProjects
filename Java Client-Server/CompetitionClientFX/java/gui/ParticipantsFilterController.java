//package gui;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.ListView;
//import model.Participant;
//import service.ServiceConcurs;
//
//import java.util.List;
//
//
//public class ParticipantsFilterController {
//    ObservableList<Participant> modelParticipants= FXCollections.observableArrayList();
//    private ServiceConcurs serviceConcurs;
//
//    public void setService(ServiceConcurs service){
//        this.serviceConcurs=service;
//    }
//    public void setModelParticipants(List<Participant> participants){
//        modelParticipants.setAll(participants);
//    }
//    @FXML
//    ListView<Participant> listViewParticipants;
//
//    @FXML
//    void initialize(){
//        listViewParticipants.setItems(modelParticipants);
//    }
//
//}
