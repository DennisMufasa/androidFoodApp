package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mobulous51 on 31/10/17.
 */

public class ItemsResponse implements Serializable{

    @SerializedName("id")
    private String itemId;

    @SerializedName("order_id")
    private String order_id;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("item_name")
    private String item_name;

    @SerializedName("item_price")
    private String item_price;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("created_on")
    private String created_on;

    @SerializedName("updated_on")
    private String updated_on;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }



}
