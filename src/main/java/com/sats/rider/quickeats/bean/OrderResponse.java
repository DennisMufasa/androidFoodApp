package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mobulous51 on 1/11/17.
 */

public class OrderResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("name")
    private String name;

    @SerializedName("order_no")
    private String order_no;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }


}
