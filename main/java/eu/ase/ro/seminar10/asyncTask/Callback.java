package eu.ase.ro.seminar10.asyncTask;

public interface Callback<R> {

    void runResultOnUiThread(R result);
}
