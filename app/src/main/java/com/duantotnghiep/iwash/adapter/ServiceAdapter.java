package com.duantotnghiep.iwash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.model.Service;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {
    private Context context;
    private List<Service> serviceList;

    public ServiceAdapter(Context context, List<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_service, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.chkSelect.setText(serviceList.get(position).getName());
        if (!serviceList.get(position).isStatus()) {
            holder.chkSelect.setEnabled(false);
            holder.chkSelect.setText(serviceList.get(position).getName() +" ( Đang bảo trì )");
        }
        holder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                serviceList.get(position).setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {
        CheckBox chkSelect;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            chkSelect = (CheckBox) itemView.findViewById(R.id.chkSelect);
        }
    }
}
