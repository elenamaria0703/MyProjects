package services;

import model.Participant;

public interface ICompClient {
    public void participantAdded(Participant participant) throws CompetitionException;
}
