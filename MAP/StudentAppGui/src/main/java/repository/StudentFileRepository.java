package repository;

import config.IOHandler;
import domain.Entity;
import domain.Student;
import domain.validators.Validator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentFileRepository extends FileRepository {
    private String archiveFileName;
    private List<Student> archivedStudents=new ArrayList<>();
    public StudentFileRepository(Validator validator,String fileN, String archivefile) {
        super(validator, fileN);
        this.archiveFileName=archivefile;
        super.loadDataFile();
    }

    private void loadArchivedDataFile() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = IOHandler.initializeBufferReader(archiveFileName);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] args = line.split(";");
                Student entity = (Student) createEntityFromLine(args);
                archivedStudents.add(entity);
            }
        } catch (IOException e) {
            System.out.println("Errors while loading the data");
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println("Errors while closing the buffer:");
            }
        }
    }
    public void deleteStudent(Integer id){
        loadArchivedDataFile();
        Student student= (Student) super.findOne(id);
        archivedStudents.add(student);
        FileWriter fileWriter=null;
        try{
            fileWriter= IOHandler.initializeDataWriter(archiveFileName);
            for(Student stud:archivedStudents){
                fileWriter.write(stud.toString());
            }
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println("Error at writing!");
        }
        super.deleteFromFile(id);
    }
    @Override
    public Entity createEntityFromLine(String[] args) {
        Integer id = Integer.parseInt(args[0]);
        String nume = args[1];
        String prenume = args[2];
        Integer grupa = Integer.parseInt(args[3]);
        String mail = args[4];
        String cadruDidact = args[5];
        Student student = new Student(nume, prenume, grupa, mail, cadruDidact);
        student.setId(id);
        return student;
    }

}
