package utils;

import rpcprotocol.CompetitionClientRpcRefWorker;
import services.ICompetitionServices;

import java.net.Socket;

public class CompetitionRpcConcurrentServer extends AbsConcurrentServer {
    private ICompetitionServices competitionServer;
    public CompetitionRpcConcurrentServer(int port,ICompetitionServices competitionServices) {
        super(port);
        this.competitionServer=competitionServices;
        System.out.println("Competition - CompetitionRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        CompetitionClientRpcRefWorker worker=new CompetitionClientRpcRefWorker(competitionServer,client);
        System.out.println(client);
        System.out.println(worker);
        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop() throws ServerException {
        System.out.println("Stopping services");
    }
}
