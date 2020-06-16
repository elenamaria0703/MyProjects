package rpcprotocol;

import model.Participant;
import model.User;
import services.CompetitionException;
import services.ICompetitionObserver;
import services.ICompetitionServices;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class CompetitionClientRpcRefWorker implements Runnable,ICompetitionObserver {
    private ICompetitionServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public CompetitionClientRpcRefWorker(ICompetitionServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private static Response okResponse=new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).getType();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.getType());
        User user= (User) request.getData();
        try {
            server.login(user,this);
            return okResponse;
        } catch (CompetitionException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request ..."+request.getType());
        User user= (User) request.getData();
        try {
            server.logout(user,this);
            connected=false;
            return okResponse;
        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_PARTICIPANT(Request request){
        System.out.println("Add participant request ...");
        Participant participant= (Participant) request.getData();
        try {
            server.addParticipant(participant);
            return okResponse;
        } catch (CompetitionException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGETALL(Request request){
        System.out.println("GetAll request....");
        try{
            List<Participant> participants=server.getAll();
            return  new Response.Builder().type(ResponseType.GETALL).data(participants).build();
        }catch (CompetitionException e){
            return  new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    @Override
    public void newParticipantAdded(Participant participant) throws CompetitionException {
        Response resp=new Response.Builder().type(ResponseType.ADDED_PARTICIPANT).data(participant).build();
        System.out.println("Participant was add"+ participant);
        try{
            sendResponse(resp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
