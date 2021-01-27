package com.example.dam_seminar_010.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
