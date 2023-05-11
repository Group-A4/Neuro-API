package com.example.Neurosurgical.App.recovery;

public class ResetCode {
    private String code;
    private String email;

    public ResetCode(String code, String email) {
        this.code = code;
        this.email = email;
    }

    public ResetCode() {
    }

    public String getCode() {
        return code;
    }

    public String getEmail() {
        return email;
    }
}
