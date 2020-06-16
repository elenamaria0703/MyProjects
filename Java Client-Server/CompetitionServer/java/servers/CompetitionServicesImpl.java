package servers;

import model.Participant;
import model.User;
import persistence.IRepository;
import persistence.ParticipantsDBRepository;
import persistence.UsersDBRepository;
import services.CompetitionException;
import services.ICompetitionObserver;
import services.ICompetitionServices;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompetitionServicesImpl implements ICompetitionServices {
    //private UsersDBRepository userRepository;
    private IRepository<String,User> userRepository;
    private ParticipantsDBRepository participantRepository;
    private Map<String,ICompetitionObserver> loggedClients;

    public CompetitionServicesImpl(IRepository<String,User> userRepository, ParticipantsDBRepository participantRepository) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        loggedClients=new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User user, ICompetitionObserver client) throws CompetitionException {
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
    public synchronized void addParticipant(Participant participant) throws CompetitionException {
        participantRepository.save(participant);
        loggedClients.forEach((k,v)->{
            try {
                v.newParticipantAdded(participant);
            } catch (CompetitionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public synchronized void logout(User user, ICompetitionObserver client) throws CompetitionException {
        ICompetitionObserver localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new CompetitionException("User "+user.getId()+" is not logged in.");
    }

    @Override
    public List<Participant> getAll() throws CompetitionException {
        return (List<Participant>) participantRepository.finadAll();
    }

}
