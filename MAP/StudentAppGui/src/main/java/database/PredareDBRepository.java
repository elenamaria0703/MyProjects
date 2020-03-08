package database;

import domain.Entity;
import domain.IdNota;
import domain.Predare;
import domain.validators.Validator;

import java.sql.*;
import java.util.Map;

public class PredareDBRepository extends AbstractDBRepository {
    public PredareDBRepository(Connection dbConnection, Validator validator) {
        super(dbConnection, validator);
    }

    @Override
    public void populateEntities(Map entities) {
        Statement stmt = null;
        try {
            stmt = super.getDbConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Predari;" );
            while ( rs.next() ) {
                int idStudent = rs.getInt("IdStudent");
                int idTema=rs.getInt("IdTema");
                int nota=rs.getInt("Nota");
                int predSapt=rs.getInt("SaptPredare");
                int deadline=rs.getInt("Deadline");
                String feedback=rs.getString("Feedback");
                String profesor=rs.getString("Profesor");
                IdNota idNota=new IdNota(idStudent,idTema);
                Predare predare=new Predare(idStudent,idTema,nota,predSapt,deadline,feedback,profesor);
                predare.setId(idNota);
                entities.put(idNota,predare);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEntityToDatabase(Entity entity) {
        Predare predare=(Predare) entity;
        Statement stmt = null;
        try {
            PreparedStatement st = super.getDbConnection().prepareStatement("INSERT INTO Predari (IdStudent,IdTema,Nota,SaptPredare,Deadline,Feedback,Profesor) VALUES (?,?,?,?,?,?,?)");
            st.setInt(1, predare.getNrStudent());
            st.setInt(2, predare.getNrTema());
            st.setInt(3, predare.getNrNota());
            st.setInt(4, predare.getSaptPredare());
            st.setInt(5,predare.getDeadline());
            st.setString(6,predare.getFeedback());
            st.setString(7,predare.getProfesor());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntityFromDatabase(Object o) {
        IdNota idNota=(IdNota) o;
        Statement stmt = null;
        try {
            stmt = super.getDbConnection().createStatement();
            String sql = "DELETE from Predari where IdStudent = "+idNota.getIdStudent()+" and IdTema="+idNota.getIdTema()+";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntityInDatabase(Entity entity) {
        Predare predare=(Predare) entity;
        Statement stmt = null;
        try {
            String sql = "UPDATE Predari "
                    + "SET IdStudent = ? "
                    + ",IdTema = ? "
                    + ",Nota = ? "
                    + ",SaptPredare = ? "
                    + ",Deadline = ? "
                    + ",Feedback = ? "
                    + ",Profesor = ? "
                    + "WHERE IdStudent = ? AND IdTema=?";
            PreparedStatement pst=super.getDbConnection().prepareStatement(sql);
            pst.setInt(1, predare.getNrStudent());
            pst.setInt(2, predare.getNrTema());
            pst.setInt(3, predare.getNrNota());
            pst.setInt(4, predare.getSaptPredare());
            pst.setInt(5,predare.getDeadline());
            pst.setString(6,predare.getFeedback());
            pst.setString(7,predare.getProfesor());
            pst.setInt(8,predare.getNrStudent());
            pst.setInt(9,predare.getNrTema());
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
