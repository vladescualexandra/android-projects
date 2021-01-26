package com.example.exercise_002.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
