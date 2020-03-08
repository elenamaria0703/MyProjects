package gui;

import domain.Nota;
import domain.Predare;
import domain.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.vm.ci.code.site.Call;
import service.NotaService;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static service.NotaService.notaRepo;
import static service.StudentService.studentRepo;


public class AllGradesController {
    ObservableList<Predare> modelPredari= FXCollections.observableArrayList();
    NotaService service;
    private Stage stage;

    @FXML
    TableView<Predare> tablePredari;
    @FXML
    TableColumn<Predare,Integer> columnStudent;
    @FXML
    TableColumn<Predare,Integer> columnTema;
    @FXML
    TableColumn<Predare,Integer> columnDeadline;
    @FXML
    TableColumn<Predare,Integer> columnNota;
    @FXML
    TableColumn<Predare,Integer> columnSaptPred;
    @FXML
    TableColumn<Predare,String> columnProfesor;
    @FXML
    TableColumn<Predare,String> columnFeed;
    @FXML
    TextField txtProfesor;
    @FXML
    TextField txtNota;
    @FXML
    TextField txtTema;


    @FXML
    public void initialize(){
        columnNota.setCellValueFactory(new PropertyValueFactory<Predare,Integer>("nrNota"));
        columnDeadline.setCellValueFactory(new PropertyValueFactory<Predare,Integer>("deadline"));
        columnProfesor.setCellValueFactory(new PropertyValueFactory<Predare,String>("profesor"));
        columnTema.setCellValueFactory(new PropertyValueFactory<Predare,Integer>("nrTema"));
        columnSaptPred.setCellValueFactory(new PropertyValueFactory<Predare,Integer>("saptPredare"));
//        columnGrupa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Predare, Integer>, ObservableValue<Integer>>() {
//            @Override
//            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Predare, Integer> predare) {
//                Student stud= (Student) studentRepo.findOne(predare.getValue().getNrStudent());
//                return new SimpleIntegerProperty(stud.getGrupa()).asObject();
//            }
//        });
//        columnStudent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Predare, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Predare, String> predare) {
//                Student stud= (Student) studentRepo.findOne(predare.getValue().getNrStudent());
//                return new SimpleStringProperty(stud.getNume()+" "+stud.getPrenume());
//            }
//        });
        columnFeed.setCellValueFactory(new PropertyValueFactory<Predare,String>("feedback"));
        columnStudent.setCellValueFactory(new PropertyValueFactory<Predare,Integer>("nrStudent"));
        txtProfesor.textProperty().addListener((observable,oldvalue,newvalue)->handleFilter());
        txtNota.textProperty().addListener((observable,oldvalue,newvalue)->handleFilterNota());
        txtTema.textProperty().addListener((observable,oldvalue,newvalue)->handleFilterTema());
        tablePredari.setItems(modelPredari);

    }
    public void handleFilterNota(){
        Predicate<Predare> filtrareNota= p->p.getNrNota().toString().startsWith(txtNota.getText());
        modelPredari.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .filter(filtrareNota)
                .collect(Collectors.toList()));
    }
    public void handleFilterTema(){
        Predicate<Predare> filtrareTema= p->p.getNrTema().toString().startsWith(txtTema.getText());
        modelPredari.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .filter(filtrareTema)
                .collect(Collectors.toList()));
    }
    public void handleFilter(){
        Predicate<Predare> filtrareProf= p->p.getProfesor().startsWith(txtProfesor.getText());
        modelPredari.setAll(StreamSupport.stream(service.getAll().spliterator(),false)
                .filter(filtrareProf)
                .collect(Collectors.toList()));
    }
    public void setService(NotaService serviceNota){
        service=serviceNota;
        initModel();
    }
    public void handleClear(ActionEvent actionEvent){
        txtTema.setText("");
        txtProfesor.setText("");
        txtNota.setText("");
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }
    public void handleBack(ActionEvent actionEvent){
        Stage owner= (Stage) this.stage.getOwner();
        owner.show();
        this.stage.close();
    }
    private void initModel(){
        List<Predare> predari= (List<Predare>) StreamSupport.stream(service.getAll().spliterator(),false)
                .collect(Collectors.toList());
        modelPredari.clear();
        modelPredari.setAll(predari);
    }
}
