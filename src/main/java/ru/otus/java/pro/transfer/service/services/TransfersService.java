package ru.otus.java.pro.transfer.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.transfer.service.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.transfer.service.entities.Account;
import ru.otus.java.pro.transfer.service.entities.Transfer;
import ru.otus.java.pro.transfer.service.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.transfer.service.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.transfer.service.exceptions_handling.ValidationException;
import ru.otus.java.pro.transfer.service.exceptions_handling.ValidationFieldError;
import ru.otus.java.pro.transfer.service.repositories.AccountsRepository;
import ru.otus.java.pro.transfer.service.repositories.TransfersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransfersService {
    private final TransfersRepository transfersRepository;
    private final AccountsRepository accountsRepository;
    private final TransactionalService transactionalService;

    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        validateExecuteTransferDtoRq(executeTransferDtoRq);
        validateTransferParameters(clientId, executeTransferDtoRq);
    }

    private void validateExecuteTransferDtoRq(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.sourceAccount().length() != 10) {
            errors.add(new ValidationFieldError("sourceAccount", "Длина поля счет отправителя должна составлять 10 символов"));
        }
        if (executeTransferDtoRq.targetAccount().length() != 10) {
            errors.add(new ValidationFieldError("targetAccount", "Длина поля счет получателя должна составлять 10 символов"));
        }
        if (executeTransferDtoRq.amount() <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }

    private void validateTransferParameters(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        Optional<Account> sourceAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.sourceAccount(), clientId, 'N');
        Optional<Account> targetAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.targetAccount(), executeTransferDtoRq.targetClientId(), 'N');

        if (sourceAccount.isEmpty()) {
            throw new ResourceNotFoundException("Счет отправителя не найден");
        }
        if (targetAccount.isEmpty()) {
            throw new ResourceNotFoundException("Счет получателя не найден");
        }
        if (sourceAccount.get().getBalance() < executeTransferDtoRq.amount()) {
            throw new BusinessLogicException("INCORRECT_TRANSFER_AMOUNT","Недостаточно средств для перевода");
        }

        transactionalService.executeTransfer(sourceAccount.get(), targetAccount.get(), executeTransferDtoRq.message(), executeTransferDtoRq.amount());
    }
}
