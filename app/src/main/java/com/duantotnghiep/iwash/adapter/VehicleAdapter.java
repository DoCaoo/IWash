package com.duantotnghiep.iwash.adapter;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.callback.ItemClick;
import com.duantotnghiep.iwash.model.Vehicle;

import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleHolder> {
    private Context context;
    private List<Vehicle> vehicles;
    private ItemClick itemClick;

    public VehicleAdapter(Context context, List<Vehicle> vehicles, ItemClick itemClick) {
        this.context = context;
        this.vehicles = vehicles;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public VehicleAdapter.VehicleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vehicles, parent, false);
        return new VehicleAdapter.VehicleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleHolder holder, int position) {
        holder.tvNameOfVehicle.setText(vehicles.get(position).getName());
        holder.tvLicense.setText(vehicles.get(position).getLicense());
        Log.e("onBindViewHolder: ", vehicles.get(position).getType());
        if (vehicles.get(position).getType().equals("Car")) {
            Log.e("onBindViewHolder1: ", vehicles.get(position).getType());
            holder.imgVehicle.setImageResource(R.drawable.carcar);
        } else if (vehicles.get(position).getType().equals("Motorcycle")) {
            Log.e("onBindViewHolder2: ", vehicles.get(position).getType());
            holder.imgVehicle.setImageResource(R.drawable.scooter);
        }
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class VehicleHolder extends RecyclerView.ViewHolder {
        ImageView imgVehicle, imgDelete, imgUpdate;
        TextView tvNameOfVehicle, tvLicense;

        public VehicleHolder(@NonNull View itemView) {
            super(itemView);
            imgVehicle = (ImageView) itemView.findViewById(R.id.imgVehicle);
            tvNameOfVehicle = (TextView) itemView.findViewById(R.id.tvNameOfVehicle);
            tvLicense = (TextView) itemView.findViewById(R.id.tvLicense);
            imgDelete = (ImageView) itemView.findViewById(R.id.imgDelete);
            imgUpdate = (ImageView) itemView.findViewById(R.id.imgUpdate);
            imgUpdate.setOnClickListener(v -> {
                itemClick.setOnItemClick(v, getAdapterPosition());
            });
            imgDelete.setOnClickListener(v -> {
                itemClick.setOnItemClick(v, getAdapterPosition());
            });
        }
    }
}
