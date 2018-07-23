package com.sats.rider.quickeats.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sats.rider.quickeats.DrawRoute.FetchUrl;
import com.sats.rider.quickeats.R;
import com.sats.rider.quickeats.activity.Delivered;
import com.sats.rider.quickeats.bean.CommonResponse;
import com.sats.rider.quickeats.bean.HomeResponse;
import com.sats.rider.quickeats.database.SharedPreferenceWriter;
import com.sats.rider.quickeats.retrofit.ApiClient;
import com.sats.rider.quickeats.retrofit.ApiInterface;
import com.sats.rider.quickeats.retrofit.MyDialog;
import com.sats.rider.quickeats.util.GPSTracker;
import com.sats.rider.quickeats.util.SharedPreferenceKey;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Maps extends Fragment implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, LocationListener {

    View view;
    Toolbar map_toolbar;
    TextView customer_id;
    private GoogleMap mMap;
    Marker currMarker;
    MarkerOptions markerOptions = new MarkerOptions();
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    //private LatLng Noida63Hblock = new LatLng(28.628454, 77.376945);
    TextView cust_id, cust_name;
    double latitude = 0.0, longitude = 0.0;
    GPSTracker gpsTracker;
    ArrayList<LatLng> MarkerPoints;
    LatLng origin;
    LatLng dest;
    ArrayList<HomeResponse> homeResponseArrayList;
    ArrayList<LatLng> marker_list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_maps, container, false);


        map_toolbar = (Toolbar) view.findViewById(R.id.map_toolbar);
        customer_id = (TextView) view.findViewById(R.id.customer_id);
        cust_id = (TextView) view.findViewById(R.id.customer_id);
        cust_name = (TextView) view.findViewById(R.id.cust_name);
        gpsTracker = new GPSTracker(getActivity(), getActivity());
        // Initializing
        MarkerPoints = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SpannableString content = new SpannableString("CT. ID: F032603265");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        customer_id.setText(content);
//        latitude = gpsTracker.getLatitude();
//        longitude = gpsTracker.getLongitude();
//        marker_list.add(new LatLng(latitude, longitude));

        calldelboyHomeApi();

        map_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!customer_id.getText().toString().equals("CT. ID: F032603265") && !cust_name.getText().toString().equals("")) {
                    Intent intent = new Intent(getContext(), Delivered.class);
                    intent.putExtra("home_list", homeResponseArrayList);
                    startActivityForResult(intent, 50);
                } else
                    Toast.makeText(getActivity(), "You have not any Order!", Toast.LENGTH_LONG).show();

            }
        });

//
//        view.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                switch (event.getAction()){
//
//                    case MotionEvent.ACTION_UP:
//
//                        Toast.makeText(getContext(), "up", Toast.LENGTH_SHORT).show();
//
//                        break;
//                }
//
//
//
//                return true;
//            }
//        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == getActivity().RESULT_OK)
                calldelboyHomeApi();
        }
    }

    private void calldelboyHomeApi() {
        MyDialog.getInstance(getActivity()).showDialog();
        Retrofit retrofit = ApiClient.getClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Call<CommonResponse> call = apiInterface.getdelboyHomeResult(SharedPreferenceWriter.getInstance(getActivity()).getString(SharedPreferenceKey.token));

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                MyDialog.getInstance(getActivity()).hideDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("SUCCESS")) {

                        if (response.body().getDelboyHome() != null) {
                            homeResponseArrayList = (ArrayList<HomeResponse>) response.body().getDelboyHome();
                           if(response.body().getDelboyHome()!=null && response.body().getDelboyHome().size()>0)
                           {
                               String status_id  = response.body().getDelboyHome().get(0).getDb_status_id();
                               SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(SharedPreferenceKey.status_id,status_id);

                               String map_location=response.body().getDelboyHome().get(0).getMap_location();
                               SharedPreferenceWriter.getInstance(getActivity()).writeStringValue(SharedPreferenceKey.map_location,map_location);

                           }

                            try {
                                if (response.body().getDelboyHome().size() > 0) {
                                    for (int i = 0; i < response.body().getDelboyHome().size(); i++) {
                                        String location = response.body().getDelboyHome().get(i).getMap_location();
                                        cust_id.setText("CT. ID: " + response.body().getDelboyHome().get(i).getOrder_no());
                                        cust_name.setText(response.body().getDelboyHome().get(i).getCust_name());
                                        String str[] = location.split(",");
                                        latitude = Double.parseDouble(str[0]);
                                        longitude = Double.parseDouble(str[1]);
                                        marker_list.add(new LatLng(latitude, longitude));
                                        onMapReady(mMap);
                                    }
                                } else {
                                    cust_name.setText("");
                                    cust_id.setText("CT. ID:");
                                    latitude = 0.0;
                                    longitude = 0.0;
                                    marker_list.clear();
                                    marker_list.add(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
                                    mMap.clear();
                                    onMapReady(mMap);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        cust_name.setText("");
                        cust_id.setText("CT. ID:");
                        latitude = 0.0;
                        longitude = 0.0;
                        marker_list.clear();
                        marker_list.add(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
                        mMap.clear();
                        onMapReady(mMap);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                        //"Invalid User."


                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                MyDialog.getInstance(getActivity()).hideDialog();
            }
        });
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
       /* if (mMap !=null && marker_list!=null)
        {

            for (int i=0;i<marker_list.size();i++)
            {
                if (i==0)
                {
                    LatLng latLng1 = new LatLng(latitude, longitude);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location));
                    //mMap.setOnMarkerClickListener(markerOptions);
                    //googleMap.setOnInfoWindowClickListener(this);

                    markerOptions.position(latLng1);

                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.addMarker(markerOptions);

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (width * 0.12);

                    builder.include(latLng1);
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                }else if(i==1){
                    origin = new LatLng(latitude, longitude);
                    dest = new LatLng(marker_list.get(i).latitude, marker_list.get(i).longitude);
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl fetchUrl = new FetchUrl(mMap);

                    // Start downloading json data from Google Directions API
                    fetchUrl.execute(url);
                    LatLng latLng = new LatLng(marker_list.get(i).latitude, marker_list.get(i).longitude);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_location));
                    //mMap.setOnMarkerClickListener(markerOptions);
                    //googleMap.setOnInfoWindowClickListener(this);

                    markerOptions.position(latLng);

                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    mMap.addMarker(markerOptions);

                    int width = getResources().getDisplayMetrics().widthPixels;
                    int height = getResources().getDisplayMetrics().heightPixels;
                    int padding = (int) (width * 0.12);

                    builder.include(latLng);
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

                    mMap.setMyLocationEnabled(true);
                }
            }

        }*/
        if (mMap != null && (latitude != 0.0 && longitude != 0.0)) {
            // Getting URL to the Google Directions API
            origin = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            dest = new LatLng(latitude, longitude);
            String url = getUrl(origin, dest);
            Log.d("onMapClick", url.toString());
            FetchUrl fetchUrl = new FetchUrl(mMap);

            // Start downloading json data from Google Directions API
            fetchUrl.execute(url);
            LatLng latLng = new LatLng(latitude, longitude);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.home_location));
            //mMap.setOnMarkerClickListener(markerOptions);
            //googleMap.setOnInfoWindowClickListener(this);

            markerOptions.position(latLng);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.addMarker(markerOptions);

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12);

            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        } else {

            LatLng latLng = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location));
            //mMap.setOnMarkerClickListener(markerOptions);
            //googleMap.setOnInfoWindowClickListener(this);

            markerOptions.position(latLng);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            currMarker= mMap.addMarker(markerOptions);

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12);

            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
            mMap.setMyLocationEnabled(true);
        }

    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap != null) {
            //mMap.clear();
//        latitude=location.getLatitude();
//        longitude=location.getLongitude();

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_location));
            //mMap.setOnMarkerClickListener(markerOptions);
            //googleMap.setOnInfoWindowClickListener(this);

            markerOptions.position(latLng);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if(currMarker!=null)
                currMarker.remove();

            currMarker= mMap.addMarker(markerOptions);

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.12);

            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }

    }
}
