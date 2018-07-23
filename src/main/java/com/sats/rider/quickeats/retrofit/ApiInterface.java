package com.sats.rider.quickeats.retrofit;

import com.sats.rider.quickeats.bean.CommonResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mobua06 on 16/6/17.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("delboyLogin")
    Call<CommonResponse> getLoginResult(@Field("email") String email,
                                        @Field("password") String pasword,
                                        @Field("device_type") String device_type,
                                        @Field("device_token") String device_token,
                                        @Field("lat") String lat,
                                        @Field("long") String lng);
    @FormUrlEncoded
    @POST("delboyLogout")
    Call<CommonResponse> getLogoutResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("delboyHome")
    Call<CommonResponse> getdelboyHomeResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("delboyProfile")
    Call<CommonResponse> getdelboyProfileResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("deliverOrder")
    Call<CommonResponse> getdeliverOrderResult(@Field("token") String token,
                                               @Field("order_id") String order_id,
                                               @Field("status_id") String status_id);

    @FormUrlEncoded
    @POST("delboyOrders")
    Call<CommonResponse> getdelboyOrdersResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("contact")
    Call<CommonResponse> getContactResult(@Field("token") String token,
                                          @Field("type") String type,
                                          @Field("title") String title,
                                          @Field("description") String description);

    @FormUrlEncoded
    @POST("updateLatlong")
    Call<CommonResponse> getUpdateLatLong(@Field("token") String token,
                                          @Field("lat") String lat,
                                          @Field("long") String lng,
                                          @Field("status_id") String status_id);




//    @FormUrlEncoded
//    @POST("register")
//    Call<CommonResponse> getSingupResult(@Field("email") String email, @Field("mobile_number") String phone,
//                                         @Field("password") String pass, @Field("device_type") String device_type,
//                                         @Field("device_token") String device_token);

   /* @FormUrlEncoded
    @POST("chkUser")
    Call<CommonResponse> getchkUserResult(@Field("email") String email, @Field("mobile_number") String phone);

    @FormUrlEncoded
    @POST("verificationCode")
    Call<CommonResponse> getverificationCodeResult(@Field("mobile_number") String phone);

    @FormUrlEncoded
    @POST("login")
    Call<CommonResponse> getLoginResult(@Field("email") String email, @Field("password") String pasword,
                                        @Field("device_type") String device_type, @Field("device_token") String device_token);

    @FormUrlEncoded
    @POST("socialLogin")
    Call<CommonResponse> getSocialLoginResult(@Field("name") String name, @Field("email") String email, @Field("social_id") String social_id,
                                              @Field("image") String image, @Field("type") String fbOrgoogle,
                                              @Field("device_type") String device_type, @Field("device_token") String device_token);



    @FormUrlEncoded
    @POST("forgotPassword")
    Call<CommonResponse> getForgotResult(@Field("email") String email);

    @FormUrlEncoded
    @POST("resetPassword")
    Call<CommonResponse> getResetPasswordResult(@Field("otp") String token, @Field("newPassword") String newPassword,
                                                @Field("cnfPassword") String cnfPassword);

    @FormUrlEncoded
    @POST("logout")
    Call<CommonResponse> getLogoutResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("profile")
    Call<CommonResponse> getProfileResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("home")
    Call<CommonResponse> getHomeResult(@Field("token") String token, @Field("latitude") String latitude, @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("menuList")
    Call<CommonResponse> getMenuListResult(@Field("token") String token, @Field("restaurent_id") String restaurent_id);

    @FormUrlEncoded
    @POST("categoryList")
    Call<CommonResponse> getCategoryListResult(@Field("token") String token, @Field("rest_id") String restaurent_id);

    @FormUrlEncoded
    @POST("categoryMenu")
    Call<CommonResponse> getCategoryMenuResult(@Field("token") String token, @Field("rest_id") String restaurent_id, @Field("category") String category);


    @POST("bookOrder")
    Call<CommonResponse> getBookOrderResult(@Body RequestBody object);

    @FormUrlEncoded
    @POST("myOrders")
    Call<CommonResponse> getMyOrderResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("myFavourites")
    Call<CommonResponse> getMyFavouriteResult(@Field("token") String token);

    @FormUrlEncoded
    @POST("favourite")
    Call<CommonResponse> getFavouriteResult(@Field("token") String token, @Field("restaurent_id") String restaurent_id, @Field("type") String type);

    @FormUrlEncoded
    @POST("searchType")
    Call<CommonResponse> getsearchTypeResult(@Field("token") String token, @Field("input") String input);

    @FormUrlEncoded
    @POST("searchDetail")
    Call<CommonResponse> getsearchDetailResult(@Field("token") String token, @Field("restaurent_id") String restaurent_id,
                                               @Field("type") String type, @Field("lat") String lat,
                                               @Field("long") String lon, @Field("city") String city);
    @FormUrlEncoded
    @POST("restDetail")
    Call<CommonResponse> getrestDetailResult(@Field("token") String token, @Field("restaurent_id") String restaurent_id);*/



}
