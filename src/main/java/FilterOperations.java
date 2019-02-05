import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class FilterOperations {

    public static void main(String[] args) {

        //  filter();
        //distinct();;
        //distinctUntilChanged();
        // ignoreElements();
        //take();
        //skip();

        //takeWhile();
//        skipWhile();
        takeUntil();
//        skipUntil();
    }

    public static void skipUntil() {
        Observable<Long> source = Observable.intervalRange(1,10,0,5,TimeUnit.SECONDS);
        Observable<Long> second = Observable.timer(350,TimeUnit.MILLISECONDS);

        source
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }


    public static void takeUntil() {
        Observable<Integer> observable = Observable.range(0, 5);
        observable.takeUntil(v -> v == 2)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }


    public static void filter() {
        Observable<Integer> values = Observable.range(0, 10);
        values.filter(v -> v % 2 == 0)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    public static void distinct() {
        Integer[] array = {1, 1, 2, 2, 3};
        Observable<Integer> values = Observable.fromArray(array);
        values.distinct()
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    public static void distinctUntilChanged() {
        Integer[] array = {1, 1, 2, 3, 2};
        Observable<Integer> values = Observable.fromArray(array);
        values.distinctUntilChanged()
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed")
                );
    }

    public static void ignoreElements() {
        Observable<Integer> values = Observable.range(0, 5);
        values.ignoreElements().subscribe(
                () -> System.out.println("Completed"),
                e -> System.out.println("error")
        );

    }

    public static void take() {
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

    public static void skip() {
        Observable<Integer> values = Observable.range(0, 5);
        values.skip(2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }

    public static void takeWhile() {
        Observable<Integer> values = Observable.range(0, 5);
        values.takeWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }

    public static void skipWhile() {
        Observable<Integer> values = Observable.range(0, 5);
        values.skipWhile(v -> v < 2)
                .subscribe(
                        v -> System.out.println("Recevied: " + v),
                        e -> System.out.println("error"),
                        () -> System.out.println("Completed")
                );
    }
}
