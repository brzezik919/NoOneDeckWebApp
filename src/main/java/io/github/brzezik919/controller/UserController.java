package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.TeamModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.GlobalService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping
public class UserController {

    private final UserService userService;
    private final GlobalService globalService;

    public UserController(UserService userService, GlobalService globalService) {
        this.userService = userService;
        this.globalService = globalService;
    }

    @GetMapping
    public String showIndex(Principal principal){
        return principal != null ? "index" : "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(Authentication auth){
        User userLogIn = userService.getUserByName(auth.getName());
        if(Objects.isNull(userLogIn)){
            User userToCreate = new User();
            userToCreate.setLogin(auth.getName());
            userToCreate.setNickname("Unknown");
            userService.save(userToCreate);
        }
        return "redirect:";
    }

    @GetMapping("/findUser")
    public String showFindUserPanel(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        model.addAttribute("userSearch", new UserModel());

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);

        List<User> users = userService.searchAll();
        Page<User> userPage = globalService.findPaginatedUser(PageRequest.of(currentPage - 1, pageSize), users);
        model.addAttribute("search", false);

        return getString(model, userPage);
    }

    @PostMapping("/findUser")
    public String userSearch(@ModelAttribute UserModel user, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        if(user.getNickname().trim().equals("")){
            return "redirect:/findUser";
        }
        user.setNickname(user.getNickname().trim());
        System.out.println(user.getNickname());
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(3);

        List<User> users = new ArrayList<>();
        users.add(userService.getUserByNickname(user.getNickname()));

        if(Objects.isNull(userService.getUserByNickname(user.getNickname()))){
            return "redirect:/findUser";
        }
        Page<User> userPage = globalService.findPaginatedUser(PageRequest.of(currentPage - 1, pageSize), users);
        model.addAttribute("search", true);

        return getString(model, userPage);
    }

    private String getString(Model model, Page<User> userPage) {
        model.addAttribute("userPage", userPage);

        int totalPages = userPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("team", new TeamModel());
            model.addAttribute("user", new UserModel());
        }
        return "findUser";
    }

    @GetMapping("/findUser/userProfile/{id}")
    public String getUserProfile(Model model, @PathVariable int id, Authentication auth){
        if(!auth.isAuthenticated()){
            return "redirect:/login";
        }
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "userProfile";
    }
}
