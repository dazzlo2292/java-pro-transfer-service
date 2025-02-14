package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Аккаунт клиента")
public record AccountDto(
        @Schema(
                description = "Идентификатор аккаунта",
                type = "string",
                example = "000000000001",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 12
        )
        String id,

        @Schema(
                description = "Номер аккаунта",
                type = "string",
                example = "1111222233334444",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 16
        )
        String accountNumber,

        @Schema(
                description = "Идентификатор клиента",
                type = "string",
                example = "100000000001",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 12
        )
        String clientId,

        @Schema(
                description = "Баланс счета",
                type = "BigDecimal",
                example = "95.3",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "0.0"
        )
        BigDecimal balance,

        @Schema(
                description = "Признак блокировки аккаунта",
                type = "char",
                example = "N",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "0.0"
        )
        char blockFlag
) {
}
