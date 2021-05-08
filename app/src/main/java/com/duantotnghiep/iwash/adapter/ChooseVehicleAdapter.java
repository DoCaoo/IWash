package com.duantotnghiep.iwash.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.duantotnghiep.iwash.R;
import com.duantotnghiep.iwash.model.Vehicle;

import java.util.List;

public class ChooseVehicleAdapter implements SpinnerAdapter {
    Context context;
    List<Vehicle> vehicleList;


    public ChooseVehicleAdapter(Context context, List<Vehicle> vehicleList) {
        this.context = context;
        this.vehicleList = vehicleList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_vehicles, parent, false);
        TextView tvName;
        TextView tvLicense;
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvLicense = (TextView) convertView.findViewById(R.id.tvLicense);
        tvName.setText(vehicleList.get(position).getName());
        tvLicense.setText(vehicleList.get(position).getLicense());
        return convertView;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return vehicleList.size();
    }

    @Override
    public Vehicle getItem(int position) {
        return vehicleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_vehicles, parent, false);
        TextView tvName;
        TextView tvLicense;
        tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvLicense = (TextView) convertView.findViewById(R.id.tvLicense);
        tvName.setText(vehicleList.get(position).getName());
        tvLicense.setText(vehicleList.get(position).getLicense());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


}
