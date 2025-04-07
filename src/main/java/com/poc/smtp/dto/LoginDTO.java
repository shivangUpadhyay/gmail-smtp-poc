package com.poc.smtp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDTO {


@JsonProperty("email")
private  String emailId;

private String password;

    public LoginDTO() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
