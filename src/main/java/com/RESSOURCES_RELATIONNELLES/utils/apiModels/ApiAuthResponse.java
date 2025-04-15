package com.RESSOURCES_RELATIONNELLES.utils.apiModels;

import com.RESSOURCES_RELATIONNELLES.entities.User;

public class ApiAuthResponse {
    private String token;
    private User user;

    // getters/setters

    public ApiAuthResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

