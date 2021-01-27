package com.example.dam_exercise_004.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();

    public <R> void executeAsync(Callable<R> asyncOperation,
                                 Callback<R> mainThreadOperation) {
        try {
            executor.execute(new RunnableTask<R>(asyncOperation, handler, mainThreadOperation));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
