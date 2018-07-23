package com.sats.rider.quickeats.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sats.rider.quickeats.R;


public class AnimationActivity extends AppCompatActivity {

    int onStartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.bottom_up,
                    R.anim.bottom_down);
        } else // already created so reverse animation
        {
            onStartCount = 2;

        }
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.bottom_down,
                    R.anim.bottom_up);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        Toast.makeText(this, "destroy", Toast.LENGTH_SHORT).show();
//
//
//        if (onStartCount > 1) {
//            this.overridePendingTransition(R.anim.bottom_down,
//                    R.anim.bottom_up);
//
//        } else if (onStartCount == 1) {
//            onStartCount++;
//        }
//
//    }
}
