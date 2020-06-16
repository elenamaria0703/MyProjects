package servers;

import model.Participant;
import model.User;
import persistence.IRepository;
import persistence.ParticipantsDBRepository;
import persistence.UsersDBRepository;
import services.CompetitionException;
import services.ICompNotificationService;
import services.ICompServicesAMS;
import services.ICompetitionObserver;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompetitionServerAMSImpl implements ICompServicesAMS {
    private UsersDBRepository userRepository;
    private ParticipantsDBRepository participantRepository;
    private Map<String,User> loggedClients;
    private ICompNotificationService notificationService;

    public CompetitionServerAMSImpl(UsersDBRepository repository, ParticipantsDBRepository participantsDBRepository, NotificationServiceImpl notificationService) {
        this.userRepository = repository;
        this.participantRepository = participantsDBRepository;
        this.loggedClients = new ConcurrentHashMap<>();
        this.notificationService = notificationService;
    }

    @Override
    public synchronized void login(User user) throws CompetitionException {
        User userFound=userRepository.findOne(user.getUsername());
        if(userFound!=null && userFound.getPassword().equals(user.getPassword())){
            if(loggedClients.size()!=0 && loggedClients.get(user.getUsername())!=null)
                throw new CompetitionException("User already logged in.");
            loggedClients.put(user.getUsername(),user);
        }else{
            throw  new CompetitionException("Authentication failed.");
        }
    }

    @Override
    public synchronized void addParticipant(Participant participant) throws CompetitionException {
        participantRepository.save(participant);
        notificationService.newParticipantAdded(participant);
    }

    @Override
    public synchronized void logout(User user) throws CompetitionException {
        User localClient=loggedClients.remove(user.getUsername());
        if (localClient==null)
            throw new CompetitionException("User "+user.getId()+" is not logged in.");
    }

    @Override
    public List<Participant> getAll() throws CompetitionException {
        return (List<Participant>) participantRepository.finadAll();
    }
}
