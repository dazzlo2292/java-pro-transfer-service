package ru.otus.java.pro.transfer.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.transfer.service.entities.Transfer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, String> {
    @Query("SELECT t FROM Transfer t WHERE t.id = :id AND (t.clientId = :clientId OR t.targetClientId = :clientId)")
    Optional<Transfer> findByIdAndClientId(String id, String clientId);

    @Query("SELECT t FROM Transfer t WHERE t.clientId = :clientId OR t.targetClientId = :clientId")
    List<Transfer> findAllByClientId(String clientId);
}
