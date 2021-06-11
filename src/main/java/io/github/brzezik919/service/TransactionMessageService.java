package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionMessageService {

    private final TransactionMessageRepository transactionMessageRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public TransactionMessageService(TransactionMessageRepository transactionMessageRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.transactionMessageRepository = transactionMessageRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionMessage> findAllMessagesFromTransaction(int id){
        return transactionMessageRepository.findByTransaction_IdOrderByTime(id);
    }

    public void sendMessage(int id, String login, String message){
        User user = userRepository.findByLogin(login);
        Transaction transaction = transactionRepository.findById(id);
        TransactionMessage messageToSave = new TransactionMessage();
        messageToSave.setIdUser(user.getId());
        messageToSave.setTransaction(transaction);
        messageToSave.setMessageText(message);
        messageToSave.setTime(LocalDateTime.now());
        transactionMessageRepository.save(messageToSave);
    }

}
