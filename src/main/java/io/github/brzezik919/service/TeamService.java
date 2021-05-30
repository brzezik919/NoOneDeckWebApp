package io.github.brzezik919.service;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TeamService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public TeamService(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
    }

    public Team findTeamByLogInUser(String login){
        User user = userRepository.findByLogin(login);
        if(Objects.nonNull(user.getTeam())){
            return user.getTeam();
        }
        return null;
    }

    public List<User> findMembers(int id){
        return userRepository.findByTeam_IdAndStatus(id, true);
    }

    public List<User> findCandidate(Team team){ return userRepository.findByTeam_IdAndStatus(team.getId(), false);}

    public Team findTeamByCode(String code){
        return teamRepository.findByCode(code);
    }

    public void save(Team team){ teamRepository.save(team);}

    public void delete(int id){teamRepository.deleteById(id);}

    public void changeStatusCandidate(String nickname, boolean state){
        User user = userRepository.findByNickname(nickname);
        if(state){
            user.setStatus(true);
        }
        else{
            user.setTeam(null);
        }
        userRepository.save(user);
    }

    public boolean checkExistTeam(String code, String name){
        if (Objects.isNull(teamRepository.findByName(name))){
            if(Objects.isNull(teamRepository.findByCode(code))){
                return true;
            }
            return false;
        }
        return false;
    }

    public void changeDescription(int id, String description){
        Team team = teamRepository.findById(id);
        team.setDescription(description);
        teamRepository.save(team);
    }
}
