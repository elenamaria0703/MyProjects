package rpcprotocol.ams;

import model.Participant;
import model.User;
import rpcprotocol.Request;
import rpcprotocol.RequestType;
import rpcprotocol.Response;
import rpcprotocol.ResponseType;
import services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CompServerAMSRpcProxy implements ICompServicesAMS {
    private String host;
    private int port;


    private ICompClient client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public CompServerAMSRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    @Override
    public void login(User user) throws CompetitionException {
        initializeConnection();
        Request req=new Request.Builder().type(RequestType.LOGIN).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new CompetitionException(err);
        }

    }

    @Override
    public void addParticipant(Participant participant) throws CompetitionException {
        Request req=new Request.Builder().type(RequestType.ADD_PARTICIPANT).data(participant).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
    }

    @Override
    public void logout(User user) throws CompetitionException {
        Request req=new Request.Builder().type(RequestType.LOGOUT).data(user).build();
        sendRequest(req);
        Response response=readResponse();
        closeConnection();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new CompetitionException(err);
        }
    }

    @Override
    public List<Participant> getAll() throws CompetitionException {
        Request req=new Request.Builder().type(RequestType.GETALL).build();
        sendRequest(req);
        Response response=readResponse();
        if(response.type()==ResponseType.ERROR){
            String err=response.data().toString();
            throw  new CompetitionException(err);
        }
        return (List<Participant>) response.data();
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

    private void sendRequest(Request request)throws CompetitionException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new CompetitionException("Error sending object "+e);
        }

    }

    private Response readResponse() throws CompetitionException {
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection() throws CompetitionException {
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isUpdate(Response response){
        return response.type()== ResponseType.ADDED_PARTICIPANT;
    }

    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                    }else{
                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

}
