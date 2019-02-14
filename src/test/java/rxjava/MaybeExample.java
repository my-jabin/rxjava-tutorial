package rxjava;

import org.junit.jupiter.api.Test;

import java.util.Random;

import io.reactivex.Maybe;

public class MaybeExample {

//   a flow with no items, exactly one item or an error.

    @Test
    public void hello() {
        Maybe.just("hello")
                .subscribe(
                        s -> System.out.println(s),     // onSuccess()
                        e -> System.out.println(e),     // onError()
                        () -> System.out.println("completed")); //onComplete()

        Maybe.empty()
                .subscribe(
                        s -> System.out.println(s),
                        e -> System.out.println(e),
                        () -> System.out.println("completed"));
    }

    @Test
    public void t() {
        Maybe.fromCallable(() -> {
            boolean r = new Random().nextBoolean();
            return r ? "hello world" : null;
        }).subscribe(
                str -> System.out.println(str),
                e -> System.out.println(e),
                () -> System.out.println("It is empty"));
    }

}