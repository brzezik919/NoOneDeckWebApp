package io.github.brzezik919.controller;

import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TransactionService;
import io.github.brzezik919.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/yourProfile")
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

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
        if(user.getNickname().equals("")) {
            return "redirect:/yourProfile";
        }
        userService.userSetNickname(user.getNickname(), auth.getName());
        return "redirect:/yourProfile";

    }

    @RequestMapping(value="/acceptOffer", method = RequestMethod.PUT, params ="acceptOffer=true")
    String acceptOffer(@ModelAttribute Transaction transaction){
        transactionService.resultOffer(transaction, true);
        return "redirect:/yourProfile";
    }

    @RequestMapping(value="/acceptOffer", method = RequestMethod.PUT, params ="acceptOffer=false")
    String declineOrResignOffer(@ModelAttribute Transaction transaction){
        transactionService.resultOffer(transaction, false);
        return "redirect:/yourProfile";
    }
}
