package com.poc.smtp.dto;

public class ResetPasswordDTO {


private String newPassword;

    public ResetPasswordDTO() {
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
