package com.example.dam_exam_subject_001.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
