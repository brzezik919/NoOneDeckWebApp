package io.github.brzezik919.controller;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.TeamService;
import io.github.brzezik919.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Controller
@RequestMapping
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    public TeamController(TeamService teamService, UserService userService) {
        this.teamService = teamService;
        this.userService = userService;
    }

    @GetMapping("/teamPanel")
    String showTeamPanel(Model model, Authentication auth){
        if(Objects.nonNull(userService.getUserByName(auth.getName()).getTeam())){
            Team teamFound = teamService.findTeamByLogInUser(auth.getName());
            UserModel userModel = new UserModel();
            List<User> memberList = teamService.findMembers(teamFound.getId());
            List<User> candidateList = teamService.findCandidate(teamFound);
            model.addAttribute("memberList", memberList);
            model.addAttribute("candidateList", candidateList);
            model.addAttribute("user", userService.getUserByName(auth.getName()));
            model.addAttribute("candidate", userModel);
            model.addAttribute("teamFound", teamFound);
            model.addAttribute("team", new Team());
            return "teamPanel";
        }
        else{
            return "redirect:/teamJoinPanel";
        }
    }

    @GetMapping("/teamJoinPanel")
    String showTeamJoinPanel(Model model){
        model.addAttribute("team", new Team());
        return "teamJoinPanel";
    }

    @PutMapping("/teamJoinPanel")
    String joinToTeam(@ModelAttribute Team team, Authentication auth){
        if(team.getCode().trim().equals("")) {
            return "redirect:/teamPanel";
        }
        userJoinToTeam(team, auth.getName(), false);
        return "redirect:/teamPanel";
    }

    @PostMapping("/teamJoinPanel")
    String createTeam(@ModelAttribute Team team, Authentication auth){
        if(team.getName().trim().equals("")){
            return "redirect:/teamPanel";
        }
        team.setCode(codeGenerator());
        boolean teamExist = teamService.checkExistTeam(team.getCode(), team.getName().trim());
        if(teamExist){
            teamService.save(team);
            userJoinToTeam(team, auth.getName(), true);
        }
        return "redirect:/teamPanel";
    }

    public void userJoinToTeam(Team team, String name, boolean newTeam){
        Team teamFoundByCode = teamService.findTeamByCode(team.getCode());
        if(teamFoundByCode.getId() != 0){
            User user = userService.getIdByName(name);
            user.setTeam(teamFoundByCode);
            if(newTeam){
                user.setStatus(true);
            }
            else{
                user.setStatus(false);
            }
            userService.save(user);
        }
    }

    @PutMapping("/teamPanel/leaveTheTeam")
    public String leaveTheTeam(@ModelAttribute UserModel model, Authentication auth){
        User user = userService.getUserByName(auth.getName());
        int id = user.getTeam().getId();
        userService.userLeaveTheTeam(auth.getName());
        List<User> memberList = teamService.findMembers(id);
        if (memberList.isEmpty()){
            teamService.delete(id);
        }
        return "/index";
    }

    @RequestMapping(value="/teamPanel/acceptCandidate", method = RequestMethod.PUT, params ="acceptCandidate=true")
    public String acceptUser(@ModelAttribute UserModel candidate, Authentication auth) {
        if(candidate.getNickname().equals("")){
            return "redirect:/teamPanel";
        }
        teamService.changeStatusCandidate(candidate.getNickname(),true, auth.getName());
        return "redirect:/teamPanel";
    }

    @RequestMapping(value="/teamPanel/acceptCandidate", method = RequestMethod.PUT, params ="acceptCandidate=false")
    public String refuseUser(@ModelAttribute UserModel candidate, Authentication auth) {
        if(candidate.getNickname().equals("")){
            return "redirect:/teamPanel";
        }
        teamService.changeStatusCandidate(candidate.getNickname(),false, auth.getName());
        return "redirect:/teamPanel";
    }

    public String codeGenerator(){
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 8;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @PutMapping("/teamPanel/changeDescription")
    public String changeDescriptionTeam(@ModelAttribute Team team){
        if(team.getDescription()==""){
            return "redirect:/teamPanel";
        }
        teamService.changeDescription(team.getId(), team.getDescription());
        return "redirect:/teamPanel";
    }
}
