package com.example.dam_exam_subject_003.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
