package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.DecklistService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
        return getString(model, userDecklist);

    }

    @GetMapping("/decklists")
    public String showDecklists(){
        return "decklists";
    }

    private String getString(Model model, Page<Decklist> decklistPage) {
        model.addAttribute("decklistPage", decklistPage);
        int totalPages = decklistPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "createDecklist";
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
}
