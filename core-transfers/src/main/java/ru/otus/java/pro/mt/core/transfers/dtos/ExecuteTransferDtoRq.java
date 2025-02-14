package ru.otus.java.pro.mt.core.transfers.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Запрос на исполнение перевода")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ExecuteTransferDtoRq {
    @Schema(
            description = "Идентификатор клиента получателя",
            type = "string",
            example = "100000000001",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 12
    )
    private String targetClientId;

    @Schema(
            description = "Идентификатор аккаунта-источника",
            type = "string",
            example = "000000000001",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 12,
            maxLength = 12
    )
    private String sourceAccount;

    @Schema(
            description = "Идентификатор целевого аккаунта",
            type = "string",
            example = "000000000002",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 12,
            maxLength = 12
    )
    private String targetAccount;

    @Schema(
            description = "Сообщение получателю",
            type = "string",
            example = "Cashback",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 255
    )
    private String message;

    @Schema(
            description = "Сумма перевода",
            type = "BigDecimal",
            example = "100.00",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.0"
    )
    private BigDecimal amount;
}