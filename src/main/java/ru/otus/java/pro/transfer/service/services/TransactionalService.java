package ru.otus.java.pro.transfer.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.java.pro.transfer.service.entities.Account;
import ru.otus.java.pro.transfer.service.entities.Transfer;
import ru.otus.java.pro.transfer.service.repositories.AccountsRepository;
import ru.otus.java.pro.transfer.service.repositories.TransfersRepository;

import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
public class TransactionalService {
    private final TransfersRepository transfersRepository;
    private final AccountsRepository accountsRepository;

    public void executeTransfer(Account sourceAccount, Account targetAccount, String message, int amount) {
        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        accountsRepository.save(sourceAccount);
        accountsRepository.save(targetAccount);

        transfersRepository.save(new Transfer(
                UUID.randomUUID().toString(),
                sourceAccount.getClientId(),
                targetAccount.getClientId(),
                sourceAccount.getId(),
                targetAccount.getId(),
                message,
                amount)
        );
    }
}
