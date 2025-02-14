package ru.otus.java.pro.mt.core.transfers.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.mt.core.transfers.dtos.AccountDto;
import ru.otus.java.pro.mt.core.transfers.dtos.AccountsPageDto;
import ru.otus.java.pro.mt.core.transfers.entities.Account;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ErrorDto;
import ru.otus.java.pro.mt.core.transfers.exceptions_handling.ResourceNotFoundException;
import ru.otus.java.pro.mt.core.transfers.services.AccountService;

import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Tag(name = "Аккаунты", description = "Методы работы с аккаунтами")
public class AccountsController {
    private final AccountService accountsService;

    private static final Function<Account, AccountDto> ENTITY_TO_DTO = a -> new AccountDto(a.getId(), a.getAccountNumber(), a.getClientId(), a.getBalance(), a.getBlockFlag());

    @GetMapping
    @Operation(
            summary = "Запрос списка аккаунтов клиента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountsPageDto.class))
                    )
            }
    )
    public AccountsPageDto getAllAccounts(
            @Parameter(description = "Идентификатор клиента", required = true, schema = @Schema(type = "string", maxLength = 12, example = "100000000001"))
            @RequestHeader(name = "client-id") String clientId
    ) {
        return new AccountsPageDto(
                accountsService
                        .getAllAccounts(clientId)
                        .stream()
                        .map(ENTITY_TO_DTO).collect(Collectors.toList())
        );
    }

    @Operation(
            summary = "Запрос конкретного аккаунта клиента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AccountDto.class))
                    ),
                    @ApiResponse(
                            description = "Аккаунт не найден", responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public AccountDto getAccountById(
            @Parameter(description = "Идентификатор клиента", required = true, schema = @Schema(type = "string", maxLength = 12, example = "100000000001"))
            @RequestHeader(name = "client-id") String clientId,

            @Parameter(description = "Идентификатор аккаунта", required = true, schema = @Schema(type = "string", maxLength = 12, example = "000000000001"))
            @PathVariable String id
    ) {
        return ENTITY_TO_DTO.apply(accountsService.getAccountById(id, clientId).orElseThrow(() -> new ResourceNotFoundException("Счет не найден")));
    }
}
