package gui;

import domain.Predare;
import domain.Tema;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;
import service.NotaService;
import service.TemaService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomeworkReportsController {
    private TemaService temaService;
    private NotaService notaService;
    private Stage stage;

    @FXML
    PieChart chartHomework;

    public void setService( NotaService notaService,TemaService temaService, Stage stage){
        this.temaService=temaService;
        this.notaService=notaService;
        this.stage=stage;
        initPieChart();
    }
    public void handleBack(ActionEvent actionEvent) {
        Stage owner = (Stage) stage.getOwner();
        owner.show();
        this.stage.close();
    }
    public Map<Tema,Double> getTemeMedii(){
        Map<Tema,Double> medii=new HashMap<>();
        temaService.getAll().forEach(tema->{
            List<Predare> predari=StreamSupport.stream(notaService.getAll().spliterator(),false)
                    .filter(predare -> predare.getNrTema().equals(tema.getId()))
                    .collect(Collectors.toList());
            Double suma=0d;
            for(Predare pr:predari)
                suma+=pr.getNrNota();
            if(predari.size()!=0)
                medii.put(tema,suma/predari.size());
        });
        return medii;
    }
    public void initPieChart() {
        Map<Tema, Double> medii = getTemeMedii();
        List<PieChart.Data> data = new ArrayList<>();
        for (Map.Entry<Tema, Double> entry : medii.entrySet()) {
            data.add(new PieChart.Data(entry.getKey().getDescriere(), entry.getValue()));
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.addAll(data);
        chartHomework.setData(pieChartData);
        chartHomework.setTitle("Ce mai grea tema");
        chartHomework.setClockwise(true);
        chartHomework.setLabelLineLength(50);
        chartHomework.setLabelsVisible(true);
        chartHomework.setStartAngle(180);
    }
}
