package notification;

import model.Participant;

public class Notification {
    private NotificationType type;
    private Participant participant;

    public Notification(NotificationType type, Participant participant) {
        this.type = type;
        this.participant = participant;
    }

    public Notification() {
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
