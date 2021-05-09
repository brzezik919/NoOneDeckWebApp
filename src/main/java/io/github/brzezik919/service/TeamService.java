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
        List<User> userList = userRepository.findByLogin(login);
        User user = userList.get(0);
        if(Objects.nonNull(user.getTeam())){
            return user.getTeam();
        }
        return null;
    }

    public List<User> findMembers(Team team){
        return userRepository.findByTeam_Id(team.getId());
    }

    public Team findTeamByCode(String code){
        return teamRepository.findByCode(code);
    }

}
