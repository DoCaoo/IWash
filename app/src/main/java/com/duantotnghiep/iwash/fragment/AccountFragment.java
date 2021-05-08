package com.duantotnghiep.iwash.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.activity.ChangePasswordActivity;
import com.duantotnghiep.iwash.activity.HistoryActivity;
import com.duantotnghiep.iwash.activity.LoginActivity;
import com.duantotnghiep.iwash.activity.NotificationActivity;
import com.duantotnghiep.iwash.activity.UserDetailActivity;
import com.duantotnghiep.iwash.api.ApiService;
import com.duantotnghiep.iwash.api.RetrofitClient;
import com.duantotnghiep.iwash.model.ServerResponse;
import com.duantotnghiep.iwash.model.User;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {

    private ImageView imgAvatar;
    private TextView tvName;
    private TextView tvPhone;
    private LinearLayout layoutInfo, layoutNoti, layoutHistory, layoutLogout, layoutChangePw;
    private User user;
    String linkAvatar, address;

    public AccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);
        Glide.with(getActivity()).load(RetrofitClient.link + RetrofitClient.user.getAvatar()).into(imgAvatar);
        tvName.setText(RetrofitClient.user.getFullName());
        tvPhone.setText(RetrofitClient.user.getPhoneNumber());
        setOnclick();
        return view;
    }

    private void initView(View view) {
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        layoutInfo = (LinearLayout) view.findViewById(R.id.layoutInfo);
        layoutNoti = (LinearLayout) view.findViewById(R.id.layoutNoti);
        layoutHistory = (LinearLayout) view.findViewById(R.id.layoutHistory);
        layoutLogout = (LinearLayout) view.findViewById(R.id.layoutLogout);
        layoutChangePw = view.findViewById(R.id.layoutChangePw);
    }

    private void setOnclick() {
        layoutInfo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), UserDetailActivity.class);
            startActivity(intent);
        });
        layoutHistory.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), HistoryActivity.class));
        });
        layoutNoti.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), NotificationActivity.class));
        });
        layoutChangePw.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ChangePasswordActivity.class));
        });
        layoutLogout.setOnClickListener(v -> {
            ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setMessage("Log out");
            dialog.show();
            new Handler().postDelayed(() -> {
                startActivity(new Intent(getContext(), LoginActivity.class));
                dialog.dismiss();
                getActivity().finish();
            }, 2000);
        });
    }

}