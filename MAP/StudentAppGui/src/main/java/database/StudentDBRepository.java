package database;

import config.IOHandler;
import domain.Entity;
import domain.Student;
import domain.validators.Validator;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentDBRepository extends AbstractDBRepository {

    private String archiveFileName;
    private List<Student> archivedStudents=new ArrayList<>();

    public StudentDBRepository(Connection dbConnection, Validator validator,String filename) {
        super(dbConnection, validator);
        this.archiveFileName=filename;
    }

    @Override
    public void populateEntities(Map entities) {
        Statement stmt = null;
        try {
            stmt = super.getDbConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Studenti;" );
            while ( rs.next() ) {
                int id = rs.getInt("Id");
                String  nume = rs.getString("Nume");
                String prenume=rs.getString("Prenume");
                int grupa=rs.getInt("Grupa");
                String email=rs.getString("Email");
                String cadru=rs.getString("CadruDidactic");
                Student student=new Student(nume,prenume,grupa,email,cadru);
                student.setId(id);
                entities.put(id,student);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEntityToDatabase(Entity entity) {
        Student student= (Student) entity;
        Statement stmt = null;
        try {
            PreparedStatement st = super.getDbConnection().prepareStatement("INSERT INTO Studenti (Id,Nume,Prenume,Grupa,Email,CadruDidactic) VALUES (?, ?, ?, ?,?,?)");
            st.setInt(1, student.getId());
            st.setString(2, student.getNume());
            st.setString(3, student.getPrenume());
            st.setInt(4, student.getGrupa());
            st.setString(5,student.getEmail());
            st.setString(6,student.getCadruDidacticIndrumatorLab());
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntityFromDatabase(Object o) {
        Integer id=(Integer) o;
        Statement stmt = null;
        try {
            stmt = super.getDbConnection().createStatement();
            String sql = "DELETE from Studenti where ID = "+id+";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntityInDatabase(Entity entity) {
//        Student student=(Student) entity;
//        Statement stmt = null;
//        try {
//            String sql = "UPDATE Studenti "
//                    + "SET Nume = ? "
//                    + ",Prenume = ? "
//                    + ",Grupa = ? "
//                    + ",Email = ? "
//                    + ",CadruDidactic = ? "
//                    + "WHERE Id = ?";
//            PreparedStatement pst=super.getDbConnection().prepareStatement(sql);
//            pst.setString(1,student.getNume());
//            pst.setString(2, student.getPrenume());
//            pst.setInt(3, student.getGrupa());
//            pst.setString(4,student.getEmail());
//            pst.setString(5,student.getCadruDidacticIndrumatorLab());
//            pst.setInt(6,student.getId());
//            pst.executeUpdate();
//            pst.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    public Student deleteStudent(Integer id){
        loadArchivedDataFile();
        Student student= (Student) super.delete(id);
        if(student== null) return student;
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
        return student;
    }
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

    private void loadArchivedDataFile() {
        BufferedReader bufferedReader = null;
        archivedStudents.clear();
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

}
