package com.example.figureapp.service;

import com.example.figureapp.model.TokenModel;
import com.example.figureapp.model.User;

public class Response {
    private boolean error;
    private String message;
    private User user;
    private TokenModel token;

    public TokenModel getToken() {
        return token;
    }

    public void setToken(TokenModel token) {
        this.token = token;
    }

    public Response(boolean error, String message, User user, String token) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.token = new TokenModel(token);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
