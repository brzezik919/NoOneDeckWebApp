package io.github.brzezik919.service;

import io.github.brzezik919.model.*;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OpinionService {

    private final OpinionRepository opinionRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public OpinionService(OpinionRepository opinionRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.opinionRepository = opinionRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public void setGrade(int id, boolean grade, String login){
        User userLogIn = userRepository.findByLogin(login);
        Transaction transaction = transactionRepository.findById(id);
        int idUserOpinion;
        if(isCurrentUserOwnsTransaction(transaction, userLogIn)){
            idUserOpinion = transaction.getOwnerOffer().getId();
        }
        else{
            idUserOpinion = transaction.getOwnerCard().getId();
        }
        Opinion foundGrade = gradeExist(id, idUserOpinion);
        if(Objects.isNull(foundGrade)){
            Opinion gradeToSave = new Opinion();
            gradeToSave.setTransaction(transaction);
            gradeToSave.setIdUser(idUserOpinion);
            gradeToSave.setGrade(grade);
            opinionRepository.save(gradeToSave);
        }
    }

    public boolean isCurrentUserOwnsTransaction(Transaction transaction, User user){
        return transaction.getOwnerCard().equals(user);
    }

    public Opinion gradeExist(int id, int idUser){
        return opinionRepository.findByTransaction_IdAndIdUser(id, idUser);
    }

    public int countOpinionByState(int id, boolean grade){
        return opinionRepository.countByIdUserAndGrade(id, grade);
    }
}
