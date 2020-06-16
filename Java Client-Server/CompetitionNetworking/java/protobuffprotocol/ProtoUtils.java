package protobuffprotocol;

import model.Participant;
import model.SportEvent;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class ProtoUtils {
    public static CompetitionProtobufs.CompetitionRequest createLoginRequest(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder().setUsername(user.getUsername()).setPassword(user.getPassword()).build();
        CompetitionProtobufs.CompetitionRequest request=CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.Login).setUser(userDTO).build();
        return request;
    }
    public static  CompetitionProtobufs.CompetitionRequest createLogoutRequest(User user){
        CompetitionProtobufs.User userDTO=CompetitionProtobufs.User.newBuilder().setUsername(user.getUsername()).setPassword(user.getPassword()).build();
        CompetitionProtobufs.CompetitionRequest request=CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.Logout).setUser(userDTO).build();
        return request;
    }
    public static CompetitionProtobufs.CompetitionRequest createGetAllRequest(){
        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder().
                setType(CompetitionProtobufs.CompetitionRequest.Type.GetAll).build();
        return request;
    }
    public static CompetitionProtobufs.CompetitionRequest createAddParticipantRequest(model.Participant participant){
        CompetitionProtobufs.SportEvent first=CompetitionProtobufs.SportEvent.newBuilder().setName("").build();
        if(participant.getFirstEvent()!=null)
            first=CompetitionProtobufs.SportEvent.newBuilder().setName(participant.getFirstEvent().getName()).build();
        CompetitionProtobufs.SportEvent second=CompetitionProtobufs.SportEvent.newBuilder().setName("").build();
        if(participant.getSecondEvent()!=null)
            second=CompetitionProtobufs.SportEvent.newBuilder().setName(participant.getSecondEvent().getName()).build();
        CompetitionProtobufs.Participant participantDTO= CompetitionProtobufs.Participant.newBuilder()
                .setId(participant.getId().toString()).setName(participant.getName())
                .setAge(participant.getAge().toString())
                .setFirstEvent(first)
                .setSecondEvent(second)
                .setNrEvents(participant.getNrEventsAttended().toString()).build();
        CompetitionProtobufs.CompetitionRequest request= CompetitionProtobufs.CompetitionRequest.newBuilder()
                .setType(CompetitionProtobufs.CompetitionRequest.Type.AddParticipant).setParticipant(participantDTO).build();
        return request;
    }
    public static String getError(CompetitionProtobufs.Response response){
        String errorMessage=response.getError();
        return errorMessage;
    }
    public static Participant getParticipant(CompetitionProtobufs.Response response){
        CompetitionProtobufs.Participant part=response.getParticipant();
        model.SportEvent first=new SportEvent(part.getFirstEvent().getName());
        model.SportEvent second=new SportEvent(part.getSecondEvent().getName());
        model.Participant participant=new Participant(part.getName(),Integer.parseInt(part.getAge()),first,second,Integer.parseInt(part.getNrEvents()));
        participant.setId(Integer.parseInt(part.getId()));
        return  participant;
    }
    public static List<Participant> getParticipants(CompetitionProtobufs.Response response){
        List<model.Participant> participants=new ArrayList<>();
        for(int i=0;i<response.getParticipantsCount();i++){
            CompetitionProtobufs.Participant part=response.getParticipants(i);
            model.SportEvent first=new SportEvent(part.getFirstEvent().getName());
            model.SportEvent second=new SportEvent(part.getSecondEvent().getName());
            model.Participant participant=new Participant(part.getName(),Integer.parseInt(part.getAge()),first,second,Integer.parseInt(part.getNrEvents()));
            participant.setId(Integer.parseInt(part.getId()));
            participants.add(participant);
        }
        return participants;
    }
}
