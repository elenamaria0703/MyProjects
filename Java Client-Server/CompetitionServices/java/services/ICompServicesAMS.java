package services;

import model.Participant;
import model.User;

import java.util.List;

public interface ICompServicesAMS {
    void login(User user) throws CompetitionException;
    void addParticipant(Participant participant) throws CompetitionException;
    void logout(User user) throws CompetitionException;
    List<Participant> getAll() throws CompetitionException;
}
