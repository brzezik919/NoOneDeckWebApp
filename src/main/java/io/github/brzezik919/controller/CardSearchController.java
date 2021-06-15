package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.User;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.service.CardService;
import io.github.brzezik919.service.UserService;
import org.springframework.data.domain.Page;
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
@RequestMapping("/cardSearch")
public class CardSearchController {

    private final CardService cardService;
    private final UserService userService;

    public CardSearchController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping
    public String showCardSearch(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        model.addAttribute("user", userLogIn);
        model.addAttribute("card", new CardModel());

        Page<Card> cardPage = cardService.searchAllCardInTeam(name.getName(), currentPage, pageSize);
        if(Objects.isNull(cardPage)){
            return "cardSearch";
        }
        return getString(model, cardPage);

    }

    @PostMapping("/cardname")
    public String cardSearch(@ModelAttribute CardModel card, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(card.getCardName().equals("")){
            return "redirect:/cardSearch";
        }

        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        model.addAttribute("user", userLogIn);
        model.addAttribute("search", true);
        model.addAttribute("searchName", card.getCardName());

        Page<Card> cardPage = cardService.searchAllCardNamesInTeam(card.getCardName(), name.getName(), currentPage, pageSize);
        if(cardPage.isEmpty()){
            Page<Card> cards = cardService.searchAllCardInTeam(name.getName(), currentPage, pageSize);
            model.addAttribute("cardNameNotFound", true);
            return getString(model, cards);
        }
        return getString(model, cardPage);
    }

    @GetMapping("/search")
    public String cardSearchPageable(Model model, @RequestParam("cardName") Optional<String> cardName, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(cardName.get().equals("")){
            return "redirect:/cardSearch";
        }
        int currentPage = page.orElse(0);
        int pageSize = size.orElse(20);

        Page<Card> cardPage = cardService.searchAllCardNamesInTeam(cardName.get(), name.getName(), currentPage, pageSize);
        User userLogIn = userService.getUserByName(name.getName());
        model.addAttribute("user", userLogIn);
        model.addAttribute("search", true);
        model.addAttribute("searchName", cardName.get());

        return getString(model, cardPage);
    }

    private String getString(Model model, Page<Card> cardPage) {
        model.addAttribute("cardPage", cardPage);

        int totalPages = cardPage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("cardList", null);
            model.addAttribute("card", new CardModel());
        }
        return "cardSearch";
    }
}
