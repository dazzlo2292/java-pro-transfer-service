package ru.otus.java.pro.transfer.service.dtos;

public record AccountDto(String id, String accountNumber, String clientId, int balance, char blockFlag) {
}
