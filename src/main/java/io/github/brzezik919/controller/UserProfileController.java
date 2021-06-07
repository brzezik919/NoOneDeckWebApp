package io.github.brzezik919.controller;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.GlobalService;
import io.github.brzezik919.service.OpinionService;
import io.github.brzezik919.service.TransactionService;
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
@RequestMapping("/yourProfile")
public class UserProfileController {

    private final UserService userService;
    private final TransactionService transactionService;
    private final GlobalService globalService;
    private final OpinionService opinionService;

    public UserProfileController(UserService userService, TransactionService transactionService, GlobalService globalService, OpinionService opinionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.globalService = globalService;
        this.opinionService = opinionService;
    }

    @GetMapping
    public String getYouProfile(Model model, @RequestParam("pageActually") Optional<Integer> pageActually, @RequestParam("pageHistory") Optional<Integer> pageHistory, @RequestParam("size") Optional<Integer> size, Authentication name){
        if(Objects.isNull(name)){
            return "redirect:/login";
        }

        int currentPageActually = pageActually.orElse(1);
        int currentPageHistory = pageHistory.orElse(1);
        int pageSize = size.orElse(10);

        User userLogIn = userService.getUserByName(name.getName());


        List<Transaction> transactionList = transactionService.findTransactionsPending(userLogIn.getId());
        List<Transaction> transactionHistory = transactionService.findTransactionHistory(userLogIn.getId());


        Page<Card> cardPageActually = globalService.findPaginatedCard(PageRequest.of(currentPageActually - 1, pageSize), transactionList);
        Page<Card> cardPageHistory = globalService.findPaginatedCard(PageRequest.of(currentPageHistory - 1, pageSize), transactionHistory);

        model.addAttribute("cardPageActually", cardPageActually);
        model.addAttribute("cardPageHistory", cardPageHistory);

        int totalPagesActually = cardPageActually.getTotalPages();
        if(totalPagesActually > 0){
            List<Integer> pageNumbersTransaction = IntStream.rangeClosed(1, totalPagesActually)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersTransaction", pageNumbersTransaction);
        }

        int totalPagesHistory = cardPageHistory.getTotalPages();
        if(totalPagesHistory > 0){
            List<Integer> pageNumbersHistory = IntStream.rangeClosed(1, totalPagesHistory)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbersHistory", pageNumbersHistory);
        }
        model.addAttribute("user", userLogIn);
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("countPositive", opinionService.countOpinionByState(userLogIn.getId(), true));
        model.addAttribute("countNegative", opinionService.countOpinionByState(userLogIn.getId(), false));

        return "yourProfile";
    }

    @PutMapping("/setUsername")
    String getUserNickname(@ModelAttribute User user, Authentication auth){
        if(user.getNickname().trim().equals("") || !auth.isAuthenticated()) {
            return "redirect:/yourProfile";
        }
        userService.userSetNickname(user.getNickname().trim(), auth.getName());
        return "redirect:/yourProfile";
    }

    @RequestMapping(value="/acceptOffer", method = RequestMethod.PUT, params ="acceptOffer=true")
    String acceptOffer(@ModelAttribute Transaction transaction, Authentication auth){
        transactionService.resultOffer(transaction, true, auth.getName());
        return "redirect:/yourProfile";
    }

    @RequestMapping(value="/acceptOffer", method = RequestMethod.PUT, params ="acceptOffer=false")
    String declineOrResignOffer(@ModelAttribute Transaction transaction, Authentication auth){
        transactionService.resultOffer(transaction, false, auth.getName());
        return "redirect:/yourProfile";
    }
}
