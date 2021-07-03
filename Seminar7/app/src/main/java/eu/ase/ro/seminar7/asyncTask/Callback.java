package eu.ase.ro.seminar7.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
