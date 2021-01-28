package com.example.dam_exam_subject_003.util;

import android.content.Context;

import com.example.dam_exam_subject_003.async.AsyncTaskRunner;
import com.example.dam_exam_subject_003.async.Callback;

import java.util.List;
import java.util.concurrent.Callable;

public class WebsiteService {

    private final WebsiteDao websiteDao;
    private final AsyncTaskRunner taskRunner;

    public WebsiteService(Context context) {
        this.websiteDao = DatabaseManager.getInstance(context).getWebsiteDao();
        this.taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Website>> callback) {
        Callable<List<Website>> callable = new Callable<List<Website>>() {
            @Override
            public List<Website> call() throws Exception {
                return websiteDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Website> callback, final Website website) {
        Callable<Website> callable = new Callable<Website>() {
            @Override
            public Website call() throws Exception {
                if (website == null) {
                    return null;
                } else {
                    long id = websiteDao.insert(website);
                    if (id == -1) {
                        return null;
                    }
                    website.setId(id);
                    return website;
                }
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void filter(Callback<List<Website>> callback, String text) {
        Callable<List<Website>> callable = new Callable<List<Website>>() {
            @Override
            public List<Website> call() throws Exception {
                return websiteDao.filter(text);
            }
        };
    }
}
