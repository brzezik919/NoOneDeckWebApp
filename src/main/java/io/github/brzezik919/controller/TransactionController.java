package io.github.brzezik919.controller;


import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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


}
