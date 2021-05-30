package io.github.brzezik919.controller;

import io.github.brzezik919.model.*;
import io.github.brzezik919.model.projection.CardModel;
import io.github.brzezik919.model.projection.UserModel;
import io.github.brzezik919.service.CardService;
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
@RequestMapping("/market")
public class CardMarketController {

    private final CardService cardService;
    private final UserService userService;

    public CardMarketController(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping
    public String showCardMarket(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cards = cardService.getCardsByState(StateCard.FORSALE.toString());
        Page<Card> cardPage = cardService.findPaginated(PageRequest.of(currentPage - 1, pageSize), cards);
        model.addAttribute("search", false);

        return getString(model, userLogIn, cardPage);
    }

    @PostMapping("/cardSearch/cardname")
    public String cardSearchCardMarket(@ModelAttribute CardModel card, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(card.getCardName().equals("")){
            return "redirect:/market";
        }
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cardList = cardService.searchAllCardNamesForSell(card.getCardName(), StateCard.FORSALE.toString());
        Page<Card> cardPage = cardService.findPaginated(PageRequest.of(currentPage - 1, pageSize), cardList);
        model.addAttribute("search", true);
        model.addAttribute("searchName", card.getCardName());
        return getString(model, userLogIn, cardPage);
    }

    @GetMapping("/cardSearch")
    public String cardSearchCardMarketPageable(Model model, @RequestParam("cardName") Optional<String> cardName, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,  Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }
        if(cardName.get().equals("")){
            return "redirect:/market";
        }
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(20);
        User userLogIn = userService.getUserByName(name.getName());

        List<Card> cardList = cardService.searchAllCardNamesForSell(cardName.get(), StateCard.FORSALE.toString());
        Page<Card> cardPage = cardService.findPaginated(PageRequest.of(currentPage - 1, pageSize), cardList);
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
            model.addAttribute("userLogIn", userLogIn);
            model.addAttribute("user", new UserModel());
            model.addAttribute("card", new CardModel());
            model.addAttribute("transaction", new Transaction());
        }
        return "market";
    }
}
