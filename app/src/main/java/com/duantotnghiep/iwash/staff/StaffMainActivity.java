package com.duantotnghiep.iwash.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.fragment.AccountFragment;
import com.duantotnghiep.iwash.model.Schedule;
import com.duantotnghiep.iwash.staff.fragment.CancelledScheduleFragment;
import com.duantotnghiep.iwash.staff.fragment.PendingScheduleFragment;
import com.duantotnghiep.iwash.staff.fragment.CompletedScheduleFragment;
import com.duantotnghiep.iwash.staff.fragment.ProcessingScheduleFragment;
import com.duantotnghiep.iwash.staff.fragment.StaffAccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class StaffMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager;
    List<Schedule> scheduleListPending;
    List<Schedule> scheduleListConfirm;
    List<Schedule> scheduleListComplete;
    List<Schedule> scheduleListCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_staff);
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        scheduleListPending = new ArrayList<>();
        scheduleListConfirm = new ArrayList<>();
        scheduleListComplete = new ArrayList<>();
        scheduleListCancel = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        setCurrentFragment(new PendingScheduleFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.schedule:
                        ProgressDialog dialog = new ProgressDialog(StaffMainActivity.this);
                        dialog.setMessage("Đang tải");
                        dialog.show();
                        new Handler().postDelayed(() -> {
                            dialog.dismiss();
                            setCurrentFragment(new PendingScheduleFragment());
                        }, 2000);
                        return true;
                    case R.id.perform:
                        ProgressDialog dialog1 = new ProgressDialog(StaffMainActivity.this);
                        dialog1.setMessage("Đang tải");
                        dialog1.show();
                        new Handler().postDelayed(() -> {
                            dialog1.dismiss();
                            setCurrentFragment(new ProcessingScheduleFragment());
                        }, 2000);
                        return true;
                    case R.id.completed:
                        ProgressDialog dialog3 = new ProgressDialog(StaffMainActivity.this);
                        dialog3.setMessage("Đang tải");
                        dialog3.show();
                        new Handler().postDelayed(() -> {
                            dialog3.dismiss();
                            setCurrentFragment(new CompletedScheduleFragment());
                        }, 2000);
                        return true;
                    case R.id.cancelled:
                        ProgressDialog dialog4 = new ProgressDialog(StaffMainActivity.this);
                        dialog4.setMessage("Đang tải");
                        dialog4.show();
                        new Handler().postDelayed(() -> {
                            dialog4.dismiss();
                            setCurrentFragment(new CancelledScheduleFragment());
                        }, 2000);
                        return true;
                    case R.id.account2:
                        ProgressDialog dialog5 = new ProgressDialog(StaffMainActivity.this);
                        dialog5.setMessage("Đang tải");
                        dialog5.show();
                        new Handler().postDelayed(() -> {
                            dialog5.dismiss();
                            setCurrentFragment(new AccountFragment());
                        }, 2000);
                }
                return false;
            }
        });
    }

    private void setCurrentFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.flFragment2, fragment).commit();
    }
}