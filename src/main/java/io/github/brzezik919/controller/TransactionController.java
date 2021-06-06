package io.github.brzezik919.controller;


import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.TransactionMessage;
import io.github.brzezik919.model.User;
import io.github.brzezik919.service.TransactionMessageService;
import io.github.brzezik919.service.TransactionService;
import io.github.brzezik919.service.UserService;
import org.keycloak.adapters.jaas.AbstractKeycloakLoginModule;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class TransactionController{

    private final TransactionService transactionService;
    private final UserService userService;
    private final TransactionMessageService transactionMessageService;

    public TransactionController(TransactionService transactionService, UserService userService, TransactionMessageService transactionMessageService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.transactionMessageService = transactionMessageService;
    }

    @PostMapping
    @RequestMapping("/market/createOffer")
    String createTransaction(@ModelAttribute Transaction transaction, Authentication auth){
        if(transaction.getCard() == null){
            return "redirect:/market";
        }
        transactionService.createOffer(transaction.getCard().getId(), transaction.getDescription(), auth.getName());
        return "redirect:/market";
    }

    @GetMapping("/yourProfile/offer/{id}")
    String showTransactionsHistory(Model model, @PathVariable int id, Authentication auth){
        Transaction transaction = transactionService.findTransaction(id);
        if(Objects.isNull(auth)){
            return "redirect:/yourProfile";
        }
        User userLogIn = userService.getIdByName(auth.getName());
        if(!Objects.nonNull(transaction) || userLogIn.getId() == transaction.getOwnerOffer().getId() || userLogIn.getId() == transaction.getOwnerCard().getId()){
            model.addAttribute("transaction", transaction);
            List<TransactionMessage> messageList = transactionMessageService.findAllMessagesFromTransaction(transaction.getId());
            model.addAttribute("messageTransaction", new TransactionMessage());
            model.addAttribute("messageList", messageList);
            return "/offer";
        }
        return "redirect:/yourProfile";
    }

    @PostMapping("/yourProfile/offer/{id}/sendMessage")
    String sendMessage(@ModelAttribute TransactionMessage message, @PathVariable int id, Authentication auth){
        if(Objects.nonNull(message) && message.getMessageText() == ""){
            return "redirect:/yourProfile/offer/{id}";
        }
        transactionMessageService.sendMessage(id, auth.getName(), message.getMessageText());
        return "redirect:/yourProfile/offer/{id}";
    }
}
