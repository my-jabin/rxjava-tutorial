package rxjava;

import io.reactivex.Observable;

public class HelloWorld {

    public static void main(String[] args) {

        // Observable are the source of data
        // Observer listening to the Observables
        // An Observable emit items, an observer consumes those items.

        Observable<String> helloWorld = Observable.just("Hello World!"); // create a Observable
        helloWorld.subscribe(
                str -> System.out.println(str)  // Subscriber consuming items.
        );
    }
}