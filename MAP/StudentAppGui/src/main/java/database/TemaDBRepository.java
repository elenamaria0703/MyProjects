package database;

import domain.Entity;
import domain.Tema;
import domain.validators.Validator;

import java.sql.*;
import java.util.Map;

public class TemaDBRepository extends AbstractDBRepository {
    public TemaDBRepository(Connection dbConnection, Validator validator) {
        super(dbConnection, validator);
    }

    @Override
    public void populateEntities(Map entities) {
        Statement stmt = null;
        try {
            stmt=super.getDbConnection().createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Teme;" );
            while ( rs.next() ) {
                int id = rs.getInt("Id");
                String  descriere = rs.getString("Descriere");
                int start=rs.getInt("StartWeek");
                int deadline=rs.getInt("DeadlineWeek");
                Tema tema=new Tema(descriere);
                tema.setStartWeek(start);
                tema.setDeadlineWeek(deadline);
                tema.setId(id);
                entities.put(id,tema);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveEntityToDatabase(Entity entity) {
        Tema tema=(Tema) entity;
        Statement stmt = null;
        try {
            PreparedStatement st = super.getDbConnection().prepareStatement("INSERT INTO Teme (Id,Descriere,StartWeek,DeadlineWeek) VALUES (?,?,?,?)");
            st.setInt(1, tema.getId());
            st.setString(2, tema.getDescriere());
            st.setInt(3, tema.getStartWeek());
            st.setInt(4, tema.getDeadlineWeek());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntityFromDatabase(Object o) {
        Statement stmt = null;
        try {
            stmt = super.getDbConnection().createStatement();
            String sql = "DELETE from Teme where ID = "+o+";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntityInDatabase(Entity entity) {
        Tema tema=(Tema) entity;
        Statement stmt = null;
        try {
            String sql = "UPDATE Teme "
                    + "SET Descriere = ? "
                    + ",StartWeek = ? "
                    + ",DeadlineWeek = ? "
                    + "WHERE Id = ?";
            PreparedStatement pst=super.getDbConnection().prepareStatement(sql);
            pst.setString(1,tema.getDescriere());
            pst.setInt(2, tema.getStartWeek());
            pst.setInt(3, tema.getDeadlineWeek());
            pst.setInt(4,tema.getId());
            pst.executeUpdate();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
