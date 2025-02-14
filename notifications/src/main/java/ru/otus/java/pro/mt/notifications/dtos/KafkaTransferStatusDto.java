package ru.otus.java.pro.mt.notifications.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaTransferStatusDto implements Serializable {
    private String transferId;
    private String status;
}
