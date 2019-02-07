package rxjava.operator;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Condition {

    @Test
    public void amb() {
//        given two or more source Observables, emits all of the items from the first of these Observables to emit an item
        Observable interval = Observable.interval(200, TimeUnit.MILLISECONDS);
        Observable<Integer> ints = Observable.range(1, 3);
        Observable intervalDelay = Observable.interval(1000, 1, TimeUnit.MILLISECONDS);
        Observable.ambArray(interval, intervalDelay, ints)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed"));

    }

    @Test
    public void sequenceEqual() {
        // test the equality of the sequences emitted by two Observables
        Observable<Integer> ints = Observable.range(1, 3);
        Observable<String> strings = Observable.just("1", "2", "3");
        Observable
                .sequenceEqual(ints, strings, (i, s) -> s.equals(String.valueOf(i)))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    @Test
    public void defaultIfEmpty() {
        Observable<Integer> observable = Observable.empty();
        observable.defaultIfEmpty(1)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed"));
    }

    @Test
    public void all() {
        Observable<Integer> observable = Observable.range(0, 5);
        observable.all(v -> v < 10)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    @Test
    public void contains() {
        Observable<Integer> observable = Observable.range(0, 2);
        observable.contains(3)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    @Test
    public void takeUntil() {
        Observable<Integer> observable = Observable.range(0, 10);
        observable.takeUntil(i -> i > 5)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    @Test
    public void skipUntil() {
        // every 1 seconds emit a Long object started from 0L
        Observable observable1 = Observable.interval(1, TimeUnit.SECONDS);

        Observable observable2 = Observable.timer(3, TimeUnit.SECONDS);

        // skip the emitted items until a second Observable emits an item.
        observable1.skipUntil(observable2)
                .blockingSubscribe(o -> System.out.println(o));
    }
}