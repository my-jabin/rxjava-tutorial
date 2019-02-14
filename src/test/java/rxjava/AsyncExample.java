package rxjava;

import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AsyncExample {

    // understanding different Schedulers: https://proandroiddev.com/understanding-rxjava-subscribeon-and-observeon-744b0c6a41ea


    // subscribeOn tells the Observable which thread it will operate on.

    @Test
    public void subscribeOn() throws InterruptedException {

        Observable.fromCallable(() -> {
            System.out.println("performing some expensive computation on the thread : " + Thread.currentThread().getName());
            Thread.sleep(1000); // some expensive computation
            return 18;
        })
                .subscribeOn(Schedulers.newThread()) // which thread to emit item on. All operators all the way down are executed on this thread.
                .map(age -> {
                    System.out.println("mapping on the thread: " + Thread.currentThread().getName());
                    return String.valueOf(age);
                })
                .subscribe(ageStr -> {
                    System.out.println("subscriber: showing age(" + ageStr + ") on the thread " + Thread.currentThread().getName());
                });

        Thread.sleep(2000);
    }

    // observeOn() switch the working thread for all remaining thread.

    @Test
    public void observeOn() throws InterruptedException {

        Observable.fromCallable(() -> {
            System.out.println("performing some expensive computation on the thread : " + Thread.currentThread().getName());
            Thread.sleep(1000);     // some expensive computation
            return 18;
        })
                .subscribeOn(Schedulers.newThread()) // which thread to emit item on.
                .map(age -> {
                    System.out.println("first mapping on the thread: " + Thread.currentThread().getName());
                    return String.valueOf(age);
                })
                .observeOn(Schedulers.single()) // switch to another thread, pass the emitted item to this thread for the remaining operations.
                .map(str -> {
                    System.out.println("second mapping on the thread: " + Thread.currentThread().getName());
                    return str;
                })
                .observeOn(Schedulers.newThread()) // switch to a new thread, so the subscribe() works on this thread
                .subscribe(ageStr -> {
                    System.out.println("subscriber: showing age(" + ageStr + ") on the thread " + Thread.currentThread().getName());
                });

        Thread.sleep(2000);
    }

    @Test
    public void example() {
        // (1) with a given client_id making remote request to obtain a token.
        // (2) with the token and a first name to obtain a list of user detail information.
        // (3) if the list is empyt, throw a UserNotFoundException
        // notice: the client_id might not valid, so we could only get a token or nothing
    }
}