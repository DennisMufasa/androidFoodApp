package com.sats.rider.quickeats.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.GPSTracker;
import com.sats.rider.quickeats.util.SharedPreferenceKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email, password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btn_login);

       // getDeviceToken();

        btn_login.setOnClickListener(this);
//        if (SharedPreferenceWriter.getInstance(this).getBoolean(SharedPreferenceKey.currentLogin,false))
//        {
//            Intent intent = new Intent(Login.this, MainMenu.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:
                if (!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
                    callLoginApi();
                }
                else
                    Toast.makeText(this,"Please fill email and password",Toast.LENGTH_LONG).show();

                break;


        }
    }

    private void getDeviceToken() {

        final Thread thread = new Thread() {
            @Override
            public void run() {
                Log.e(">>>>>>>>>>>>>>", "thred IS  running");
                SharedPreferenceWriter mPreference = SharedPreferenceWriter.getInstance(getApplicationContext());
                try {
                    if (mPreference.getString(SharedPreferenceKey.device_token).isEmpty()) {
                        //String token = FirebaseInstanceId.getInstance().getToken();
                        String token = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),
                                android.provider.Settings.Secure.ANDROID_ID);
                        Log.e("Generated Device Token", "-->" + token);
                        if (token == null) {
                            getDeviceToken();
                        } else {
                            mPreference.writeStringValue(SharedPreferenceKey.device_token, token);
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();

    }

    private void callLoginApi() {

        GPSTracker gpsTracker = new GPSTracker(this,this);
        double lat = 0.0, lon = 0.0;
        try {
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyDialog.getInstance(this).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getLoginResult(email.getText().toString().trim(), password.getText().toString().trim(),
                "android", SharedPreferenceWriter.getInstance(this).getString(SharedPreferenceKey.device_token),lat+"",lon+"");

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(Login.this).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        SharedPreferenceWriter.getInstance(Login.this).writeStringValue(SharedPreferenceKey.token, response.body().getDelboyLogin().getToken());
                        SharedPreferenceWriter.getInstance(Login.this).writeBooleanValue(SharedPreferenceKey.currentLogin, true);

                        Intent intent = new Intent(Login.this, MainMenu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                MyDialog.getInstance(Login.this).hideDialog();
            }
        });
    }
}
