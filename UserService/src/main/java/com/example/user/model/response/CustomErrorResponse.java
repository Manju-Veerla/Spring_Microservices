package com.example.user.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomErrorResponse(
        String header,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message,
        Boolean isSuccess
) {
    public CustomErrorResponse(String header, String message) {
        this(header, message, false);
    }
}