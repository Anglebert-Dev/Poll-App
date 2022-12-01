package com.pollapp.Pollapp.payload;
//Login and Signup APIs


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail(){
        return  usernameOrEmail;
    }

    public void setUsernameOrEmail(){
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
