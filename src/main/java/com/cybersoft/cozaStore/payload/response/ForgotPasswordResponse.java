package com.cybersoft.cozaStore.payload.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class ForgotPasswordResponse {
    @Email
    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
