package services;

import model.Participant;

public interface ICompetitionObserver {
    void newParticipantAdded(Participant participant) throws CompetitionException;
}
