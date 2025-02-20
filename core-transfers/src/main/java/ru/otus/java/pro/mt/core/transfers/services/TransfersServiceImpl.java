package ru.otus.java.pro.mt.core.transfers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.mt.core.transfers.configs.properties.TransfersProperties;
import ru.otus.java.pro.mt.core.transfers.dtos.ExecuteTransferDtoRq;
import ru.otus.java.pro.mt.core.transfers.dtos.KafkaTransferStatusDto;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.entities.Transfer;
import ru.otus.java.pro.mt.core.transfers.integrations.notifications.KafkaProducer;
import ru.otus.java.pro.mt.core.transfers.metrics.TransferRequestsMetricsService;
import ru.otus.java.pro.mt.core.transfers.repositories.AccountsRepository;
import ru.otus.java.pro.mt.core.transfers.repositories.TransfersRepository;
import ru.otus.java.pro.mt.core.transfers.validators.TransferRequestValidator;

import java.util.List;
import java.util.Optional;

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
    private final TransferRequestsMetricsService transferRequestsMetricsService;

    private static final int MAX_PAGE_SIZE = 100;

    @Override
    public Optional<Transfer> getTransferById(String id, String clientId) {
        return transfersRepository.findByIdAndClientId(id, clientId);
    }

    @Override
    public List<Transfer> getAllTransfers(String clientId, Integer pageNumber, Integer pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        return transfersRepository.findAllByClientId(clientId, PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public void execute(String clientId, ExecuteTransferDtoRq executeTransferDtoRq) {
        transferRequestsMetricsService.incrementTransferRequestsMetric();

        try {
            transferRequestValidator.validate(executeTransferDtoRq);
            transferRequestValidator.validateTransferParameters(clientId, executeTransferDtoRq);

            Optional<Account> sourceAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getSourceAccount(), clientId, 'N');
            Optional<Account> targetAccount = accountsRepository.findByIdAndClientIdAndBlockFlag(executeTransferDtoRq.getTargetAccount(), executeTransferDtoRq.getTargetClientId(), 'N');

            if (sourceAccount.isPresent() && targetAccount.isPresent()) {
                Transfer transfer = transactionalService.executeTransfer(sourceAccount.get(), targetAccount.get(), executeTransferDtoRq.getMessage(), executeTransferDtoRq.getAmount());
                transferRequestsMetricsService.incrementSuccessTransferRequestsMetric();
                kafkaProducer.send(new KafkaTransferStatusDto(transfer.getId(), "EXECUTED"));
            }

            //        // execution
//        if (!limitsService.isLimitEnough(clientId, executeTransferDtoRq.getAmount())) {
//            // ...
//        }
//        if (executeTransferDtoRq.getAmount().compareTo(transfersProperties.getMaxTransferSum()) > 0) {
//            throw new BusinessLogicException("OOPS", "OOPS_CODE");
//        }
        } catch (Exception e) {
            transferRequestsMetricsService.incrementFailedTransferRequestsMetric();
            throw e;
        }
    }
}
