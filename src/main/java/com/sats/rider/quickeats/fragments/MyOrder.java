package com.sats.rider.quickeats.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.adapter.MyOrderAdap;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.bean.OrderResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.SharedPreferenceKey;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyOrder extends Fragment {

    MyOrderAdap myOrderAdap;
    View view;
    ArrayList<OrderResponse> orderResponseArrayList;
    RecyclerView rv_myorder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view= inflater.inflate(R.layout.fragment_my_order, container, false);



         rv_myorder= (RecyclerView)view.findViewById(R.id.rv_myorder);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rv_myorder.setLayoutManager(linearLayoutManager);

        calldelboyOrdersApi();

        return view;

    }

    private void calldelboyOrdersApi() {
        MyDialog.getInstance(getActivity()).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getdelboyOrdersResult(SharedPreferenceWriter.getInstance(getActivity()).getString(SharedPreferenceKey.token));

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        orderResponseArrayList= (ArrayList<OrderResponse>) response.body().getDelboyOrders();
                        myOrderAdap = new MyOrderAdap(getActivity(), orderResponseArrayList);
                        rv_myorder.setAdapter(myOrderAdap);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();
            }
        });
    }

}
