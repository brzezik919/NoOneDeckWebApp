package io.github.brzezik919.controller;

import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TransactionService;
import io.github.brzezik919.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/yourProfile")
public class UserProfileController {

    private final UserService userService;
    private final TransactionService transactionService;

    public UserProfileController(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @GetMapping
    String getYouProfile(Model model, Principal name){
        User userLogIn = userService.getUserByName(name.getName());
        List<Transaction> transactionList = transactionService.findTransactionsPending(userLogIn.getId());
        List<Transaction> transactionHistory = transactionService.findTransactionHistory(userLogIn.getId());
        model.addAttribute("user", userLogIn);
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("transactionHistory", transactionHistory);
        model.addAttribute("transaction", new Transaction());
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
