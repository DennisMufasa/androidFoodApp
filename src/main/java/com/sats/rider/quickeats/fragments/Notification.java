package com.sats.rider.quickeats.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.adapter.NotifcationAdapter;
import com.sats.rider.quickeats.util.BeanData;

import java.util.ArrayList;


public class Notification extends Fragment {

    View view;
    BeanData beanClass= new BeanData();
    NotifcationAdapter notAdap;

    ArrayList<BeanData> noti= new ArrayList<BeanData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView rv_notification= (RecyclerView)view.findViewById(R.id.rv_notification);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rv_notification.setLayoutManager(linearLayoutManager);



        for (int i = 0; i < 4; i++) {

            beanClass.setNoti_data("You have delivered  Sandwich to Steven Smith");
            beanClass.setNoti_time("Yesterday at 10:05PM");

            noti.add(beanClass);
        }

        notAdap = new NotifcationAdapter(getContext(), noti);
        rv_notification.setAdapter(notAdap);

        return  view;
    }

}
