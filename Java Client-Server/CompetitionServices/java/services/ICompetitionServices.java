package services;

import model.Participant;
import model.User;

import java.util.List;

public interface ICompetitionServices {
    void login(User user,ICompetitionObserver client) throws CompetitionException;
    void addParticipant(Participant participant) throws CompetitionException;
    void logout(User user,ICompetitionObserver client) throws CompetitionException;
    List<Participant> getAll() throws CompetitionException;
}
