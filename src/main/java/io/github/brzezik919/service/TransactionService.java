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

    public void createOffer(int id, String description, String login){
        Card card = cardRepository.findById(id);
        if(card.getState().equals(StateCard.FORSALE.toString())){
            User user = userRepository.findByLogin(login);
            Transaction transaction = new Transaction();
            transaction.setDescription(description.trim());
            transaction.setState(StateTransaction.PENDING.toString());
            transaction.setCard(card);
            transaction.setOwnerCard(card.getUser());
            transaction.setOwnerOffer(user);
            card.setState(StateCard.PENDING.toString());
            cardRepository.save(card);
            transactionRepository.save(transaction);
        }
    }

    public List<Transaction> findTransactionsPending(int id){
        if(id != 0){
            return transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.PENDING.toString(), id, StateTransaction.PENDING.toString());
        }
        return null;
    }

    public List<Transaction> findTransactionHistory(int id){
        if(id != 0){
            List<Transaction> list = transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.ACCEPTED.toString(), id, StateTransaction.ACCEPTED.toString());
            list.addAll(transactionRepository.findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState(id, StateTransaction.CANCELED.toString(), id, StateTransaction.CANCELED.toString()));
            return list;
        }
        return null;
    }

    public void resultOffer(Transaction transaction, boolean state, String login){
        transaction = transactionRepository.findById(transaction.getId());
        Card card = cardRepository.findById(transaction.getCard().getId());
        User userLogIn = userRepository.findByLogin(login);
        if(transaction.getState().equals(StateTransaction.PENDING.toString()) && card.getState().equals(StateCard.PENDING.toString()) && (transaction.getOwnerCard().getId() == userLogIn.getId() || transaction.getOwnerOffer().getId() == userLogIn.getId())){
            if(state){
                card.setUser(transaction.getOwnerOffer());
                card.setState(StateCard.BOUGHT.toString());
                transaction.setState(StateTransaction.ACCEPTED.toString());
            }
            else{
                card.setState(StateCard.FORSALE.toString());
                transaction.setState(StateTransaction.CANCELED.toString());
            }
            cardRepository.save(card);
            transactionRepository.save(transaction);
        }
    }

    public Transaction findTransaction(int id){
        return transactionRepository.findById(id);
    }

}
