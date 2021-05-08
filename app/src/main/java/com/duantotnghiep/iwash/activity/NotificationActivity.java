package com.duantotnghiep.iwash.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.adapter.NotificationAdapter;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.model.Notification;
import com.duantotnghiep.iwash.model.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    List<Notification> notifications;
    RecyclerView rvNotification;
    NotificationAdapter adapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Thông báo");
        toolbar.setNavigationIcon(R.drawable.backicon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        rvNotification = findViewById(R.id.rvNotification);
        notifications = new ArrayList<>();
        RetrofitClient.getInstance().create(ApiService.class).getNotifyUser().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.body().success) {
                    notifications = response.body().notifications;
                    Log.d("onResponse54: ", notifications.size() + "huhu");
                    adapter = new NotificationAdapter(NotificationActivity.this, notifications);
                    rvNotification.setLayoutManager(new LinearLayoutManager(NotificationActivity.this, LinearLayoutManager.VERTICAL, false));
                    rvNotification.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.d("onFailure: ", t.getMessage());
            }
        });
    }
}