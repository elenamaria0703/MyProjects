import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import utils.AbstractServer;
import utils.CompetitionAMSRpcConServer;
import utils.ServerException;

public class StartAMSServer {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-server.xml");
        AbstractServer server=context.getBean("compTCPServer", CompetitionAMSRpcConServer.class);
        try{
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }
    }
}
