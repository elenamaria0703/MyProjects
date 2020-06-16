package servers;

import model.Participant;
import notification.Notification;
import notification.NotificationType;
import org.springframework.jms.core.JmsOperations;
import services.ICompNotificationService;

public class NotificationServiceImpl implements ICompNotificationService {
    private JmsOperations jmsOperations;

    public NotificationServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    @Override
    public void newParticipantAdded(Participant participant) {
        System.out.println("new participant was added");
        Notification notif=new Notification(NotificationType.NEW_PARTICIPANT,participant);
        jmsOperations.convertAndSend(notif);
        System.out.println("Sent message to ActiveMQ..."+notif);
    }
}
