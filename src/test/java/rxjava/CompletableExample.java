package rxjava;

import org.junit.jupiter.api.Test;

import io.reactivex.Completable;

public class CompletableExample {

    //it can only emit either a completion or error signal (there is no onNext or onSuccess).

    @Test
    public void hello() {
        Completable.complete()
                .subscribe(
                        () -> System.out.println("completed"), // onComplete()
                        e -> System.out.println(e)  // onError(e)
                );
    }

    @Test
    public void fromRunnable() {
        Completable.fromRunnable(() -> {
            System.out.println("Running on the thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        })
                .subscribe(() -> System.out.println("completed"));
    }

    @Test
    public void fromCallable() {
        Completable.fromCallable(() -> {
            System.out.println("Running on the thread " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello world";
        })
                .subscribe(() -> System.out.println("completed")); // cannot get the returned value
    }
}