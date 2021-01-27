package com.example.dam_seminar_010.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    // Handler-ul este obiectul care veghează asupra firului principal de execuție.
    // Păstrează o referință către acesta astfel încât să putem trimite mesaje
    // din alte thread-uri.
    private final Handler handler =
            new Handler(Looper.getMainLooper());

    // Executor este responsabil cu gestionarea thread-urilor, decide momentul optim
    // de a porni un thread (apelare metoda start)
    private final Executor executor =
            Executors.newCachedThreadPool();

    public <R> void executeAsync(Callable<R> asyncOperation,
                                 Callback<R> mainThreadOperation) {
        try {
            // Îi specificăm Executor-ului că dorim să procesăm un Thread
            // de tipul clasei RunnableTask
            executor.execute(new RunnableTask<>(handler, asyncOperation, mainThreadOperation));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
