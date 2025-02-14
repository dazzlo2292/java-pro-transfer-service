package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.dtos.KafkaTransferStatusDto;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.BusinessLogicException;
import ru.otus.java.pro.mt.core.transfers.integrations.notifications.KafkaProducer;
import ru.otus.java.pro.mt.core.transfers.repositories.AccountsRepository;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransfersServiceImpl implements TransfersService {
    private final TransfersRepository transfersRepository;
    private final TransferRequestValidator transferRequestValidator;
    private final TransfersProperties transfersProperties;
    private final LimitsServiceImpl limitsService;
    private final TransactionalService transactionalService;
    private final AccountsRepository accountsRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId) {
        return transfersRepository.findAllByClientId(clientId);
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferRequestValidator.validate(executeTransferDtoRq);
        transferRequestValidator.validateTransferParameters(clientId, executeTransferDtoRq);

        Optional<Account> sourceAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getSourceAccount(), clientId, 'N');
        Optional<Account> targetAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getTargetAccount(), executeTransferDtoRq.getTargetClientId(), 'N');

        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            Transfer transfer = transactionalService.executeTransfer(sourceAccount.get(), targetAccount.get(), executeTransferDtoRq.getMessage(), executeTransferDtoRq.getAmount());
            kafkaProducer.send(new KafkaTransferStatusDto(transfer.getId(), "EXECUTED"));
        }

//        // execution
//        if (!limitsService.isLimitEnough(clientId, executeTransferDtoRq.getAmount())) {
//            // ...
//        }
//        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
//            throw new BusinessLogicException("OOPS", "OOPS_CODE");
//        }
//        Transfer transfer = new Transfer(UUID.randomUUID().toString(), "1", "2", "1", "2", "Demo", BigDecimal.ONE);
//        save(transfer);
    }

    @Override
    public void save(Transfer transfer) {
        transfersRepository.save(transfer);
    }

}
