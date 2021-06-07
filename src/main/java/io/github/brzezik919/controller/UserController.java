package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.StateCard;
import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.TeamModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.service.OpinionService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequestMapping
public class UserController {

    private final UserService userService;
    private final CardService cardService;
    private final OpinionService opinionService;

    public UserController(UserService userService, CardService cardService, OpinionService opinionService) {
        this.userService = userService;
        this.cardService = cardService;
        this.opinionService = opinionService;
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

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);

        Page<User> userPage = userService.searchAll(currentPage, pageSize);
        model.addAttribute("search", false);

        return getString(model, userPage);
    }

    @PostMapping("/findUser")
    public String userSearch(@ModelAttribute UserModel user, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        if(user.getNickname().trim().equals("")){
            return "redirect:/findUser";
        }
        user.setNickname(user.getNickname().trim());
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);

        Page <User> userPage = userService.getUserByNickname(user.getNickname(), currentPage, pageSize);
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
        int countCardsForSell = cardService.searchAllCardsUserByState(id, StateCard.FORSALE.toString()).size();
        model.addAttribute("user", user);
        model.addAttribute("countPositive", opinionService.countOpinionByState(id, true));
        model.addAttribute("countNegative", opinionService.countOpinionByState(id, false));
        model.addAttribute("cardsCount", countCardsForSell);
        return "userProfile";
    }

    @GetMapping("/findUser/userProfile/{id}/cards")
    public String getUserProfileCards(Model model, @PathVariable int id, Authentication name, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());
        Page<Card> cardPage = cardService.getCardsByStateAndUserId(StateCard.FORSALE.toString(), id, currentPage, pageSize);
        model.addAttribute("idUser", id);
        return getString(model, userLogIn, cardPage);
    }

    private String getString(Model model, User userLogIn, Page<Card> cardPage) {
        model.addAttribute("cardPage", cardPage);
        int totalPages = cardPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("userLogIn", userLogIn);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
            model.addAttribute("transaction", new Transaction());
        }
        return "cardsUser";
    }
}
