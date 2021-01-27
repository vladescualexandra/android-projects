package com.example.dam_exercise_003.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceJSONParser {

    public static List<Service> fromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(Service.SERVICES);
            return readServices(jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Service> readServices(JSONArray array) throws JSONException {
        List<Service> services = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            Service service = getService(array, i);
            services.add(service);
        }
        return services;
    }

    private static Service getService(JSONArray array, int i) throws JSONException {
        JSONObject object = array.getJSONObject(i);
        int regNo = object.getInt(Service.REG_NO);
        String dep = object.getString(Service.DEPARTMENT);
        double costs = object.getDouble(Service.COSTS);
        String strDate = object.getString(Service.DATE);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Service(regNo, dep, costs, date);
    }
}
