package github.fredsonchaves07.productmanager.controller;

import github.fredsonchaves07.productmanager.error.ProductError;

public record ApiResponse<T>(
        String status,
        int statusCode,
        String path,
        T payload
) {

    public static <T> ApiResponse<T> of(String status, int statusCode, String path, T payload) {
        return new ApiResponse<>(status, statusCode, path, payload);
    }

    public static ApiResponse<ApiErrorPayload> of(String status, int statusCode, String path, ProductError error) {
        ApiErrorPayload errorPayload = ApiErrorPayload.of(error.typeError().toString(), error.message(), error.description());
        return new ApiResponse<>(status, statusCode, path, errorPayload);
    }
}
