package io.github.brzezik919.controller;

import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.DecklistService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class DecklistController {

    private final UserService userService;
    private final DecklistService decklistService;

    public DecklistController(UserService userService, DecklistService decklistService) {
        this.userService = userService;
        this.decklistService = decklistService;
    }

    @GetMapping("/createDecklist")
    public String showCreateDecklistPanel(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Authentication name){
        model.addAttribute("decklist", new Decklist());
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        User userLogIn = userService.getUserByName(name.getName());
        Page<Decklist> userDecklist = decklistService.getAllUserDecklist(userLogIn.getId(), currentPage, pageSize);
        return getString(model, userDecklist, "createDecklist");
    }

    @GetMapping("/decklists")
    public String showDecklists(Model model, @RequestParam("pagePublic") Optional<Integer> pagePublic, @RequestParam("pageTeam") Optional<Integer> pageTeam, @RequestParam("size") Optional<Integer> size, Authentication name){

        int currentTeamPage = pageTeam.orElse(0);
        int currentPublicPage = pagePublic.orElse(0);
        int pageSize = size.orElse(5);
        User userLogIn = userService.getUserByName(name.getName());

        Page<Decklist> userTeamDecklist = decklistService.getAllTeamDecklist(userLogIn.getTeam(), currentTeamPage, pageSize);
        Page<Decklist> userPublicDecklist = decklistService.getAllPublicDecklist(currentPublicPage, pageSize);

        model.addAttribute("pageTeamNumbers", 0);
        model.addAttribute("pagePublicNumbers", 0);
        model.addAttribute("decklistPublicPage", userPublicDecklist);
        model.addAttribute("decklistTeamPage", userTeamDecklist);

        if(userLogIn.getTeam() != null && userTeamDecklist.getTotalPages() > 0){
            List<Integer> pageTeamNumbers = IntStream.rangeClosed(1, userPublicDecklist.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageTeamNumbers", pageTeamNumbers);
        }
        if(userPublicDecklist.getTotalPages() > 0){
            List<Integer> pagePublicNumbers = IntStream.rangeClosed(1, userPublicDecklist.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pagePublicNumbers", pagePublicNumbers);
        }

        return "decklists";
    }

    private String getString(Model model, Page<Decklist> decklistPage, String template) {
        model.addAttribute("decklistPage", decklistPage);
        int totalPages = decklistPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return template;
    }

    @PostMapping("/createDecklist/generate/save")
    public String generateDecklist(Authentication auth, @RequestParam("deck") MultipartFile multipartFile) throws IOException {
        if(StringUtils.cleanPath(multipartFile.getOriginalFilename()) == "") {
            return "redirect:/createDecklist";
        }
        String deck = decklistService.encodingDecklist(multipartFile);
        decklistService.createDecklist(multipartFile.getOriginalFilename(), deck, auth.getName());
        return "redirect:/createDecklist";
    }

    @GetMapping("/decklists/deck/{id}")
    public String showDecklist(Authentication auth, Model model, @PathVariable int id){
        if(Objects.isNull(auth)){
            return "redirect:/decklist";
        }
        Decklist decklist = decklistService.getDecklistById(id);
        model.addAttribute("decklist", decklist);
        model.addAttribute("permissionToDecklist", decklistService.getPermissionToDecklist(auth.getName(), id));
        return "deck";
    }

    @DeleteMapping("/createDecklist/delete/{id}")
    public String deleteDecklist(Authentication auth, Model model, @PathVariable int id){
        User userLogIn = userService.getUserByName(auth.getName());
        Decklist decklist = decklistService.getDecklistById(id);
        if(userLogIn != decklist.getUser()){
            model.addAttribute("permissionToDecklist", decklistService.getPermissionToDecklist(auth.getName(), id));
            return "deck";
        }
        decklistService.deleteById(id);
        return "redirect:/createDecklist";
    }
}
