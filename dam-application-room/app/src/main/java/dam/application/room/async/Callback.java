package dam.application.room.async;

public interface Callback<R> {
    void runResultOnUIThread(R result);
}
