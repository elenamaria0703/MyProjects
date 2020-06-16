package protobuffprotocol;

import model.Participant;
import model.User;

import services.CompetitionException;
import services.ICompetitionObserver;
import services.ICompetitionServices;

import java.io.IOException;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoCompProxy implements ICompetitionServices {
    private String host;
    private int port;


    private ICompetitionObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<CompetitionProtobufs.Response> qresponses;
    private volatile boolean finished;

    public ProtoCompProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<CompetitionProtobufs.Response>();
    }

    @Override
    public void login(User user, ICompetitionObserver client) throws CompetitionException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(user));
        CompetitionProtobufs.Response response=readResponse();
        if (response.getType()==CompetitionProtobufs.Response.Type.Ok){
            this.client=client;
            return;
        }
        if (response.getType()==CompetitionProtobufs.Response.Type.Error){
            String err=ProtoUtils.getError(response);
            closeConnection();
            throw new CompetitionException(err);
        }

    }
    @Override
    public void addParticipant(Participant participant) throws CompetitionException {
        sendRequest(ProtoUtils.createAddParticipantRequest(participant));
        CompetitionProtobufs.Response response=readResponse();
        if (response.getType()==CompetitionProtobufs.Response.Type.Error){
            String err=ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
    }

    @Override
    public void logout(User user, ICompetitionObserver client) throws CompetitionException {
        sendRequest(ProtoUtils.createLogoutRequest(user));
        CompetitionProtobufs.Response response=readResponse();
        closeConnection();
        if (response.getType()==CompetitionProtobufs.Response.Type.Error){
            String err=ProtoUtils.getError(response);
            throw new CompetitionException(err);
        }
    }

    @Override
    public List<Participant> getAll() throws CompetitionException {
        sendRequest(ProtoUtils.createGetAllRequest());
        CompetitionProtobufs.Response response=readResponse();
        if(response.getType()==CompetitionProtobufs.Response.Type.Error){
            String err=ProtoUtils.getError(response);
            throw  new CompetitionException(err);
        }
        return (List<Participant>) ProtoUtils.getParticipants(response);
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void sendRequest(CompetitionProtobufs.CompetitionRequest request)throws CompetitionException{
        try {
            System.out.println("Sending request ..."+request);
            //request.writeTo(output);
            request.writeDelimitedTo(output);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            throw new CompetitionException("Error sending object "+e);
        }

    }
    private void initializeConnection() throws CompetitionException{
        try {
            connection=new Socket(host,port);
            output=  connection.getOutputStream();
            //output.flush();
            input=  connection.getInputStream();     //new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private CompetitionProtobufs.Response readResponse() throws CompetitionException{
        CompetitionProtobufs.Response response=null;
        try{
            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    CompetitionProtobufs.Response response=CompetitionProtobufs.Response.parseDelimitedFrom(input);
                    System.out.println("response received "+response);

                    if (isUpdateResponse(response.getType())){
                        handleUpdate(response);
                    }else{
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
    private void handleUpdate(CompetitionProtobufs.Response response){
        model.Participant participant= ProtoUtils.getParticipant(response);
        System.out.println("Participant was added");
        try {
            client.newParticipantAdded(participant);
        } catch (CompetitionException e) {
            e.printStackTrace();
        }
    }
    private boolean isUpdateResponse(CompetitionProtobufs.Response.Type type){
        if(type==CompetitionProtobufs.Response.Type.AddedParticipant)
            return true;
        return false;
    }

}
