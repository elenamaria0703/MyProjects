package rest.client;

import comp.services.rest.ServiceException;
import model.User;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.Callable;

public class UsersClient {
    public static final String url="http://localhost:8080/competition/users";
    private RestTemplate restTemplate=new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public User[] getAll(){
        return execute(()->restTemplate.getForObject(url,User[].class));
    }
    public User getUser(String id){
        return execute(()->restTemplate.getForObject(String.format("%s/%s",url,id),User.class));
    }
    public User saveUser(User user) {
        return execute(() -> restTemplate.postForObject(url, user, User.class));
    }

    public void updateUser(User user) {
        execute(() -> {
            restTemplate.put(url, user);
            return null;
        });
    }

    public void removeUser(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", url, id));
            return null;
        });
    }
}
