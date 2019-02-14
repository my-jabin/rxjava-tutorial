package rxjava.operator;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Transformation {

    @Test
    public void map() {
        Observable<Integer> observable = Observable.range(0, 3);
        observable.map(i -> "String: " + i)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void flatMap() {
        // transform the emitted items into Observable
        Observable<Integer> observable = Observable.range(1, 3);
        observable.flatMap(i -> Observable.just(100 + i))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void concatMap() {
        Observable<Integer> observable = Observable.range(1, 3);
        observable.concatMap(i -> Observable.just(100 + i))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void switchMap() {
        Observable<Integer> observable = Observable.range(1, 3);
        observable.switchMap(i -> Observable.just(100 + i))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    // what is the difference between flatMap, concatMap and switchMap ?
    @Test
    public void differenceFlatmap() {
        // flatMap merges the emissions of the resulting Observables
        // flatMap does not care about the order of the items
        int delays[] = {2000, 1000, 1};
        Observable<Integer> observable = Observable.range(0, 3);
        observable.flatMap(i -> Observable.just(100 + i).delay(delays[i], TimeUnit.MICROSECONDS))
                .blockingSubscribe(
                        result -> System.out.println(result)
                );
    }

    @Test
    public void differenceConcatMap() {
        // concatMap does care about the order of the items
        int delays[] = {2000, 1000, 1};
        Observable<Integer> observable = Observable.range(0, 3);
        observable.concatMap(i -> Observable.just(100 + i).delay(delays[i], TimeUnit.MICROSECONDS))
                .blockingSubscribe(
                        result -> System.out.println(result)
                );
    }

    @Test
    public void differenceSwitchMap() throws InterruptedException {
        // switchMap emit only the most recently operator item
        int delays[] = {2000, 1000, 1};
        Observable<Integer> observable = Observable.range(0, 3);
        observable.switchMap(i -> Observable.just(100 + i).delay(delays[i], TimeUnit.MILLISECONDS))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
        Thread.sleep(4000);
    }


    @Test
    public void buffer() {
        // gather a group number of items into a buffer and emit the items at once.
        Observable<Integer> observable = Observable.range(0, 10);
        observable.buffer(3)
                .subscribe(
                        list -> System.out.println(list),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    @Test
    public void groupby() {
        Observable<Integer> observable = Observable.range(0, 10);
        observable.groupBy(v -> v % 2 == 0)  // 2 groups, one for even, one for odd
                .subscribe(
                        // group is a GroupObservable object
                        // take the last item in each group and print it out.
                        group -> group.takeLast(1).subscribe(v -> System.out.println(v)),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }
}