package ru.otus.java.pro.mt.core.transfers.services;

import ru.otus.java.pro.mt.core.transfers.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountById(String id, String clientId);
    List<Account> getAllAccounts(String clientId);
}
