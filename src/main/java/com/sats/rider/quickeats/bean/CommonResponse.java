package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mobulous51 on 30/8/17.
 */

public class CommonResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("requestKey")
    private String requestKey;

    @SerializedName("delboyLogin")
    private LoginResponse delboyLogin;

    @SerializedName("delboyHome")
    private List<HomeResponse> delboyHome;

    @SerializedName("delboyProfile")
    private ProfileResponse delboyProfile;

    @SerializedName("delboyOrders")
    private List<OrderResponse> delboyOrders;

    public List<OrderResponse> getDelboyOrders() {
        return delboyOrders;
    }

    public void setDelboyOrders(List<OrderResponse> delboyOrders) {
        this.delboyOrders = delboyOrders;
    }



    public ProfileResponse getDelboyProfile() {
        return delboyProfile;
    }

    public void setDelboyProfile(ProfileResponse delboyProfile) {
        this.delboyProfile = delboyProfile;
    }



    public List<HomeResponse> getDelboyHome() {
        return delboyHome;
    }

    public void setDelboyHome(List<HomeResponse> delboyHome) {
        this.delboyHome = delboyHome;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public LoginResponse getDelboyLogin() {
        return delboyLogin;
    }

    public void setDelboyLogin(LoginResponse delboyLogin) {
        this.delboyLogin = delboyLogin;
    }



}
