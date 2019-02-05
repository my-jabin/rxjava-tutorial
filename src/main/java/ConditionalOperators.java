import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

public class ConditionalOperators {
    public static void main(String[] args) {

        //all();
        //contains();
//        defaultIfEmpty();
//        sequenceEqual();
        amb();
    }

    public static void amb(){
        Observable interval = Observable.interval(200,TimeUnit.MILLISECONDS);
        Observable<Integer> ints = Observable.range(1, 3);
        Observable intervalDelay = Observable.interval(1000,1,TimeUnit.MILLISECONDS);
        Observable.ambArray(interval,intervalDelay,ints)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed"));

    }

    public static void sequenceEqual(){
        Observable<Integer> ints = Observable.range(1, 3);
        Observable<String> strings = Observable.just("1","2","3");
        Observable
                .sequenceEqual(ints,strings, (i,s) -> s.equals(String.valueOf(i)))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    public static void defaultIfEmpty() {
        Observable<Integer> observable = Observable.empty();
        observable.defaultIfEmpty(1)
                .subscribe(
                        v -> System.out.println("Received:" + v),
                        e -> System.out.println("Errir" + e),
                        () -> System.out.println("Completed"));
    }

    public static void all() {
        Observable<Integer> observable = Observable.range(0, 5);
        observable.all(v -> v < 10)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

    public static void contains() {
        Observable<Integer> observable = Observable.range(0, 2);
        observable.contains(3)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e)
                );
    }

}
