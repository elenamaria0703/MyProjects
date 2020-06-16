package comp.repositories;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import persistence.IRepository;
import persistence.JdbcUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UsersDBRepository implements IRepository<String, User> {
    private JdbcUtils jdbcUtils;
    private static final Logger logger=  LogManager.getLogger();

    public UsersDBRepository() {
        jdbcUtils=new JdbcUtils(getProps());
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = connection.prepareStatement("select count(*) as [SIZE] from Users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
        logger.traceEntry("saving user {}",entity);
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("insert into Users values(?,?)")) {
            preStmt.setString(1,entity.getUsername());
            preStmt.setString(2,entity.getPassword());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String remove(String username){
        logger.traceEntry("removing user {}",username);
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("delete from Users where username=?")){
            preStmt.setString(1,username);
            preStmt.executeUpdate();
            return username;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User update(User entity){
        logger.traceEntry("updating user {}",entity.getUsername());
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("update Users set password=? where username=?")){
            preStmt.setString(1,entity.getPassword());
            preStmt.setString(2,entity.getUsername());
            preStmt.executeUpdate();
            return entity;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findOne(String s) {
        logger.traceEntry("finding user with id {}",s);
        Connection connection=jdbcUtils.getConnection();
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Users where username=?")) {
            preStmt.setString(1,s);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    String password=result.getString("password");
                    return new User(s,password);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<User> finadAll() {
        logger.info("finding all users");
        Connection connection=jdbcUtils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Users")) {
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    String username=result.getString("username");
                    String password=result.getString("password");
                    users.add(new User(username,password));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private Properties getProps(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(new FileInputStream("C:\\Users\\Maria\\Desktop\\SportCompetition\\CompetitionServer\\src\\main\\resources\\competition.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find competition.properties "+e);
            return null;
        }
        return serverProps;
    }
}
