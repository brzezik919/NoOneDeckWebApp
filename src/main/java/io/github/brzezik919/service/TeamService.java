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

    public List<User> findMembers(Team team){
        return userRepository.findByTeam_IdAndStatus(team.getId(), true);
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
}
