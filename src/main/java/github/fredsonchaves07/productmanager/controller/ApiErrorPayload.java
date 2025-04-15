package github.fredsonchaves07.productmanager.controller;

public record ApiErrorPayload(String type, String message, String description) {

    public static ApiErrorPayload of(String type, String message, String description) {
        return new ApiErrorPayload(type, message, description);
    }
}
