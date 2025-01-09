package ru.otus.java.pro.transfer.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.transfer.service.dtos.*;
import ru.otus.java.pro.transfer.service.entities.Account;
import ru.otus.java.pro.transfer.service.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.transfer.service.services.AccountsService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {
    private final AccountsService accountsService;

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
