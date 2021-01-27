package com.example.dam_seminar_010.async;

public class HandlerMessage<R> implements Runnable{

    // Este trimis RunnableTask după  ce a fost
    // primit din firul principal (Activitate/Fragment)
    private final Callback<R> mainThreadOperation;

    // Este trimis din Runnabletask.
    private final R result;

    public HandlerMessage(Callback<R> mainThreadOperation,
                          R result) {
        this.mainThreadOperation = mainThreadOperation;
        this.result = result;
    }

    @Override
    public void run() {
        // Se trimite rezultatul în Activitate/Fragment.
        mainThreadOperation.runResultOnUIThread(result);
    }
}
