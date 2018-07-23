package com.sats.rider.quickeats.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.SharedPreferenceKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by mobulous51 on 7/12/17.
 */

public class Contact extends Fragment {

    View view;
    EditText title,description;
    Button send_button;
    ContactInterface contactInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contact_frag, container, false);

        contactInterface= (ContactInterface) getActivity();
        title=(EditText)view.findViewById(R.id.title);
        description=(EditText)view.findViewById(R.id.description);
        send_button = (Button)view.findViewById(R.id.send_button);


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"Please fill title",Toast.LENGTH_LONG).show();
                }else if (description.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"Please fill description",Toast.LENGTH_LONG).show();
                }else {
                    callContactApi();
                }
            }
        });

        return view;
    }

    private void callContactApi() {
        MyDialog.getInstance(getActivity()).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getContactResult(SharedPreferenceWriter.getInstance(getActivity()).getString(SharedPreferenceKey.token),"Delivery",title.getText().toString(),description.getText().toString());

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        contactInterface.getMessage("success");
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

    public interface ContactInterface{
        public void getMessage(String message);
    }
}
