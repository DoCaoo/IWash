package com.duantotnghiep.iwash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.Schedule;
import com.duantotnghiep.iwash.model.Time;
import com.duantotnghiep.iwash.model.User;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;
import java.util.List;

public class ChooseTimeAdapter extends RecyclerView.Adapter<ChooseTimeAdapter.ViewHolder> {
    private Context context;
    private List<Time> timeList;
    private List<User> staffList;
    private ItemClick itemClick;
    private int itemSelected = -1;
    private String dateBook;
    private List<Schedule> pendingList;

    public ChooseTimeAdapter(Context context, List<Time> timeList, String dateBook, List<User> staffList, List<Schedule> pendingList, ItemClick itemClick) {
        this.context = context;
        this.timeList = timeList;
        this.itemClick = itemClick;
        this.dateBook = dateBook;
        this.pendingList = pendingList;
        this.staffList = staffList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_time, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvChoosedTime.setText(timeList.get(position).getTime());
        holder.tvChoosedTime.setBackgroundColor(Color.YELLOW);
        if (position == itemSelected) {
            holder.tvChoosedTime.setBackgroundColor(Color.YELLOW);
        } else {
            holder.tvChoosedTime.setBackgroundColor(Color.WHITE);
        }
        if (pendingList != null) {
            int count = 0;
            for (int i = 0; i < pendingList.size(); i++) {
                if (pendingList.get(i).getTimeBook().split("@")[1].trim().equals(dateBook)) {
                    if (timeList.get(position).getTime().equals(pendingList.get(i).getTimeBook().split("@")[0].trim())) {
                        count++;
                    }
                }
                if (count >= staffList.size()) {
                    timeList.get(position).setStatus(true);
                } else {
                    timeList.get(position).setStatus(false);
                }
            }
        }
        if (timeList.get(position).isStatus()) {
            holder.tvChoosedTime.setBackgroundColor(Color.RED);
        }
        if (!dateBook.equals("")) {
            for (int i = 0; i < timeList.size(); i++) {
                if (dateBook.split("/")[0].equals(String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH))) && dateBook.split("/")[1].equals(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1))) {
                    if (Integer.parseInt(timeList.get(i).getTime().split(":")[0]) < Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                        timeList.get(i).setTimeOut(false);
                    } else if (Integer.parseInt(timeList.get(i).getTime().split(":")[0]) == Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
                        if (Integer.parseInt(timeList.get(i).getTime().split(":")[1]) < Calendar.getInstance().get(Calendar.MINUTE)) {
                            timeList.get(i).setTimeOut(false);
                        }
                    }
                } else {
                    timeList.get(i).setTimeOut(true);
                }
            }
            if (!timeList.get(position).isTimeOut()) {
                holder.tvChoosedTime.setEnabled(false);
                holder.container.setStrokeColor(Color.GRAY);

            }
        }


    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvChoosedTime;
        MaterialCardView container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChoosedTime = itemView.findViewById(R.id.tvChoosedTime);
            container = itemView.findViewById(R.id.layoutContainer);
            itemView.setOnClickListener(v -> {
                if (timeList.get(getAdapterPosition()).isStatus()) {
                    Toast.makeText(context, "Khung giờ này đã đầy", Toast.LENGTH_SHORT).show();
                } else if (!timeList.get(getAdapterPosition()).isTimeOut()) {
                    Toast.makeText(context, "Thời gian đã qua", Toast.LENGTH_SHORT).show();
                } else if (dateBook.equals("")) {
                    Toast.makeText(context, "Bạn phải chọn ngày trước", Toast.LENGTH_SHORT).show();
                } else {
                    itemClick.setOnItemClick(v, getAdapterPosition());
                    itemSelected = getAdapterPosition();
                    tvChoosedTime.setBackgroundColor(Color.YELLOW);
                    Toast.makeText(context, timeList.get(getAdapterPosition()).getTime(), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }


            });
        }
    }
}
