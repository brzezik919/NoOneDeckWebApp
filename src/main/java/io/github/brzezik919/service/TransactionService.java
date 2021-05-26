package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Transaction> findTransactionsPending(int id){
        if(id != 0){
            return transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.pending.toString(), id, StateTransaction.pending.toString());
        }
        return null;
    }

    public List<Transaction> findTransactionHistory(int id){
        if(id != 0){
            List<Transaction> list = transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.accepted.toString(), id, StateTransaction.accepted.toString());
            list.addAll(transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.canceled.toString(), id, StateTransaction.canceled.toString()));
            return list;
        }
        return null;
    }

    public void resultOffer(Transaction transaction, boolean state){
        transaction = transactionRepository.findById(transaction.getId());
        Card card = cardRepository.findById(transaction.getCard().getId());
        if(state){
            card.setUser(transaction.getOwnerOffer());
            card.setState(State.bought.toString());
            transaction.setState(StateTransaction.accepted.toString());
        }
        else{
            card.setState(State.forSell.toString());
            transaction.setState(StateTransaction.canceled.toString());
        }
        cardRepository.save(card);
        transactionRepository.save(transaction);
    }

    public Transaction findTransaction(int id){
        return transactionRepository.findById(id);
    }

}
