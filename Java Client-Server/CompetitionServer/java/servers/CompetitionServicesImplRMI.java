package servers;

import services.*;


import model.Participant;
import model.User;
import persistence.ParticipantsDBRepository;
import persistence.UsersDBRepository;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompetitionServicesImplRMI extends UnicastRemoteObject implements ICompetitionServicesRMI {
    private UsersDBRepository userRepository;
    private ParticipantsDBRepository participantRepository;
    private Map<String,ICompetitionObserverRMI> loggedClients;

    public CompetitionServicesImplRMI(UsersDBRepository userRepository, ParticipantsDBRepository participantRepository) throws RemoteException{
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, ICompetitionObserverRMI client) throws CompetitionException {
        User userFound=userRepository.findOne(user.getUsername());
        if(userFound!=null && userFound.getPassword().equals(user.getPassword())){
            if(loggedClients.size()!=0 && loggedClients.get(user.getUsername())!=null)
                throw new CompetitionException("User already logged in.");
            loggedClients.put(user.getUsername(),client);
        }else{
            throw  new CompetitionException("Authentication failed.");
        }
    }

    @Override
    public synchronized void addParticipant(Participant participant) throws CompetitionException, RemoteException {
        participantRepository.save(participant);
        loggedClients.forEach((k,v)->{
            try {
                v.newParticipantAdded(participant);
            } catch (CompetitionException | RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public synchronized void logout(User user, ICompetitionObserverRMI client) throws CompetitionException {
        ICompetitionObserverRMI localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new CompetitionException("User "+user.getId()+" is not logged in.");
    }

    @Override
    public List<Participant> getAll() throws CompetitionException {
        return (List<Participant>) participantRepository.finadAll();
    }

}
