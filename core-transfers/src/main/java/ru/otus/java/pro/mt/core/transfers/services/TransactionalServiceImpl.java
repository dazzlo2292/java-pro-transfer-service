package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.repositories.AccountsRepository;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class TransactionalServiceImpl implements TransactionalService {
    private final TransfersRepository transfersRepository;
    private final AccountsRepository accountsRepository;

    @Override
    public Transfer executeTransfer(Account sourceAccount, Account targetAccount, String message, BigDecimal amount) {
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        accountsRepository.save(sourceAccount);
        accountsRepository.save(targetAccount);

        return transfersRepository.save(new Transfer(
                UUID.randomUUID().toString(),
                sourceAccount.getClientId(),
                targetAccount.getClientId(),
                sourceAccount.getId(),
                targetAccount.getId(),
                message,
                amount));
    }
}
