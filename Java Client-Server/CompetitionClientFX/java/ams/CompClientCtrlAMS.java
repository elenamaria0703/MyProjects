package ams;

import gui.ParticipantsController;
import model.Participant;
import model.User;
import notification.Notification;
import rpcprotocol.ams.CompServerAMSRpcProxy;
import services.CompetitionException;
import services.ICompServicesAMS;
import services.NotificationReceiver;
import services.NotificationSubscriber;

import javax.swing.*;
import java.util.List;

public class CompClientCtrlAMS implements NotificationSubscriber {
    private ICompServicesAMS server;
    private ParticipantsControllerAMS partCtrl;
    private NotificationReceiver receiver;

    public CompClientCtrlAMS(ICompServicesAMS server) {
        this.server=server;
    }

    public void setPartCtrl(ParticipantsControllerAMS partCtrl) {
        this.partCtrl = partCtrl;
    }

    public void setReceiver(NotificationReceiver receiver) {
        this.receiver = receiver;
    }

    public void login(User user) throws CompetitionException {
        server.login(user);
        receiver.start(this);
    }

    public List<Participant> getAll()throws CompetitionException{
        return server.getAll();
    }
    public void logout(User user)throws CompetitionException{
        try {
            server.logout(user);
        }finally {
            receiver.stop();
        }
    }
    public void addParticipant(Participant participant) throws CompetitionException{
        server.addParticipant(participant);
    }
    @Override
    public void notificationReceived(Notification notif) {
        try {
            System.out.println("Ctrl notificationReceived ... " + notif.getType());
            SwingUtilities.invokeLater(()->{
                switch (notif.getType()) {
                    case NEW_PARTICIPANT: {
                        partCtrl.newParticipantAdded(notif.getParticipant());
                        break;
                    }
                }});
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
