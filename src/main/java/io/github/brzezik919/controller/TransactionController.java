package io.github.brzezik919.controller;


import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TransactionService;
import io.github.brzezik919.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class TransactionController{

    private final TransactionService transactionService;
    private final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/market/createOffer")
    String createTransaction(@ModelAttribute Transaction transaction, Authentication auth){
        if(transaction.getCard() == null){
            return "redirect:/market";
        }
        transactionService.createOffer(transaction, auth.getName());
        return "redirect:/market";
    }

    @GetMapping("/yourProfile/offer/{id}")
    String showTransactionsHistory(Model model, @PathVariable int id, Authentication auth){
        Transaction transaction = transactionService.findTransaction(id);
        User userLogIn = userService.getIdByName(auth.getName());
        if(!Objects.nonNull(transaction) || !auth.isAuthenticated() || userLogIn.getId() == transaction.getOwnerOffer().getId() || userLogIn.getId() == transaction.getOwnerCard().getId()){
            model.addAttribute("transaction", transaction);
            return "/offer";
        }
        return "/index";
    }
}
