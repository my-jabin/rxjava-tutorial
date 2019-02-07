package rxjava;


import org.junit.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;


public class BackpressureExample {


    @Test
    public void whatIsTheProblem() throws InterruptedException {
        // every second emit 1000 items.
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation()) // async. In a different thread(scheduler)
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                    // every second consume only 1 items, the rest is buffed. 999 items added to the Buffer every second. The buffer is unbounded.
                    // as time goes on, it might cause OOM.
                });
        // basically, some asynchronous stages can't process the values fast enough and need a strategy to handle this situation.
        Thread.sleep(50000);
    }

    @Test
    public void backpressureException1() throws InterruptedException {
        // flowable default buffer size = 128
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation())
                .subscribe(l -> {
                    Thread.sleep(2000);
                    System.out.println(l);
                });

        Thread.sleep(50000);
    }

    @Test
    public void backpressureException2() throws InterruptedException {
        PublishProcessor<Integer> source = PublishProcessor.create();

        source
                .observeOn(Schedulers.computation())
                .subscribe(v -> compute(v), Throwable::printStackTrace);

        // hot observable
        for (int i = 0; i < 1_000_000; i++) {
            source.onNext(i);
        }

        Thread.sleep(10_000);
    }


    private void compute(Integer v) {
        System.out.println(v);
    }
}