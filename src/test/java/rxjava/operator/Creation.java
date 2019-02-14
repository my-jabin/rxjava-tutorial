package rxjava.operator;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import model.Rectangle;

public class Creation {

    @Test
    public void create() {
        Observable<Rectangle> o = createRectangle();
        o.subscribe(
                r -> System.out.println("Area: " + r.calculateArea()),
                e -> System.out.println(e),
                () -> System.out.println("completed")
        );
    }

    private Observable<Rectangle> createRectangle() {
        return Observable.create(new ObservableOnSubscribe<Rectangle>() {
            @Override
            public void subscribe(ObservableEmitter<Rectangle> emitter) throws Exception {
                Rectangle r = new Rectangle();
                r.setHeight(2);
                r.setWidth(2);
                emitter.onNext(r);
                Thread.sleep(2000);
                emitter.onComplete();
            }
        });
    }

    @Test
    public void fromArray() {
        String[] anArray = {"hello", "world"};
        Observable.fromArray(anArray)
                .subscribe(
                        item -> System.out.println(item)  // subscriber
                );
        //An operator emits items; a subscriber consumes those items
    }

    @Test
    public void fromArray2() {
        String[] anArray = {"c++", "java"};
        Observable.fromArray(anArray)
                .subscribe(
                        item -> System.out.println(item),  // subscriber
                        e -> System.out.println("error = " + e),
                        () -> System.out.println("onComplete") // onComplete
                );
        //An operator emits items; a subscriber consumes those items
    }

    @Test
    public void interval() throws InterruptedException {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .subscribe(
                        t -> System.out.println(t),  // onNext()
                        e -> System.out.println("error = " + e),  //onError()
                        () -> System.out.println("completed") // onComplete(),
                );

        Thread.sleep(5000);
    }

    @Test
    public void range() {
        Observable.range(1, 10)
                .subscribe(integer -> System.out.println("count : " + integer));
    }

    @Test
    public void empty() {
        Observable.empty().subscribe(o -> System.out.println("It an empty object"));
    }

    @Test
    public void timer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(l -> System.out.println("after 3 second emit a 0L"));
    }

    @Test
    public void fromCallable() {
        System.out.println("start: fromCallable()");
        Observable.fromCallable(() -> {
            // Find username from database
            Thread.sleep(1000);
            return "Yanbin";
        }).subscribe(
                name -> System.out.println(name), // onNext()
                e -> System.out.println("error = " + e), // onError()
                () -> System.out.println("I find the user name") // onComplete()
        );
    }
}