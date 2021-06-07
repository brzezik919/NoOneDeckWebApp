package io.github.brzezik919.model;

public interface OpinionRepository {
    Opinion save(Opinion entity);
    int countByIdUserAndGrade(int id, boolean grade);
    Opinion findByTransaction_IdAndIdUser(int idTransaction, int idUser);
}