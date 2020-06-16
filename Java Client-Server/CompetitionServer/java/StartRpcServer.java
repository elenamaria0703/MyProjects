import model.User;
import persistence.ORMUtils;
import persistence.ParticipantsDBRepository;
import persistence.UsersDBRepository;
import persistence.UsersHibernateRepository;
import servers.CompetitionServicesImpl;
import services.ICompetitionServices;
import utils.AbstractServer;
import utils.CompetitionRpcConcurrentServer;
import utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/competition.properties"));
            System.out.println("Server properties set");

        } catch (IOException e) {
            System.out.println("Can not find properties file");
            return;
        }
        //UsersDBRepository repoUsers=new UsersDBRepository(serverProps);
        UsersHibernateRepository repoUsers=new UsersHibernateRepository();
        repoUsers.delete(new User("alex","bbb"));
        repoUsers.save(new User("ana","ana"));
        ParticipantsDBRepository repoPart=new ParticipantsDBRepository(serverProps);
        ICompetitionServices compServerImpl=new CompetitionServicesImpl(repoUsers,repoPart);

        int compServerPort=defaultPort;
        try {
            compServerPort = Integer.parseInt(serverProps.getProperty("competition.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+compServerPort);
        AbstractServer server = new CompetitionRpcConcurrentServer(compServerPort, compServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
                ORMUtils.close();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
