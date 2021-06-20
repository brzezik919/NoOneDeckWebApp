package io.github.brzezik919.controller;

import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.DecklistRepository;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardDecklistModel;
import io.github.brzezik919.model.projection.DecklistModel;
import io.github.brzezik919.service.DecklistService;
import io.github.brzezik919.service.GlobalService;
import io.github.brzezik919.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    private final GlobalService globalService;

    public DecklistController(UserService userService, DecklistService decklistService, GlobalService globalService) {
        this.userService = userService;
        this.decklistService = decklistService;
        this.globalService = globalService;
    }

    @GetMapping("/createDecklist")
    public String showCreateDecklistPanel(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Authentication name){
        model.addAttribute("decklist", new Decklist());
        model.addAttribute("generatedList", false);
        model.addAttribute("decklistModel", new DecklistModel());
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

    @PostMapping("/createDecklist/generate")
    public String generateDecklist(Authentication auth, Model model, @RequestParam("deck") MultipartFile multipartFile, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) throws IOException {
        if(StringUtils.cleanPath(multipartFile.getOriginalFilename()) == "" || !globalService.getExtensionFile(multipartFile.getOriginalFilename()).equals("ydk") || multipartFile.isEmpty()) {
            model.addAttribute("file", false);
            return "redirect:/createDecklist";
        }
        String deck = decklistService.encodingDecklist(multipartFile);
        model.addAttribute("deckCode", deck);
        model.addAttribute("decklist", new Decklist());
        model.addAttribute("generatedList", true);
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(5);
        List<CardDecklistModel> orderDecklist = decklistService.getCountCardDecklist(decklistService.decodingDecklist(deck));
        if(orderDecklist == null){ return "redirect:/createDecklist"; }
        model.addAttribute("countDecklist", orderDecklist);
        User userLogIn = userService.getUserByName(auth.getName());
        Page<Decklist> userDecklist = decklistService.getAllUserDecklist(userLogIn.getId(), currentPage, pageSize);
        return getString(model, userDecklist, "createDecklist");
    }

    @PostMapping("/createDecklist/generate/save")
    public String generateDecklist(@ModelAttribute Decklist decklist, Authentication auth, @RequestParam("deck") String deck){
        if(deck.trim() == "" ||  decklist.getName().trim() == "") {
            return "redirect:/createDecklist";
        }
        decklistService.createDecklist(decklist.getName().trim(), deck.trim(), auth.getName());
        return "redirect:/createDecklist";
    }

    @GetMapping("/decklists/deck/{id}")
    public String showDecklist(Authentication auth, Model model, @PathVariable int id){
        if(Objects.isNull(auth)){
            return "redirect:/decklist";
        }
        Decklist decklist = decklistService.getDecklistById(id);
        String deck = decklistService.decodingDecklist(decklist.getDeck());
        List<CardDecklistModel> orderDecklist = decklistService.getCountCardDecklist(deck);
        model.addAttribute("countDecklist", orderDecklist);
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

    @PutMapping("/createDecklist/change/{id}")
    public String changePermissionDecklist(Authentication auth, @ModelAttribute DecklistModel decklistModel, @PathVariable int id){
        User userLogIn = userService.getUserByName(auth.getName());
        Decklist decklist = decklistService.getDecklistById(id);
        if(decklist.getUser() != userLogIn){
            return "redirect:/createDecklist";
        }
        decklistService.changePermissionDecklist(decklist.getId(), decklistModel.getPermission());
        return "redirect:/createDecklist";
    }
}
