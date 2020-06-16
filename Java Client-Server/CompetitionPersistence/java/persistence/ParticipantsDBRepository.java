package persistence;

import model.Participant;
import model.SportEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantsDBRepository implements IRepository<Integer, Participant> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger= LogManager.getLogger();

    public ParticipantsDBRepository(Properties props) {
        logger.info("Initializing ParticipantsRepository with properties {}",props);
        this.jdbcUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("select count(*) as [SIZE] from Participants")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Participant entity) {
        logger.traceEntry("saving participant {}",entity);
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("insert into Participants values(?,?,?,?,?,?)")) {
            preStmt.setString(2,entity.getName());
            preStmt.setInt(3,entity.getAge());
            if(entity.getFirstEvent()!=null) preStmt.setString(4,entity.getFirstEvent().getName());
            if(entity.getSecondEvent()!=null) preStmt.setString(5,entity.getSecondEvent().getName());
            preStmt.setInt(6,entity.getNrEventsAttended());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Participant findOne(Integer integer) {
        logger.traceEntry("finding participant with id {}",integer);
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Participants where id=?")) {
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    int id=result.getInt("id");
                    int age=result.getInt("age");
                    int nrEvents=result.getInt("nrEvents");
                    String name=result.getString("name");
                    SportEvent firstEvent=new SportEvent(result.getString("firstEvent"));
                    SportEvent secondEvent=new SportEvent(result.getString("secondEvent"));
                    return new Participant(name,age,firstEvent,secondEvent,nrEvents);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Participant> finadAll() {
        logger.info("finding all the the participants");
        Connection connection=jdbcUtils.getConnection();
        List<Participant> participants=new ArrayList<>();
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Participants")) {
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()) {
                    int id = result.getInt("id");
                    int age = result.getInt("age");
                    int nrEvents = result.getInt("nrEvents");
                    String name = result.getString("name");
                    SportEvent firstEvent = new SportEvent(result.getString("firstEvent"));
                    SportEvent secondEvent = new SportEvent(result.getString("secondEvent"));
                    Participant participant = new Participant(name, age, firstEvent, secondEvent, nrEvents);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participants;
    }
}
