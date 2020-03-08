package repository;

import domain.StudentNota;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentPersnalFile {
    private static String studentName;
    private static StudentNota studentNota;

    public StudentPersnalFile() {
    }

    public static void setFilename(String filename) {
        studentName= filename;
    }
    public static void setStudentNota(StudentNota nota){
        studentNota=nota;
    }
    private static void loadDataFromfile(File file,List<StudentNota> studentNotas){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            JSONParser jsonParser=new JSONParser();
            try {
                Object obj=jsonParser.parse(bufferedReader);
                JSONArray note= (JSONArray) obj;
                note.forEach(notaObj->{
                    JSONObject nota= (JSONObject) notaObj;
                    Integer tema=Integer.parseInt(nota.get("Tema").toString());
                    Integer valNota=Integer.parseInt(nota.get("Nota").toString());
                    Integer sapt=Integer.parseInt(nota.get("Predata in saptamana").toString());
                    Integer deadline=Integer.parseInt(nota.get("Deadline").toString());
                    String feedback=nota.get("Feedback").toString();
                    StudentNota studentN=new StudentNota(tema,valNota,sapt,deadline,feedback);
                    studentNotas.add(studentN);
                });

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
    private static void writeDataToFile(File file,List<StudentNota> studentNotas) {
        try {
            FileWriter writer=new FileWriter(file);
            JSONArray note=new JSONArray();
            studentNotas.forEach(stNota->{
                JSONObject obj=new JSONObject();
                obj.put("Tema",stNota.getTema().toString());
                obj.put("Nota",stNota.getNota().toString());
                obj.put("Predata in saptamana",stNota.getSaptPred().toString());
                obj.put("Deadline",stNota.getDeadline().toString());
                obj.put("Feedback",stNota.getFeedback());
                note.add(obj);
            });
            writer.write(note.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveNota(){
        File file=new File("./src/main/resources/files/"+studentName+".json");
        List<StudentNota> studentNotaList=new ArrayList<>();
        try {
            if(file.createNewFile()){
                studentNotaList.add(studentNota);
                writeDataToFile(file,studentNotaList);
            }
            else{
                loadDataFromfile(file,studentNotaList);
                studentNotaList.add(studentNota);
                writeDataToFile(file,studentNotaList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<StudentNota> getAllGrades(){
        File file=new File("./src/main/resources/files/"+studentName+".json");
        List<StudentNota> studentNotaList=new ArrayList<>();
        if(file.exists()){
            loadDataFromfile(file,studentNotaList);
            return studentNotaList;
        }
        else return studentNotaList;
    }
}
