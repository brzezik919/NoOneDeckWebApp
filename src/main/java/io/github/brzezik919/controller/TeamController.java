package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.TeamService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teamPanel")
public class TeamController {
    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    private final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @GetMapping
    String CardPanel(Model model){
        Team teamFound = teamService.findTeamByLogInUser("admin");
        List<User> memberList = teamService.findMembers(teamFound);
        model.addAttribute("memberList", memberList);
        model.addAttribute("user", new User());
        model.addAttribute("teamFound", teamFound);
        model.addAttribute("team", new Team());
        return "teamPanel";
    }

    @PutMapping
    String joinToTeam(Model model, @ModelAttribute Team team){
        if(team.getCode().equals("")) {
            return "redirect:/teamPanel";
        }
        Team teamFoundByCode = teamService.findTeamByCode(team.getCode());
        if(teamFoundByCode.getId() != 0){
            //Change in Logged user id_team from null -> teamFoundByCode.getId();
            User user = userService.getIdByName("user"); //This is static, in production this will be Session Login User.
            user.setTeam(teamFoundByCode);
            userService.save(user);
        }
        return "redirect:/teamPanel";

    }
}
