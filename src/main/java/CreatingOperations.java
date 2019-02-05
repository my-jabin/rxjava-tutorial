import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class CreatingOperations {

    public static void main(String[] args) {

        //just();
//        range();
//        fromArray();
        interval();
    }

    public static void just() {
        Observable<String> observable = Observable.just("Hello", "World");
        observable.subscribe(
                v -> System.out.println("Received:" + v),
                e -> System.out.println("Errir" + e),
                () -> System.out.println("Completed")
        );

    }

    public static void range() {
        Observable<Integer> observable = Observable.range(0, 2);
        observable.subscribe(
                v -> System.out.println("Received:" + v),
                e -> System.out.println("Errir" + e),
                () -> System.out.println("Completed")
        );
    }

    public static void interval() {
        Observable<Long> observable = Observable.interval(1000,1, TimeUnit.SECONDS);
        observable.subscribe(
                v -> System.out.println("Received:" + v),
                e -> System.out.println("Errir" + e),
                () -> System.out.println("Completed")
        );
    }

    public static void fromArray() {
        Integer[] array = {1, 3, 5};
        Observable<Integer> observable = Observable.fromArray(array);
        observable.subscribe(
                v -> System.out.println("Received:" + v),
                e -> System.out.println("Errir" + e),
                () -> System.out.println("Completed")
        );
    }

}
