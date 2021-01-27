package com.example.dam_exercise_003.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
