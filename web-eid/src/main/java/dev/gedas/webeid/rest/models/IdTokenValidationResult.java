package dev.gedas.webeid.rest.models;

public class IdTokenValidationResult {
    private final String code;
    private final String status;

    public IdTokenValidationResult(String code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
