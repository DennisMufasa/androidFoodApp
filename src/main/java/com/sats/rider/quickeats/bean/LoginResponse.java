package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobulous51 on 31/10/17.
 */

public class LoginResponse {

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_number")
    private String mobile_number;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("device_type")
    private String device_type;

    @SerializedName("device_token")
    private String device_token;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }


}
