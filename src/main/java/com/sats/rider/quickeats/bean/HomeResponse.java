package com.sats.rider.quickeats.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mobulous51 on 31/10/17.
 */

public class HomeResponse implements Serializable{

    @SerializedName("id")
    private String id;

    @SerializedName("db_status_id")
    private String db_status_id;

    @SerializedName("order_no")
    private String order_no;

    @SerializedName("cust_name")
    private String cust_name;

    @SerializedName("address")
    private String address;

    @SerializedName("map_location")
    private String map_location;

    @SerializedName("total_item")
    private String total_item;

    @SerializedName("total_amount")
    private String total_amount;

    @SerializedName("date")
    private String date;

    @SerializedName("status")
    private String status;

    @SerializedName("accept_time")
    private String accept_time;

    @SerializedName("dispatch_time")
    private String dispatch_time;

    @SerializedName("items")
    private List<ItemsResponse> items;

    @SerializedName("payment_mode")
    private String payment_mode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDb_status_id() {
        return db_status_id;
    }

    public void setDb_status_id(String db_status_id) {
        this.db_status_id = db_status_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMap_location() {
        return map_location;
    }

    public void setMap_location(String map_location) {
        this.map_location = map_location;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_item(String total_item) {
        this.total_item = total_item;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getDispatch_time() {
        return dispatch_time;
    }

    public void setDispatch_time(String dispatch_time) {
        this.dispatch_time = dispatch_time;
    }

    public List<ItemsResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemsResponse> items) {
        this.items = items;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }


}
