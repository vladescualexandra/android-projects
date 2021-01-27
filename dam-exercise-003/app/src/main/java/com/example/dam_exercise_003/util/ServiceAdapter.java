package com.example.dam_exercise_003.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dam_exercise_003.R;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Service> {
    
    Context context;
    int resource;
    List<Service> services;
    LayoutInflater inflater;
    
    public ServiceAdapter(Context context, 
                          int resource, 
                          List<Service> services,
                          LayoutInflater inflater) {
        super(context, resource, services);
        this.context = context;
        this.resource = resource;
        this.services = services;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, 
                        @Nullable View convertView, 
                        @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(resource, parent, false);
        Service service = services.get(position);
        
        if (service != null) {
            buildService(view, service);
        }
        
        return view;
    }

    private void buildService(View view, Service service) {
        TextView tv_regNo = view.findViewById(R.id.service_regNo);
        TextView tv_department = view.findViewById(R.id.service_department);
        TextView tv_costs = view.findViewById(R.id.service_costs);
        TextView tv_date = view.findViewById(R.id.service_date);

        tv_regNo.setText(String.valueOf(service.getRegNo()));
        tv_department.setText(service.getDepartment());
        tv_costs.setText(String.valueOf(service.getCosts()));
        tv_date.setText(service.getDate().toString());
    }
}
