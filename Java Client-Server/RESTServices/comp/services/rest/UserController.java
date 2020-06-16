package comp.services.rest;

import comp.repositories.UsersDBRepository;
import model.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@CrossOrigin
@RestController
@RequestMapping("/competition/users")
public class UserController {
    @Autowired
    private UsersDBRepository userRepo;

    @RequestMapping(method= RequestMethod.GET)
    public Iterable<User> getAll(){
        System.out.println("Get all users...");
        return userRepo.finadAll();
    }

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public ResponseEntity<?> findUser(@PathVariable String id){
        User user=userRepo.findOne(id);
        if(user==null)
            return new ResponseEntity<String>("User not found!", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveUser(@RequestBody User user){
        System.out.println("");
        userRepo.save(user);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        String result=userRepo.remove(id);
        if(result==null)
            return new ResponseEntity<String>("User not found!",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<String>("id was successfully deleted",HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User result=userRepo.update(user);
        if(result==null)
            return new ResponseEntity<String>("User not found!",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<User>(user,HttpStatus.OK);
    }
}
