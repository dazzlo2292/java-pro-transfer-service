package ru.otus.java.pro.mt.core.transfers.services;

import ru.otus.java.pro.mt.core.transfers.entities.Account;

import java.math.BigDecimal;

public interface TransactionalService {
    void executeTransfer(Account sourceAccount, Account targetAccount, String message, BigDecimal amount);
}
