package com.example.dam_exercise_004.database.service;

import android.content.Context;
import android.telecom.Call;

import com.example.dam_exercise_004.async.AsyncTaskRunner;
import com.example.dam_exercise_004.async.Callback;
import com.example.dam_exercise_004.database.DatabaseManager;
import com.example.dam_exercise_004.database.dao.BusRideDao;
import com.example.dam_exercise_004.database.model.BusRide;

import java.util.List;
import java.util.concurrent.Callable;

public class BusRideService {

    private final BusRideDao brDao;
    private final AsyncTaskRunner taskRunner;

    public BusRideService(Context context) {
        brDao = DatabaseManager.getInstance(context).getBusRideDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<BusRide>> callback) {
        Callable<List<BusRide>> callable = new Callable<List<BusRide>>() {
            @Override
            public List<BusRide> call() throws Exception {
                return brDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(final BusRide busRide, Callback<BusRide> callback) {
        Callable<BusRide> callable = new Callable<BusRide>() {
            @Override
            public BusRide call() throws Exception {
                if (busRide == null) {
                    return null;
                } else {
                    long id = brDao.insert(busRide);
                    if (id == -1) {
                        return null;
                    } else {
                        busRide.setId(id);
                        return busRide;
                    }
                }
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
