package com.sats.rider.quickeats.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.bean.HomeResponse;
import com.sats.rider.quickeats.bean.ItemsResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.databinding.DeliveryDiaolgueBinding;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.AnimationActivity;
import com.sats.rider.quickeats.util.SharedPreferenceKey;
import com.sats.rider.quickeats.util.SlideButton;
import com.sats.rider.quickeats.util.SlideButtonListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Delivered extends AnimationActivity implements View.OnClickListener {
    Toolbar my_toolbar;
    Button  btn_down;
    SlideButton btn_delivered;
    TextView tv_cust_id, tv_cust_name, tv_total_price, itemName, item_quantity,pay_mode;
    ArrayList<HomeResponse> homeResponseArrayList;
    String str = "";
    String orderId = "", statusId = "", customerName = "", customerId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_delivered);


        All_id();
        try {
            homeResponseArrayList = (ArrayList<HomeResponse>) getIntent().getSerializableExtra("home_list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (HomeResponse homeResponse : homeResponseArrayList) {
            orderId = homeResponse.getId();
            statusId = homeResponse.getDb_status_id();
            customerName = homeResponse.getCust_name();
            customerId = homeResponse.getOrder_no();
            tv_cust_name.setText(homeResponse.getCust_name());
            tv_cust_id.setText(homeResponse.getOrder_no());
            item_quantity.setText(homeResponse.getTotal_item());
            tv_total_price.setText("$" + homeResponse.getTotal_amount());
            pay_mode.setText(homeResponse.getPayment_mode());
            for (ItemsResponse itemsResponse : homeResponse.getItems()) {
                str = str + itemsResponse.getItem_name() + ",";

            }
            if (str.endsWith(","))
                str = str.substring(0, str.length() - 1);
            itemName.setText(str);
        }


        btn_delivered.setOnClickListener(this);
        btn_down.setOnClickListener(this);
        my_toolbar.setOnClickListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.delivery, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.status:

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void diologue_activity() {

        DeliveryDiaolgueBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.delivery_diaolgue, null, false);

        final Dialog dialog = new Dialog(this, R.style.MyDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#c9000000")));
        ImageView dialogue_close = dialog.findViewById(R.id.dialogue_close);
        TextView tv_id = dialog.findViewById(R.id.tv_id);
        TextView custName = dialog.findViewById(R.id.custName);

        SpannableString content = new SpannableString(customerId);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_id.setText(content);
        custName.setText(customerName);

        dialogue_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                Intent intent = new Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        dialog.show();

    }

    private void calldeliverOrderApi() {
        MyDialog.getInstance(this).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getdeliverOrderResult(SharedPreferenceWriter.getInstance(this).getString(SharedPreferenceKey.token), orderId, statusId);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(Delivered.this).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {

                        diologue_activity();
                        Toast.makeText(Delivered.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Delivered.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                MyDialog.getInstance(Delivered.this).hideDialog();
            }
        });
    }


    public void All_id() {

        my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        btn_delivered = (SlideButton) findViewById(R.id.btn_delivered);
        tv_cust_id = (TextView) findViewById(R.id.tv_cust_id);
        item_quantity = (TextView) findViewById(R.id.item_quantity);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        itemName = (TextView) findViewById(R.id.itemName);
        tv_cust_name = (TextView) findViewById(R.id.tv_cust_name);
        pay_mode = (TextView) findViewById(R.id.pay_mode);
        btn_down = (Button) findViewById(R.id.btn_down);


        setSupportActionBar(my_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SpannableString content = new SpannableString("CT. ID: F0326032");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_cust_id.setText(content);

        btn_delivered.setSlideButtonListener(new SlideButtonListener() {
            @Override
            public void handleSlide() {
                if (!orderId.equals("") && !statusId.equals(""))
                    calldeliverOrderApi();
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.my_toolbar:

                finish();
                break;

            case R.id.btn_down:

                onBackPressed();
                break;


           // case R.id.btn_delivered:
             //   if (!orderId.equals("") && !statusId.equals(""))
               //     calldeliverOrderApi();

                //break;


        }


    }
}
