package bciChallenge.example.controller;

import bciChallenge.example.dao.PhonesDAO;
import bciChallenge.example.dao.UsersDAO;
import java.util.List;
import java.util.Optional;

import bciChallenge.example.model.Phone;
import bciChallenge.example.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class UsersController {
    
    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private PhonesDAO phonesDAO;

    @GetMapping("/users")
    public List<User> getUsers() {
        return (List<User>) usersDAO.findAll();
    }
    
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Long userId) throws ResponseStatusException {
        return usersDAO.findById(userId).orElseThrow(() -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); } );
    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user) throws JsonProcessingException {
        List<User> dbUsers = getUsers();
        for (User dbUser : dbUsers) {
            if (dbUser.getEmail() == user.getEmail()) {
                return "{\"mensaje\": \"El correo ya\n" +
                        "registrado\"}";
            }
        }
        for (Phone phone : user.getPhones()) {
            phonesDAO.save(phone);
        }
        usersDAO.save(user);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper.writeValueAsString(user);
    }
    
    @PutMapping("/users/{userId}")
    void putUser(@RequestBody User user, @PathVariable Long userId) throws ResponseStatusException {
        Optional<User> dbTask = usersDAO.findById(userId);
        if (dbTask.isPresent())
        {
            user.setId(userId);
            usersDAO.save(user);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    
    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable Long userId) throws ResponseStatusException {
        Optional<User> dbTask = usersDAO.findById(userId);
        if (dbTask.isPresent())
        {
            usersDAO.deleteById(userId);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
