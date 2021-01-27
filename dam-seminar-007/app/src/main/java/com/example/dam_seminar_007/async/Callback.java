package com.example.dam_seminar_007.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
