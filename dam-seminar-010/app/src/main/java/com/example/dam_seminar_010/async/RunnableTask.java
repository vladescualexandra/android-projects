package com.example.dam_seminar_010.async;

import android.os.Handler;

import java.util.concurrent.Callable;

public class RunnableTask<R> implements Runnable{

    private final Handler handler;
    // Este trimis din firul principal (Activitate/Fragment).
    private final Callable<R> asyncOperation;
    // Este trimis din firul principal (Activitate/Fragment).
    private final Callback<R> mainThreadOperation;


    // Această clasă implementează Runnable pentru a o considera un Thread.
    // Este un template pentru că nu știm tipul rezultatului pe care operația
    // asincronă îl va returna.
    public RunnableTask(Handler handler,
                        Callable<R> asyncOperation,
                        Callback<R> mainThreadOperation) {
        this.handler = handler;
        this.asyncOperation = asyncOperation;
        this.mainThreadOperation = mainThreadOperation;
    }

    @Override
    public void run() {
        try {
            // Apelăm declanșarea operației asincrone, care se
            // realizează pe acest thread.
            final R result = asyncOperation.call();

            // Rezultatul obținut mai sus este trimis către handler
            // prin intermediul unui obiect de tipul clasei HandlerMessage.
            // Scopul acestui apel de post este de a notifica Handler-ul
            // că dorim să trimitem rezultatul R în bucata de cod referită
            // de mainThreadOperation (care este implementată în MainActivity).
            handler.post(new HandlerMessage<>(mainThreadOperation, result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
