package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.repositories.AccountsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountService {
    private final AccountsRepository accountsRepository;

    @Override
    public Optional<Account> getAccountById(String id, String clientId) {
        return accountsRepository.findByIdAndClientIdAndBlockFlag(id, clientId, 'N');
    }

    @Override
    public List<Account> getAllAccounts(String clientId) {
        return accountsRepository.findAllByClientId(clientId);
    }
}
