package com.sats.rider.quickeats.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.databinding.FragmentProfileBinding;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.SharedPreferenceKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    View view;
    FragmentProfileBinding binding;
    EditText name,email,number;
    ImageView iv_profile_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();

        name=(EditText)view.findViewById(R.id.name);
        email=(EditText)view.findViewById(R.id.email);
        number=(EditText)view.findViewById(R.id.number);
        iv_profile_pic=(ImageView)view.findViewById(R.id.iv_profile_pic);

        calldelboyProfileApi();

        return view;

    }

    private void calldelboyProfileApi() {
        MyDialog.getInstance(getActivity()).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getdelboyProfileResult(SharedPreferenceWriter.getInstance(getActivity()).getString(SharedPreferenceKey.token));

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        if (!response.body().getDelboyProfile().getImage_url().equals(""))
                        {
                            AQuery aQuery=new AQuery(getActivity());
                            aQuery.id(iv_profile_pic).image(response.body().getDelboyProfile().getImage_url(), false, false, 0, R.drawable.profile);
                        }
                        name.setText(response.body().getDelboyProfile().getName());
                        email.setText(response.body().getDelboyProfile().getEmail());
                        number.setText(response.body().getDelboyProfile().getMobile_number());

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
