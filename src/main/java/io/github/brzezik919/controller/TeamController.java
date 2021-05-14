package io.github.brzezik919.controller;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TeamService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class TeamController {
    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @GetMapping("/teamPanel")
    String TeamPanel(Model model, Authentication auth){
        if(Objects.nonNull(userService.getUserByName(auth.getName()).getTeam())){ //Change with Keycloak
            Team teamFound = teamService.findTeamByLogInUser(auth.getName()); //Change with Keycloak
            List<User> memberList = teamService.findMembers(teamFound);
            model.addAttribute("memberList", memberList);
            model.addAttribute("user", new User());
            model.addAttribute("teamFound", teamFound);
            model.addAttribute("team", new Team());
            return "teamPanel";
        }
        else{
            return "redirect:/teamJoinPanel";
        }
    }

    @GetMapping("/teamJoinPanel")
    String TeamJoinPanel(Model model){
        model.addAttribute("team", new Team());
        return "teamJoinPanel";
    }

    @PutMapping("/teamJoinPanel")
    String joinToTeam(@ModelAttribute Team team, Authentication auth){
        if(team.getCode().equals("")) {
            return "redirect:/teamPanel";
        }
        userJoinToTeam(team, auth.getName());
        return "redirect:/teamPanel";

    }

    @PostMapping("/teamJoinPanel")
    String createTeam(@ModelAttribute Team team, Authentication auth){
        if(team.getName().equals("")){
            return "redirect:/teamPanel";
        }
        team.setCode("1234567"); //This is static, in production this will be Random Generate Function.
        teamService.save(team);
        userJoinToTeam(team, auth.getName());
        return "redirect:/teamPanel";
    }

    public void userJoinToTeam(Team team, String name){
        Team teamFoundByCode = teamService.findTeamByCode(team.getCode());
        if(teamFoundByCode.getId() != 0){
            User user = userService.getIdByName(name);
            user.setTeam(teamFoundByCode);
            userService.save(user);
        }
    }
}
