package rxjava.operator;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Filtering {

    // examples on https://github.com/ReactiveX/RxJava/wiki/Filtering-Observables

    @Test
    public void skipUntil() {
        Observable<Long> source = Observable.intervalRange(1, 10, 0, 5, TimeUnit.SECONDS);
        Observable<Long> second = Observable.timer(350, TimeUnit.MILLISECONDS);

        source
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void takeUntil() {
        Observable<Integer> observable = Observable.range(0, 5);
        observable.takeUntil(v -> v == 2)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void filter() {
        Observable<Integer> values = Observable.range(0, 10);
        values.filter(v -> v % 2 == 0)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void distinct() {
        Integer[] array = {1, 1, 2, 2, 3};
        Observable<Integer> values = Observable.fromArray(array);
        values.distinct()
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void distinctUntilChanged() {
        Integer[] array = {1, 1, 2, 3, 2};
        Observable<Integer> values = Observable.fromArray(array);
        values.distinctUntilChanged()
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void ignoreElements() {
        Observable<Integer> values = Observable.range(0, 5);
        values.ignoreElements().subscribe(
                () -> System.out.println("Completed"),
                e -> System.out.println("error")
        );

    }

    @Test
    public void take() {
        Observable<Integer> values = Observable.create(o -> {
            o.onNext(1);
            o.onNext(1);
            o.onError(new Exception("Oops"));
        });

        values.take(1).subscribe(
                v -> System.out.println("Recevied: " + v),
                e -> System.out.println("error"),
                () -> System.out.println("Completed")
        );

    }

    @Test
    public void skip() {
        Observable<Integer> values = Observable.range(0, 5);
        values.skip(2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void takeWhile() {
        Observable<Integer> values = Observable.range(0, 5);
        values.takeWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void skipWhile() {
        Observable<Integer> values = Observable.range(0, 5);
        values.skipWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }
}