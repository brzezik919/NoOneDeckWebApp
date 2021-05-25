package io.github.brzezik919.controller;


import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.service.TransactionService;
import org.keycloak.adapters.jaas.AbstractKeycloakLoginModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
public class TransactionController{

    @Autowired
    TransactionService transactionService;

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
    String showOfferHistory(Model model, @PathVariable int id, Authentication auth){
        Transaction transaction = transactionService.findTransaction(id);
        if(!Objects.nonNull(transaction) || !auth.isAuthenticated()){
            return "/index";
        }
        model.addAttribute("transaction", transaction);
        return "/offer";
    }



}
