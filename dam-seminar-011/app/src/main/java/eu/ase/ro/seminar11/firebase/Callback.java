package eu.ase.ro.seminar11.firebase;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
