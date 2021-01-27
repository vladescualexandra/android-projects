package com.example.dam_exercise_004.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
