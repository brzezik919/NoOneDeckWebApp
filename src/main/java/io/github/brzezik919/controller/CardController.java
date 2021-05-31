package io.github.brzezik919.controller;


import io.github.brzezik919.model.*;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.GlobalService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/cardPanel")
public class CardController {

    private final  CardService cardService;
    private final UserService userService;
    private final GlobalService globalService;

    public CardController(CardService cardService, UserService userService, GlobalService globalService) {
        this.cardService = cardService;
        this.userService = userService;
        this.globalService = globalService;
    }

    @GetMapping
    public String showCardSearch(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cards = cardService.getAllStats(name.getName());
        Page<Card> cardPage = globalService.findPaginatedCard(PageRequest.of(currentPage - 1, pageSize), cards);
        model.addAttribute("search", false);

        return getString(model, userLogIn, cardPage);
    }

    @PostMapping("/cardSearch")
    public String cardSearch(@ModelAttribute CardModel card, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(card.getCardName().equals("")){
            return "redirect:/cardPanel";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cardList = cardService.searchAllCardsNames(card.getCardName(), name.getName());
        Page<Card> cardPage = globalService.findPaginatedCard(PageRequest.of(currentPage - 1, pageSize), cardList);
        model.addAttribute("search", true);
        model.addAttribute("searchName", card.getCardName());
        return getString(model, userLogIn, cardPage);
    }

    @GetMapping("/cardSearch")
    public String cardSearchPageable(Model model, @RequestParam("cardName") Optional<String> cardName, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(cardName.get().equals("")){
            return "redirect:/cardPanel";
        }
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cardList = cardService.searchAllCardsNames(cardName.get(), name.getName());
        Page<Card> cardPage = globalService.findPaginatedCard(PageRequest.of(currentPage - 1, pageSize), cardList);
        model.addAttribute("search", true);
        model.addAttribute("searchName", cardName.get());

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
            model.addAttribute("cardList", null);
            model.addAttribute("card", new CardModel());
            model.addAttribute("user", userService.getUserByName(userLogIn.getLogin()));
        }
        return "cardPanel";
    }

    @PostMapping("/addCard")
    String addCard(@ModelAttribute CardModel card, Authentication name){
        if(card.getCardName().trim().equals("")) {
            return "redirect:/cardPanel";
        }
        CardName cardNameFound = this.cardService.getCardName(card.getCardName().trim());

        if(Objects.nonNull(cardNameFound)){
            Card cardToSave = card.newCard(cardNameFound, userService.getAllUserStats(name.getName()));
            cardService.save(cardToSave);
        } else {
            return "redirect:/cardPanel";
        }
        return "redirect:/cardPanel";
    }

    @PutMapping("/cardPanelEditCard")
    String changeStateCard(@ModelAttribute CardModel card, Authentication auth){
        if(Objects.isNull(cardService.searchCardById(card.getId()))){
            return "redirect:/cardPanel";
        }
        User user = userService.getUserByName(auth.getName());
        cardService.changeState(user.getId(), card.getId(), card.getState(), card.getNote().trim());
        return "redirect:/cardPanel";
    }

    @DeleteMapping("/cardPanelDeleteCard")
    String deleteCard(@ModelAttribute CardModel card, Authentication auth){
        if(Objects.isNull(cardService.searchCardById(card.getId()))){
            return "redirect:/cardPanel";
        }
        User user = userService.getUserByName(auth.getName());
        cardService.delete(user.getId(), card.getId());
        return "redirect:/cardPanel";
    }
}