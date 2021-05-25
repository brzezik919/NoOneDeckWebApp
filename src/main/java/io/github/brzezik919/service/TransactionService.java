package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(CardRepository cardRepository, TransactionRepository transactionRepository, UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public void createOffer(Transaction transaction, String login){
        transaction.setOwnerCard(transaction.getCard().getUser());
        User user = userRepository.findByLogin(login);
        Card card = cardRepository.findById(transaction.getCard().getId());
        transaction.setOwnerOffer(user);
        transaction.setState(StateTransaction.pending.toString());
        card.setState(State.pending.toString());
        cardRepository.save(card);
        transactionRepository.save(transaction);
    }

}
