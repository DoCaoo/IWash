package com.duantotnghiep.iwash.staff.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.activity.LoginActivity;
import com.duantotnghiep.iwash.adapter.ScheduleAdapter;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.Schedule;
import com.duantotnghiep.iwash.model.ServerResponse;
import com.duantotnghiep.iwash.staff.StaffMainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PendingScheduleFragment extends Fragment {
    private RecyclerView rvPendingSchedule;
    private List<Schedule> scheduleList;
    ScheduleAdapter scheduleAdapter;
    Button btnConfirm, btnCancel;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_schedule, container, false);
        initView(view);
        scheduleList = new ArrayList<>();
        fragmentManager = getActivity().getSupportFragmentManager();
        RetrofitClient.getInstance().create(ApiService.class).getAllSchedule().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                List<Schedule> schedules = response.body().schedules;
                if (response.body().success) {
                    for (int i = 0; i < schedules.size(); i++) {
                        if (schedules.get(i).getStatus().equals("Pending")) {
                            scheduleList.add(schedules.get(i));
                        }
                    }
                    scheduleAdapter = new ScheduleAdapter(scheduleList, getActivity(), new ItemClick() {
                        @Override
                        public void setOnItemClick(View v, int pos) {
                            if (v.getId() == R.id.btnConfirm) {
                                RetrofitClient.getInstance().create(ApiService.class).confirm(scheduleList.get(pos).getId(), "Confirmed").enqueue(new Callback<ServerResponse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ServerResponse> call1, @NonNull Response<ServerResponse> response1) {
                                        if (response1.body().success) {
                                            ProgressDialog dialog = new ProgressDialog(getContext());
                                            dialog.setMessage("Đang xác nhận");
                                            dialog.show();
                                            new Handler().postDelayed(() -> {
                                                scheduleList.remove(pos);
                                                scheduleAdapter.notifyDataSetChanged();
                                                dialog.dismiss();
                                            }, 2000);
                                            Toast.makeText(getActivity(), " Xác nhận thành công", Toast.LENGTH_SHORT).show();
                                            //setCurrentFragment(new ProcessingScheduleFragment());
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                                        Log.d("onFailure: ", t.getMessage());
                                    }
                                });
                            } else if (v.getId() == R.id.btnCancel) {
                                AlertDialog builder = new AlertDialog.Builder(getContext()).create();
                                View dialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_cancel_schedule, null);
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
                                                ProgressDialog dialog = new ProgressDialog(getContext());
                                                dialog.setMessage("Đang hủy");
                                                dialog.show();
                                                new Handler().postDelayed(() -> {
                                                    scheduleList.remove(pos);
                                                    scheduleAdapter.notifyDataSetChanged();
                                                    dialog.dismiss();
                                                }, 2000);
                                                Toast.makeText(getActivity(), "Hủy thành công", Toast.LENGTH_SHORT).show();
                                                setCurrentFragment(new CancelledScheduleFragment());
                                                builder.dismiss();
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
                            }
                        }
                    });
                    rvPendingSchedule.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvPendingSchedule.setAdapter(scheduleAdapter);
                } else {
                    Toast.makeText(getActivity(), "Lỗi " + response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.e("onFailureStaffMain: ", t.getMessage());
            }
        });
        return view;
    }

    private void initView(View view) {
        rvPendingSchedule = view.findViewById(R.id.rvPendingSchedule);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnCancel = view.findViewById(R.id.btnCancel);
    }

    private void setCurrentFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.flFragment2, fragment).commit();
    }
}