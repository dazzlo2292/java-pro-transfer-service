package ru.otus.java.pro.transfer.service.dtos;

public record ExecuteTransferDtoRq(String targetClientId, String sourceAccount, String targetAccount, String message, int amount) {
}