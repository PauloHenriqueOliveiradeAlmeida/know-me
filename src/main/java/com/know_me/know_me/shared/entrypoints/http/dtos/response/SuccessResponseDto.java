package com.know_me.know_me.shared.entrypoints.http.dtos.response;

public record SuccessResponseDto<T>(String message, T data) { }
