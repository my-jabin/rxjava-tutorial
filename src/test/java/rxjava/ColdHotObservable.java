package rxjava;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

public class ColdHotObservable {


    //    They start emitting items only after an observer subscribes to it and
    //    so the subscriber is guaranteed to receive all the items emitted from the start.
    @Test
    public void cold1() {
        Observable<Integer> observable = Observable.just(new Random().nextInt());

        // subscription 1
        observable.subscribe(o -> System.out.println(o));

        // subscription 2
        observable.subscribe(o -> System.out.println(o));
    }

    @Test
    public void cold2() throws InterruptedException {
        Observable observable = Observable.interval(200, TimeUnit.MILLISECONDS);

        // subscription 1
        observable.subscribe(o -> System.out.println("First subscriber: " + o));
        Thread.sleep(500);
        // subscription 2
        observable.subscribe(o -> System.out.println("Second subscriber: " + o));

        Thread.sleep(2000);
    }

    @Test
    public void hot() throws InterruptedException {
        ConnectableObservable observable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();

        observable.connect();// start to emit items to its subscribers. All of the subscribers will receive the same events at the same time

        // subscription 1
        observable.subscribe(o -> System.out.println("First subscriber: " + o));
        Thread.sleep(500);
        // subscription 2
        observable.subscribe(o -> System.out.println("Second subscriber: " + o));

        Thread.sleep(2000);
    }

    @Test
    public void disconnect() throws InterruptedException {
        ConnectableObservable observable = Observable.interval(200, TimeUnit.MILLISECONDS).publish();

        Disposable d = observable.connect();// start to emit items to its subscribers. All of the subscribers will receive the same events at the same time

        // subscription 1
        observable.subscribe(o -> System.out.println("First subscriber: " + o));
        Thread.sleep(500);
        // subscription 2
        observable.subscribe(o -> System.out.println("Second subscriber: " + o));

        Thread.sleep(2000);

        System.out.println("disconnecting");
        d.dispose(); // disconnecting

    }
}