package com.devbd.devmukul.e_notify.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName("token")
    private String token;

    public LoginResponse() {
        this(null);
    }

    public LoginResponse(String token) {

        this.token = token;
    }

    public String getToken() {

        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        return result;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                '}';
    }
}
