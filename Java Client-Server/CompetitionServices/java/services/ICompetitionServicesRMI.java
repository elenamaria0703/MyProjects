package services;

import model.Participant;
import model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICompetitionServicesRMI extends Remote {
    void login(User user,ICompetitionObserverRMI client) throws CompetitionException, RemoteException;
    void addParticipant(Participant participant) throws CompetitionException,RemoteException;
    void logout(User user,ICompetitionObserverRMI client) throws CompetitionException,RemoteException;
    List<Participant> getAll() throws CompetitionException,RemoteException;
}
