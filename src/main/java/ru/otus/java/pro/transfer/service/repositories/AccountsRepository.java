package ru.otus.java.pro.transfer.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.java.pro.transfer.service.entities.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Account, String> {
    Optional<Account> findByIdAndClientIdAndBlockFlag(String id, String clientId, char blockFlag);
    List<Account> findAllByClientId(String clientId);
}
