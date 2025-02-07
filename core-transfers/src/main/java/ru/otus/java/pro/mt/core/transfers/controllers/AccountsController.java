package ru.otus.java.pro.mt.core.transfers.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.mt.core.transfers.dtos.AccountDto;
import ru.otus.java.pro.mt.core.transfers.dtos.AccountsPageDto;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.mt.core.transfers.services.AccountService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {
    private final AccountService accountsService;

    private static final Function<Account, AccountDto> ENTITY_TO_DTO = a -> new AccountDto(a.getId(), a.getAccountNumber(), a.getClientId(), a.getBalance(), a.getBlockFlag());

    @GetMapping
    public AccountsPageDto getAllAccounts(@RequestHeader(name = "client-id") String clientId) {
        return new AccountsPageDto(
                accountsService
                        .getAllAccounts(clientId)
                        .stream()
                        .map(ENTITY_TO_DTO).collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    public AccountDto getAccountById(@RequestHeader(name = "client-id") String clientId, @PathVariable String id) {
        return ENTITY_TO_DTO.apply(accountsService.getAccountById(id, clientId).orElseThrow(() -> new ResourceNotFoundException("Счет не найден")));
    }
}
