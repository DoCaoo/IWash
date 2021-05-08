package com.duantotnghiep.iwash.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.activity.CarWashServiceActivity;
import com.duantotnghiep.iwash.activity.HistoryActivity;
import com.duantotnghiep.iwash.activity.NotificationActivity;
import com.duantotnghiep.iwash.activity.VehicleActivity;
import com.duantotnghiep.iwash.adapter.NewsAdapter;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.News;
import com.duantotnghiep.iwash.model.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private ImageView imgAvatar, imgNotification;
    private TextView tvName;
    private TextView tvPhone;
    private List<News> newsList = new ArrayList<>();
    LinearLayout layoutOpenWashService, layoutOpenVehicle, layoutOpenHistory;
    RecyclerView rvNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        loadDataUserInfo();
        getNews();
        layoutOpenWashService.setOnClickListener(v -> startActivity(new Intent(getContext(), CarWashServiceActivity.class)));
        layoutOpenHistory.setOnClickListener(v -> startActivity(new Intent(getContext(), HistoryActivity.class)));
        layoutOpenVehicle.setOnClickListener(v -> startActivity(new Intent(getContext(), VehicleActivity.class)));
        imgNotification.setOnClickListener(v -> startActivity(new Intent(getContext(), NotificationActivity.class)));
        return view;
    }

    private void getNews() {
        RetrofitClient.getInstance().create(ApiService.class).getNews().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.body().success) {
                    newsList = response.body().newsList;
                    Log.e("onResponse: ", String.valueOf(newsList.size()));
                    rvNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    rvNews.setAdapter(new NewsAdapter(getContext(), newsList, new ItemClick() {
                        @Override
                        public void setOnItemClick(View view, int pos) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_news_detail, null, false);
                            ImageView imgNews = v.findViewById(R.id.imgNews);
                            TextView tvTitle = v.findViewById(R.id.tvTitle);
                            TextView tvDescription = v.findViewById(R.id.tvDescription);
                            Glide.with(getActivity()).load(RetrofitClient.link + newsList.get(pos).getImage()).into(imgNews);
                            tvTitle.setText(newsList.get(pos).getTitle());
                            tvDescription.setText(newsList.get(pos).getDescription());
                            alertDialog.setView(v);
                            alertDialog.show();
                        }
                    }));
                } else {
                    Toast.makeText(getActivity(), response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.e("onFailureGetNews: ", t.getMessage());
            }
        });
    }

    private void initView(View view) {
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        imgNotification = (ImageView) view.findViewById(R.id.imgNotification);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        layoutOpenWashService = view.findViewById(R.id.layoutOpenWashService);
        layoutOpenVehicle = view.findViewById(R.id.layoutOpenVehicle);
        layoutOpenHistory = view.findViewById(R.id.layoutOpenHistory);
        rvNews = view.findViewById(R.id.rvNews);
    }

    private void loadDataUserInfo() {
        RetrofitClient.getInstance().create(ApiService.class).getUserInfo().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NonNull Call<ServerResponse> call, @NonNull Response<ServerResponse> response) {
                if (response.body().success) {
                    Glide.with(getActivity()).load(RetrofitClient.link + response.body().user.getAvatar()).into(imgAvatar);
                    tvName.setText(response.body().user.getFullName());
                    tvPhone.setText(response.body().user.getPhoneNumber());
                    RetrofitClient.user = response.body().user;
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServerResponse> call, @NonNull Throwable t) {
                Log.d("onFailure: ", t.getMessage());
            }
        });
    }

}