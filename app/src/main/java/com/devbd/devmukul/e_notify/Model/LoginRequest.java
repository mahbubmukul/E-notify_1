package com.devbd.devmukul.e_notify.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by MUKUL on 5/15/17.
 */

public class LoginRequest implements Serializable {

    @SerializedName("username")
    private final String username;
    @SerializedName("password")
    private final String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
