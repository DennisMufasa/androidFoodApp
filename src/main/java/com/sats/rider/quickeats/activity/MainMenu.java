package com.sats.rider.quickeats.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.fragments.Contact;
import com.sats.rider.quickeats.fragments.Maps;
import com.sats.rider.quickeats.fragments.MyOrder;
import com.sats.rider.quickeats.fragments.Profile;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.GPSTracker;
import com.sats.rider.quickeats.util.SharedPreferenceKey;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainMenu extends AppCompatActivity implements Contact.ContactInterface{

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    NavigationView navigationView;
    Fragment fragment = null;
    TextView tv_title;
    ImageView iv_menu;
    String fcm_type = "", fcm_message = "";

//    ArrayList<HomeResponse> homeResponseArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Move this to where you establish a user session
        DataBindingUtil.setContentView(this, R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        tv_title = (TextView) findViewById(R.id.tv_title);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, true);
        toolbar.setNavigationIcon(R.drawable.menu);
        navigationDrawerInit();

        iv_menu.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Maps()).commit();

        LocalBroadcastManager.getInstance(this.getApplicationContext()).registerReceiver(messageReciever,
                new IntentFilter("Push"));
        try {
            fcm_type = getIntent().getStringExtra("type");
            fcm_message = getIntent().getStringExtra("message");
            if (!fcm_message.equals(""))
                Toast.makeText(this, fcm_message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(Gravity.LEFT);

            }
        });


        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);

            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000);

    }//end onCreate

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainMenu.this.getSupportFragmentManager();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
    private BroadcastReceiver messageReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!intent.getStringExtra("type").equals("")) {
                //callMyOrderApi();
                try {
                    fcm_type = intent.getStringExtra("type");
                    fcm_message = intent.getStringExtra("message");
                    if (!fcm_message.equals(""))
                        Toast.makeText(MainMenu.this, fcm_message, Toast.LENGTH_LONG).show();
                    if (getVisibleFragment() instanceof Maps) {
                        if (android.os.Build.VERSION.SDK_INT >= 21) {
                            Window window = getWindow();
                            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                        }
                        toolbar.setVisibility(View.GONE);


                        tv_title.setText(null);

                        toolbar.setNavigationIcon(R.drawable.menu);

                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Maps()).commit();

                        drawerLayout.closeDrawers();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferenceWriter.getInstance(this).writeBooleanValue(SharedPreferenceKey.appOpenOrNOt, false);
    }

    final Handler handler = new Handler();
    final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                if(SharedPreferenceWriter.getInstance(MainMenu.this).getBoolean(SharedPreferenceKey.currentLogin, false))
                {
                    callUpdateDelBoyLatLongApi();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void navigationDrawerInit() {


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id != R.id.home) {

                    toolbar.setVisibility(View.VISIBLE);
                    iv_menu.setVisibility(View.GONE);

                } else {
                    toolbar.setVisibility(View.GONE);
                    iv_menu.setVisibility(View.VISIBLE);


                }

                switch (id) {

                    case R.id.home:

                        toolbar.setVisibility(View.GONE);

                        tv_title.setText(null);

                        toolbar.setNavigationIcon(R.drawable.menu);

                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Maps()).commit();

                        drawerLayout.closeDrawers();


                      /*  Timer timer = new Timer();
                        TimerTask doAsynchronousTask = new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(runnable);

                            }
                        };
                        timer.schedule(doAsynchronousTask, 0, 5000);*/

                        break;

                    case R.id.myprofile:

                        toolbar.setNavigationIcon(R.drawable.side_menu_white);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("Profile");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Profile()).commit();
                        drawerLayout.closeDrawers();

                        break;

                    case R.id.myOrder:

                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("My Orders");
                        toolbar.setNavigationIcon(R.drawable.side_menu_white);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new MyOrder()).commit();
                        drawerLayout.closeDrawers();

                        break;
//                    case R.id.notification:
//                        toolbar.setNavigationIcon(R.drawable.back);
//
//                        tv_title.setVisibility(View.VISIBLE);
//                        tv_title.setText("Notification");
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Notification()).commit();
//                        drawerLayout.closeDrawers();
//                        break;
//                    case R.id.help:
//                        toolbar.setNavigationIcon(R.drawable.back);
//
//                        Toast.makeText(MainMenu.this, "help", Toast.LENGTH_SHORT).show();
//                        break;

                    case R.id.contact:
                        /*toolbar.setNavigationIcon(R.drawable.side_menu_white);
                        drawerLayout.closeDrawers();*/
                        toolbar.setNavigationIcon(R.drawable.side_menu_white);
                        tv_title.setVisibility(View.VISIBLE);
                        tv_title.setText("Contact Us");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Contact()).commit();
                        drawerLayout.closeDrawers();

                        //Toast.makeText(MainMenu.this, "contact", Toast.LENGTH_SHORT).show();
                        break;
//                    case R.id.about_us:
//                        toolbar.setNavigationIcon(R.drawable.back);
//
//                        Toast.makeText(MainMenu.this, "about us", Toast.LENGTH_SHORT).show();
//                        break;

                    case R.id.logout:

                        callLogoutApi();

                        break;
                }

                return false;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void callUpdateDelBoyLatLongApi() {

        GPSTracker gpsTracker = new GPSTracker(this,this);
        double lat = 0.0, lon = 0.0;
        try {
            lat = gpsTracker.getLatitude();
            lon = gpsTracker.getLongitude();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String status_id = SharedPreferenceWriter.getInstance(MainMenu.this).getString(SharedPreferenceKey.status_id);
//        MyDialog.getInstance(this).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getUpdateLatLong(SharedPreferenceWriter.getInstance(MainMenu.this).getString(SharedPreferenceKey.token),lat+"",lon+"",status_id);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
//                MyDialog.getInstance(MainMenu.this).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {

                       // Toast.makeText(MainMenu.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainMenu.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
//                MyDialog.getInstance(MainMenu.this).hideDialog();
            }
        });
    }


    private void callLogoutApi() {
        MyDialog.getInstance(this).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getLogoutResult(SharedPreferenceWriter.getInstance(this).getString(SharedPreferenceKey.token));

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(MainMenu.this).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        SharedPreferenceWriter.getInstance(MainMenu.this).writeStringValue(SharedPreferenceKey.token, null);
                        SharedPreferenceWriter.getInstance(MainMenu.this).writeBooleanValue(SharedPreferenceKey.currentLogin, false);

                        if(handler!=null && runnable!=null)
                        {
                            handler.removeCallbacks(runnable);
                        }
                        toolbar.setNavigationIcon(R.drawable.back);
                        startActivity(new Intent(MainMenu.this, Login.class));
                        finish();
                        Toast.makeText(MainMenu.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainMenu.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                MyDialog.getInstance(MainMenu.this).hideDialog();
            }
        });
    }

    @Override
    public void getMessage(String message) {
        if (message.equals("success")){
            toolbar.setVisibility(View.GONE);
            tv_title.setText(null);
            toolbar.setNavigationIcon(R.drawable.menu);
            iv_menu.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Maps()).commit();

            drawerLayout.closeDrawers();
        }
    }
}
