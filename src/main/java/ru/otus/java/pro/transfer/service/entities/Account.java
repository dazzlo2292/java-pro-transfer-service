package ru.otus.java.pro.transfer.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "balance")
    private int balance;

    @Column(name = "block_fl")
    private char blockFlag;
}
