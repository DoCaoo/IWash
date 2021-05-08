package com.duantotnghiep.iwash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.Schedule;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    Context context;
    List<Schedule> schedules;
    ItemClick itemClick;

    public HistoryAdapter(Context context, List<Schedule> schedules, ItemClick itemClick) {
        this.context = context;
        this.schedules = schedules;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String time = schedules.get(position).getTimeBook();
        String hour = time.substring(0, 6);
        String date = time.substring(7, time.length());
        holder.tvTime.setText("Lúc: " + hour + " Ngày: " + date);
        String service = "";
        for (int i = 0; i < schedules.get(position).getServices().size(); i++) {
            service = schedules.get(position).getServices().get(i).getName();
        }
        service = service + "";
        holder.tvVehicle.setText(schedules.get(position).getVehicle().getName());
        if (schedules.get(position).getStatus().equals("Confirmed") && !schedules.get(position).getVehicleStatus()) {
            holder.btnConfirmVehicle.setVisibility(View.VISIBLE);
        }
        if (schedules.get(position).getStatus().equals("Pending")) {
            holder.btnCancelSchedule.setVisibility(View.VISIBLE);
        }
        holder.tvStatus.setText(schedules.get(position).getStatus());
        holder.tvService.setText(service);
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSchedule;
        TextView tvTime, tvVehicle, tvStatus, tvService;
        TextView btnConfirmVehicle, btnCancelSchedule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSchedule = itemView.findViewById(R.id.imgSchedule);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvVehicle = itemView.findViewById(R.id.tvVehicle);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvService = itemView.findViewById(R.id.tvService);
            btnConfirmVehicle = itemView.findViewById(R.id.btnConfirmVehicle);
            btnCancelSchedule = itemView.findViewById(R.id.btnCancelSchedule);
            btnCancelSchedule.setOnClickListener(v -> itemClick.setOnItemClick(v, getAdapterPosition()));
            btnConfirmVehicle.setOnClickListener(v -> itemClick.setOnItemClick(v, getAdapterPosition()));
        }
    }
}