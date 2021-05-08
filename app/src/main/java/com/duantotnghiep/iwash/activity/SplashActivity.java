package com.duantotnghiep.iwash.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.services.IWashNotificationManager;
import com.duantotnghiep.iwash.utils.Constants;

public class SplashActivity extends AppCompatActivity {
    TextView tvSplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvSplash = findViewById(R.id.tvSplash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSplash.setText("Chào mừng bạn đến với IWash");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                },2000);
            }
        }, 1000);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        /*
         * Displaying a notification locally
         */
        IWashNotificationManager.getInstance(this).displayNotification("IWash", "Chào mừng bạn đến với ứng dụng IWash");
    }
}