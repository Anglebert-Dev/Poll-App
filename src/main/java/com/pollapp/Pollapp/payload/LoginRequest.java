package com.pollapp.Pollapp.payload;
//Login and Signup APIs


import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    private String getUsernameOrEmail(){
        return  usernameOrEmail;
    }

    private void setUsernameOrEmail(){
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
