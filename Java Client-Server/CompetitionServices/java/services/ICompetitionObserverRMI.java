package services;

import model.Participant;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICompetitionObserverRMI extends Remote {
    void newParticipantAdded(Participant participant) throws CompetitionException, RemoteException;
}
