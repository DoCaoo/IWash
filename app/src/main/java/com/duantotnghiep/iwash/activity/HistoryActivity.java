package com.duantotnghiep.iwash.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.adapter.HistoryAdapter;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.Schedule;
import com.duantotnghiep.iwash.model.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView rvHistory;
    private List<Schedule> scheduleList = new ArrayList<>();
    HistoryAdapter historyAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        toolbar.setTitle("Lịch sử");
        toolbar.setNavigationIcon(R.drawable.backicon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        RetrofitClient.getInstance().create(ApiService.class).getSchedulesUser().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                scheduleList = response.body().schedules;
                historyAdapter = new HistoryAdapter(HistoryActivity.this, scheduleList, new ItemClick() {
                    @Override
                    public void setOnItemClick(View view, int pos) {
                        if (view.getId() == R.id.btnCancelSchedule) {
                            AlertDialog builder = new AlertDialog.Builder(HistoryActivity.this).create();
                            View dialog = LayoutInflater.from(HistoryActivity.this).inflate(R.layout.dialog_cancel_schedule, null);
                            EditText edtNote = dialog.findViewById(R.id.edtNote);
                            Button btnCancel = dialog.findViewById(R.id.btnOKCancel);
                            Button btnNone = dialog.findViewById(R.id.btnNone);
                            btnNone.setOnClickListener(view1 -> {
                                builder.dismiss();
                            });
                            btnCancel.setOnClickListener(v1 -> {
                                RetrofitClient.getInstance().create(ApiService.class).cancel(scheduleList.get(pos).getId(), edtNote.getText().toString(), "Canceled").enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ServerResponse> call2, @NonNull Response<ServerResponse> response2) {
                                        if (response2.body().success) {
                                            Toast.makeText(HistoryActivity.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                            scheduleList.remove(pos);
                                            builder.dismiss();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ServerResponse> call12, @NonNull Throwable t) {
                                        Log.d("onFailure: ", t.getMessage());
                                    }
                                });
                            });
                            builder.setView(dialog);
                            builder.show();
                        } else if (view.getId() == R.id.btnConfirmVehicle) {
                            RetrofitClient.getInstance().create(ApiService.class).confirmVehicle(scheduleList.get(pos).getId(), true).enqueue(new Callback<ServerResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                                    if (response.body().success) {
                                        finish();
                                        startActivity(getIntent());
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                                    Log.d("onFailure: ", t.getMessage());

                                }
                            });
                        }

                    }

                });
                rvHistory.setLayoutManager(new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                rvHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.e("onFailureGetScheduleUser: ", t.getMessage());
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        rvHistory = (RecyclerView) findViewById(R.id.rvHistory);
    }
}