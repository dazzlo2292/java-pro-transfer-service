package ru.otus.java.pro.mt.core.transfers.services;

import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;

import java.math.BigDecimal;

public interface TransactionalService {
    Transfer executeTransfer(Account sourceAccount, Account targetAccount, String message, BigDecimal amount);
}
