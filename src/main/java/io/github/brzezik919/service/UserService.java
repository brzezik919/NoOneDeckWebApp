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
        List<User> loginUser =  this.userRepository.findByLoginAndPassword(login,password);
        if(!loginUser.isEmpty()){
            System.out.println("Znalazlem");
            return loginUser.get(0);
        }
        return null;
    }
}
