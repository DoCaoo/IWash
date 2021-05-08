package com.duantotnghiep.iwash.staff.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.adapter.ScheduleAdapter;
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

public class ProcessingScheduleFragment extends Fragment {
    private RecyclerView rvSchedule;
    private List<Schedule> scheduleList;
    ScheduleAdapter scheduleAdapter;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_processing_schedule, container, false);
        initView(view);
        scheduleList = new ArrayList<>();
        fragmentManager = getActivity().getSupportFragmentManager();
        RetrofitClient.getInstance().create(ApiService.class).getAllSchedule().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                List<Schedule> schedules = response.body().schedules;
                if (response.body().success) {
                    for (int i = 0; i < schedules.size(); i++) {
                        if (schedules.get(i).getStatus().equals("Confirmed")) {
                            scheduleList.add(schedules.get(i));
                        }
                    }
                    scheduleAdapter = new ScheduleAdapter(scheduleList, getActivity(), (view1, pos) ->
                            RetrofitClient.getInstance().create(ApiService.class).complete(scheduleList.get(pos).getId(), "Completed").enqueue(new Callback<ServerResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<ServerResponse> call1, @NonNull Response<ServerResponse> response1) {
                                    if (response1.body().success) {
                                        ProgressDialog dialog = new ProgressDialog(getContext());
                                        dialog.setMessage("Đang tải");
                                        dialog.show();
                                        new Handler().postDelayed(() -> {
                                            scheduleList.remove(pos);
                                            scheduleAdapter.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }, 2000);
                                        Toast.makeText(getActivity(), "Lịch đã hoàn thành", Toast.LENGTH_SHORT).show();
                                        //setCurrentFragment(new CompletedScheduleFragment());
                                    } else {
                                        Toast.makeText(getActivity(), "Khách hàng chưa lấy xe, chưa thể hoàn thành lịch", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<ServerResponse> call12, @NonNull Throwable t) {
                                    Log.d("onFailure: ", t.getMessage());
                                }
                            }));
                    rvSchedule.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rvSchedule.setAdapter(scheduleAdapter);
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
        rvSchedule = view.findViewById(R.id.rvProcessingSchedule);
    }

    private void setCurrentFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.flFragment2, fragment).commit();
    }
}