package rxjava;

import org.junit.jupiter.api.Test;

import io.reactivex.Single;

public class SingleExample {

    // it can only emit either a single successful value or an error. There is no "onComplete()"
    // Single has onSuccess(o) and onError(e) methods.

    @Test
    public void hello() {
        Single.just("Hello world")
                .subscribe((s, throwable) -> System.out.println(s));
    }
}