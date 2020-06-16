package start;

import comp.services.rest.ServiceException;
import model.User;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rest.client.UsersClient;

public class StartRestClient {
    private static final UsersClient client=new UsersClient();

    public static void main(String[] args) {
        try{
            show(()->client.removeUser("alex"));
            show(()->client.saveUser(new User("andrei","aaa")));
            show(()->client.updateUser(new User("tudor","ttt")));
            show(()->{
                User[] res=client.getAll();
                for(User u:res){
                    System.out.println(u.getUsername()+": "+u.getPassword());
                }
            });

        }catch (RestClientException e){
            e.printStackTrace();
        }
    }
    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}

