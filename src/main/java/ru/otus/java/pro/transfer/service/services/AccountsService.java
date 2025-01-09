package ru.otus.java.pro.transfer.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.transfer.service.entities.Account;
import ru.otus.java.pro.transfer.service.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository accountsRepository;

    public Optional<Account> getAccountById(String id, String clientId) {
        return accountsRepository.findByIdAndClientIdAndBlockFlag(id, clientId, 'N');
    }

    public List<Account> getAllAccounts(String clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }
}
