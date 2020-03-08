package repository;

import config.IOHandler;
import domain.IdNota;
import domain.Predare;
import domain.validators.Validator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;


public class NotaJSONRepository extends InMemoryRepository<IdNota, Predare> {
    String fileName;
    public NotaJSONRepository(Validator<Predare> validator, String fileName) {
        super(validator);
        this.fileName=fileName;
        loadDataFromFile();
    }

    private void loadDataFromFile(){
        JSONParser jsonParser=new JSONParser();
        try{
            Object obj=jsonParser.parse(IOHandler.initializeBufferReader(fileName));
            JSONArray note=(JSONArray) obj;
            note.forEach(notaObj->{
                JSONObject nota=(JSONObject) notaObj;
                Integer nrStudent=Integer.parseInt((String) nota.get("Student"));
                Integer nrTema=Integer.parseInt((String) nota.get("Tema"));
                Integer nrNota=Integer.parseInt((String) nota.get("Nota"));
                Integer saptPredare=Integer.parseInt((String) nota.get("Predata in saptamana"));
                Integer deadline=Integer.parseInt((String) nota.get("Deadline"));
                String feedback=(String) nota.get("Feedback");
                String profesor=(String) nota.get("Profesor");
                Predare predare=new Predare(nrStudent,nrTema,nrNota,saptPredare,deadline,feedback,profesor);
                IdNota idNota=new IdNota(nrStudent,nrTema);
                predare.setId(idNota);
                super.save(predare);
            });
        } catch (ParseException e) {
            System.out.println("Error at parsing");;
        } catch (IOException e) {
            System.out.println("Error IO");
        }
    }

    private void writeDataToFile(){
        try {
            FileWriter fileWriter=IOHandler.initializeDataWriter(fileName);
            JSONArray toatePredari=new JSONArray();
            super.findAll().forEach(predare -> {
                JSONObject obj=new JSONObject();
                obj.put("Profesor",predare.getProfesor());
                obj.put("Student",predare.getNrStudent().toString());
                obj.put("Tema",predare.getNrTema().toString());
                obj.put("Nota",predare.getNrNota().toString());
                obj.put("Predata in saptamana",predare.getSaptPredare().toString());
                obj.put("Deadline",predare.getDeadline().toString());
                obj.put("Feedback",predare.getFeedback());
                toatePredari.add(obj);
            });
            fileWriter.write(toatePredari.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IO Error");
        }

    }

    public void savePredare(Predare predare){
        super.save(predare);
        writeDataToFile();
    }
}
