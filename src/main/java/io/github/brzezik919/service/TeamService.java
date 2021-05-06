package io.github.brzezik919.service;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    private Team team;
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }


}
