package github.fredsonchaves07.productmanager.controller;

public record HealthStatus(
        String status,
        String version
) {
    public static HealthStatus of(String status, String version) {
        return new HealthStatus(status, version);
    }
}
