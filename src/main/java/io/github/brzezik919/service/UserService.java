package io.github.brzezik919.service;

import io.github.brzezik919.model.User;
import io.github.brzezik919.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAllUserStats(String login){
        User loginUser = this.userRepository.findByLogin(login);
        if(Objects.nonNull(loginUser)){
            return loginUser;
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
        User loginUser = this.userRepository.findByLogin(login);
        if(Objects.nonNull(loginUser)){
            return loginUser;
        }
        return null;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public User getUserByName(String login){
        return userRepository.findByLogin(login);
    }

    public User getUserByNickname(String login){
        if(Objects.nonNull(userRepository.findByNickname(login))){
            System.out.println("siema");
            return userRepository.findByNickname(login);
        }
        return null;
    }

    public void userSetNickname(String nickname, String login){
        User userToSave = getUserByName(login);
        userToSave.setNickname(nickname);
        userRepository.save(userToSave);
    }

    public void userLeaveTheTeam(String login){
        User userToSave = getUserByName(login);
        userToSave.setTeam(null);
        userToSave.setStatus(false);
        userRepository.save(userToSave);
    }

    public List<User> searchAll(){
        return userRepository.findAll();
    }
}
