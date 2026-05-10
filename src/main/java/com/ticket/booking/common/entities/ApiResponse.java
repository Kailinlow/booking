package com.ticket.booking.common.entities;

import com.ticket.booking.common.constants.ApiMessage;
import com.ticket.booking.common.constants.ApiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    private ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, ApiMessage.SUCCESS, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(ApiStatus.CREATED, ApiMessage.CREATED, data);
    }

    public static <T> ApiResponse<T> message(String message, T data) {
        return new ApiResponse<>(ApiStatus.SUCCESS, message, data);
    }
}
