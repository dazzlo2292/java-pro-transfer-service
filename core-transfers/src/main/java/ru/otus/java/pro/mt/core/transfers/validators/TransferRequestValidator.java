package ru.otus.java.pro.mt.core.transfers.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ValidationException;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ValidationFieldError;
import ru.otus.java.pro.mt.core.transfers.metrics.TransferRequestsMetricsService;
import ru.otus.java.pro.mt.core.transfers.repositories.AccountsRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TransferRequestValidator {
    private final AccountsRepository accountsRepository;

    public void validate(ExecuteTransferDtoRq executeTransferDtoRq) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (executeTransferDtoRq.getSourceAccount().length() != 12) {
            errors.add(new ValidationFieldError("sourceAccount", "Длина поля счет отправителя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.getTargetAccount().length() != 12) {
            errors.add(new ValidationFieldError("targetAccount", "Длина поля счет получателя должна составлять 12 символов"));
        }
        if (executeTransferDtoRq.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.add(new ValidationFieldError("amount", "Сумма перевода должна быть больше 0"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("EXECUTE_TRANSFER_VALIDATION_ERROR", "Проблемы заполнения полей перевода", errors);
        }
    }

    public void validateTransferParameters(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        Optional<Account> sourceAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getSourceAccount(), clientId, 'N');
        Optional<Account> targetAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getTargetAccount(), executeTransferDtoRq.getTargetClientId(), 'N');

        if (sourceAccount.isEmpty()) {
            throw new ResourceNotFoundException("Счет отправителя не найден");
        }
        if (targetAccount.isEmpty()) {
            throw new ResourceNotFoundException("Счет получателя не найден");
        }

        if (sourceAccount.get().getBalance().compareTo(executeTransferDtoRq.getAmount()) < 0) {
            throw new BusinessLogicException("INCORRECT_TRANSFER_AMOUNT","Недостаточно средств для перевода");
        }
    }
}
