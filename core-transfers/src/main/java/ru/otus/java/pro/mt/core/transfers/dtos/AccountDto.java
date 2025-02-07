package ru.otus.java.pro.mt.core.transfers.dtos;

import java.math.BigDecimal;

public record AccountDto(String id, String accountNumber, String clientId, BigDecimal balance, char blockFlag) {
}
