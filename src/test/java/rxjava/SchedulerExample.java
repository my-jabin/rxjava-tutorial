package rxjava;

import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;


public class SchedulerExample {

    @Test
    public void io() {
        Observable.just(1, 3, 5, 7, 9)
                .subscribeOn(Schedulers.io())
                .subscribe(n -> System.out.println(n));

        Observable.just(2, 4, 6, 8, 0)
                .subscribeOn(Schedulers.io())
                .subscribe(n -> System.out.println(n));
    }

    @Test
    public void trampoline() {
        Observable.just(1, 3, 5, 7, 9)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(n -> System.out.println(n));

        Observable.just(2, 4, 6, 8, 0)
                .subscribeOn(Schedulers.trampoline())
                .subscribe(n -> System.out.println(n));

    }

    @Test
    public void single() {
        Observable.just(1, 3, 5, 7, 9)
                .subscribeOn(Schedulers.single())
                .subscribe(n -> System.out.println(n));

        Observable.just(2, 4, 6, 8, 0)
                .subscribeOn(Schedulers.single())
                .subscribe(n -> System.out.println(n));

    }

}