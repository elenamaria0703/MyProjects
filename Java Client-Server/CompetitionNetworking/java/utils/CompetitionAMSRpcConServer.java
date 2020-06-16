package utils;

import rpcprotocol.CompetitionClientRpcRefWorker;
import rpcprotocol.ams.CompClientAMSRefWorker;
import services.ICompServicesAMS;

import java.net.Socket;

public class CompetitionAMSRpcConServer extends AbsConcurrentServer {
    private ICompServicesAMS compServices;

    public CompetitionAMSRpcConServer(int port, ICompServicesAMS compServices) {
        super(port);
        this.compServices = compServices;
    }

    @Override
    protected Thread createWorker(Socket client) {
        CompClientAMSRefWorker worker=new CompClientAMSRefWorker(compServices, client);

        Thread tw=new Thread(worker);
        return tw;
    }
}
