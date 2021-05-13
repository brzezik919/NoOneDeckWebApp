package io.github.brzezik919.service;

import io.github.brzezik919.model.User;
import io.github.brzezik919.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAllUserStats(String login, String password){
        List<User> loginUser =  this.userRepository.findByLoginAndPassword(login,password); //Change with Keycloak
        if(!loginUser.isEmpty()){
            return loginUser.get(0);
        }
        return null;
    }

    public User getUserById(int id){
        List<User> loginUser = this.userRepository.findById(id);
        if(!loginUser.isEmpty()){
            return loginUser.get(0);
        }
        return null;
    }

    public User getIdByName(String login){
        List<User> loginUser = this.userRepository.findByLogin(login);
        if(!loginUser.isEmpty()){
            return loginUser.get(0);
        }
        return null;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserByName(String login){
        return userRepository.findByLogin(login).get(0);
    }
}
