package ru.otus.java.pro.mt.core.transfers.exceptions_handling;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Ошибка")
public class ErrorDto {
    @Schema(
            description = "Код ошибки",
            type = "string",
            example = "INTERNAL_SERVER_ERROR",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String code;

    @Schema(
            description = "Сообщение с подробностями об ошибке",
            type = "string",
            example = "Произошла непредвиденная ошибка",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String message;

    @Schema(
            description = "Время возникновения ошибки",
            type = "dateTime",
            example = "2025-02-12T18:04:44.049",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDateTime dateTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
