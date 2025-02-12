package ru.otus.java.pro.mt.limits.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Остаток лимита")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemainingLimitDto {
    @Schema(
            description = "Количество оставшегося лимита",
            type = "BigDecimal",
            example = "99",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.0"
    )
    private BigDecimal remainingLimit;
}
