package gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.NotaService;
import service.StudentService;
import service.TemaService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StudentReportsController {
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;
    private Stage stage;
    private static boolean whoIsIt=false;
    ObservableList<Student> modelStudent= FXCollections.observableArrayList();
   @FXML
   ListView<Student> listViewReports;
   @FXML
    PieChart chartReports;

    public void setService(NotaService service, StudentService studentService, TemaService temaService,Stage reportsStage) {
        notaService = service;
        this.studentService=studentService;
        this.temaService=temaService;
        stage = reportsStage;
    }

    public void handleBack(ActionEvent actionEvent) {
        Stage owner = (Stage) stage.getOwner();
        owner.show();
        this.stage.close();
    }
    private Map<Student, Double> setMedieStudent(){
        Map<Student,Double> medii=new HashMap<>();
        for(Student student:studentService.getAll()){
            Double sumaPonderi=0d;
            Double sumaNotePond=0d;
            List<StudentNota> noteStud=notaService.getNoteStudent(student);
            for(StudentNota nota:noteStud){
                Tema tema= (Tema) temaService.temaRepo.findOne(nota.getTema());
                Integer pondere=tema.getDeadlineWeek()-tema.getStartWeek();
                Integer valNota=nota.getNota();
                sumaPonderi+=pondere;
                sumaNotePond+=pondere*valNota;
            }
            Double media=sumaNotePond/sumaPonderi;
            if(noteStud.size()!=0)
                medii.put(student,media);
        }
        return medii;
    }
    public void handleMedieStudenti(ActionEvent actionEvent){
        List<Student> listStudents= StreamSupport.stream(studentService.getAll().spliterator(),false)
                .collect(Collectors.toList());
        modelStudent.setAll(listStudents);
        listViewReports.setItems(modelStudent);
        Map<Student,Double> medii=setMedieStudent();
        listViewReports.setCellFactory(list->new ListCell<Student>(){
            @Override
            protected void updateItem(Student item, boolean empty){
                super.updateItem(item,empty);
                if(item==null || empty){
                    setText(null);
                }else{
                    setText(item.getNume()+" "+item.getPrenume()+" "+medii.get(item));
                }
            }
        });
        whoIsIt=false;
        showPieChart(medii);
    }
    public void handleStudentiAdmisi(ActionEvent actionEvent){
        Map<Student,Double> medii=setMedieStudent();
        List<Student> listStudents=new ArrayList<>();
        for(Map.Entry<Student,Double> entry:medii.entrySet())
        {
            if(entry.getValue()>=4){
                listStudents.add(entry.getKey());
            }
        }
        modelStudent.setAll(listStudents);
        listViewReports.setItems(modelStudent);
        listViewReports.setCellFactory(list->new ListCell<Student>(){
                @Override
                protected void updateItem(Student item, boolean empty){
                    super.updateItem(item,empty);
                    if(item==null || empty){
                        setText(null);
                    }else{
                        setText(item.getNume()+" "+item.getPrenume()+" "+medii.get(item));
                    }
                }
        });
        whoIsIt=false;
        showPieChart(medii);
    }
    public List<Student> getStudentiExemplari(){
        List<Student> listStudent=new ArrayList<>();
        for(Student student:studentService.getAll()){
            List<StudentNota> note=notaService.getNoteStudent(student);
            Integer nr= Math.toIntExact(note.stream().filter(n -> n.getDeadline() == n.getSaptPred()).count());
            if(nr==note.size() && !note.isEmpty())
                listStudent.add(student);
        }
        return listStudent;
    }
    public void handleStudentiExemplari(ActionEvent actionEvent){
        modelStudent.setAll(getStudentiExemplari());
        listViewReports.setItems(modelStudent);
        listViewReports.setCellFactory(list->new ListCell<Student>(){
            @Override
            protected void updateItem(Student item, boolean empty){
                super.updateItem(item,empty);
                if(item==null || empty){
                    setText(null);
                }else{
                    setText(item.getNume()+" "+item.getPrenume());
                }
            }
        });
        whoIsIt=true;
        showPieChart(setMedieStudent());
    }

    private void showPieChart(Map<Student,Double> medii){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Medii peste 9", detMediiNoua(medii)),
                new PieChart.Data("Medii peste 8", detMediiOpt(medii)),
                new PieChart.Data("Medii peste 5", detMediiCinci(medii)));
        chartReports.setData(pieChartData);
        chartReports.setTitle("Medie Studenti");
        chartReports.setClockwise(true);
        chartReports.setLabelLineLength(50);
        chartReports.setLabelsVisible(true);
        chartReports.setStartAngle(180);
    }

    private Integer detMediiNoua(Map<Student,Double> medii){
        return Math.toIntExact(medii.values().stream()
                .filter(m -> m >= 9d)
                .count());
    }
    private Integer detMediiOpt(Map<Student,Double> medii){
        return Math.toIntExact(medii.values().stream().filter(m-> m>=8d).count());
    }
    private Integer detMediiCinci(Map<Student,Double> medii){
        return Math.toIntExact(medii.values().stream().filter(m-> m>=5d).count());
    }
    public void handleSaveToPdf(ActionEvent actionEvent)  {
        Document document = new Document();
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
            PdfWriter.getInstance(document, new FileOutputStream(selectedDirectory.getAbsolutePath()+"/Reports.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(1);
            if(whoIsIt==true)
                addRowsExpl(table);
            else
                addRowsMedii(table);
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Saved","Fisierul a fost salvat cu succes!");
        document.close();
    }
    private void addRowsMedii(PdfPTable table) {
        Map<Student,Double> mediiStud=setMedieStudent();
        for(Map.Entry<Student,Double> entry : mediiStud.entrySet())
        {
            table.addCell(entry.getKey().getNume()+" "+entry.getKey().getPrenume()+" "+entry.getValue().toString());
        }

    }
    private void addRowsExpl(PdfPTable table) {
        getStudentiExemplari().forEach(student -> {
            table.addCell(student.getNume()+" "+student.getPrenume());
        });
    }
}
