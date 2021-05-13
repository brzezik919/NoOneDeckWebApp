package io.github.brzezik919.controller;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TeamService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    String TeamPanel(Model model){
        if(Objects.nonNull(userService.getUserByName("user").getTeam())){ //Change with Keycloak
            Team teamFound = teamService.findTeamByLogInUser("user"); //Change with Keycloak
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
    String joinToTeam(@ModelAttribute Team team){
        if(team.getCode().equals("")) {
            return "redirect:/teamPanel";
        }
        Team teamFoundByCode = teamService.findTeamByCode(team.getCode());
        if(teamFoundByCode.getId() != 0){
            User user = userService.getIdByName("user"); //This is static, in production this will be Session Login User. //Change with Keycloak
            user.setTeam(teamFoundByCode);
            userService.save(user);
        }
        return "redirect:/teamPanel";

    }

    @PostMapping("/teamJoinPanel")
    String createTeam(@ModelAttribute Team team){
        if(team.getName().equals("")){
            return "redirect:/teamPanel";
        }
        System.out.println(team.getName());
        team.setCode("1234567"); //This is static, in production this will be Random Generate Function.
        teamService.save(team);
        joinToTeam(team);
        return "redirect:/teamPanel";
    }
}
