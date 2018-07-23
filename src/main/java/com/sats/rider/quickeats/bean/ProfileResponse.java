package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobulous51 on 31/10/17.
 */

public class ProfileResponse {

    @SerializedName("id")
    private String profileId;

    @SerializedName("type")
    private String type;

    @SerializedName("status")
    private String status;

    @SerializedName("owner_id")
    private String owner_id;

    @SerializedName("restaurent_id")
    private String restaurent_id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("mobile_number")
    private String mobile_number;

    @SerializedName("dob")
    private String dob;

    @SerializedName("message")
    private String message;

    @SerializedName("image")
    private String image;

    @SerializedName("password")
    private String password;

    @SerializedName("temp_password")
    private String temp_password;

    @SerializedName("image_url")
    private String image_url;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getRestaurent_id() {
        return restaurent_id;
    }

    public void setRestaurent_id(String restaurent_id) {
        this.restaurent_id = restaurent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTemp_password() {
        return temp_password;
    }

    public void setTemp_password(String temp_password) {
        this.temp_password = temp_password;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

}
