import persistence.ParticipantsDBRepository;
import persistence.UsersDBRepository;
import servers.CompetitionServicesImpl;
import servers.CompetitionServicesImplRMI;
import services.ICompetitionServices;
import services.ICompetitionServicesRMI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

public class StartRMIServer {
    public static void main(String[] args) {

        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRMIServer.class.getResourceAsStream("/competition.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find competition.properties "+e);
            return;
        }
        UsersDBRepository repoUsers=new UsersDBRepository(serverProps);
        ParticipantsDBRepository repoPart=new ParticipantsDBRepository(serverProps);
        ICompetitionServicesRMI compServerImpl= null;
        try {
            compServerImpl = new CompetitionServicesImplRMI(repoUsers,repoPart);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            String name = serverProps.getProperty("competition.rmi.serverID","Competition");
            //ICompetitionServicesRMI stub =(ICompetitionServicesRMI) UnicastRemoteObject.exportObject(compServerImpl, 0);
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("before binding");
            registry.rebind(name, compServerImpl);
            System.out.println("Competition server   bound");
        } catch (Exception e) {
            System.err.println("Competition server exception:"+e);
            e.printStackTrace();
        }

    }
}
