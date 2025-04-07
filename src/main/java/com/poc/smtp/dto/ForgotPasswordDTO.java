package com.poc.smtp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ForgotPasswordDTO {

@JsonProperty("email")
private String  emailId;

    public ForgotPasswordDTO() {
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

}
