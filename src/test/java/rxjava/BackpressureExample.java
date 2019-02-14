package rxjava;


import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureOverflowStrategy;
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
                    // every second consume only 1 items, the rest is buffed. 999 items added to the Buffer every second. In the early Rxjava, buffer is unbounded.
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
                    Thread.sleep(1000);
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

    @Test
    public void backpressureNoException() throws InterruptedException {
        // There is no error and everything runs smoothly with small memeory usage.
        // The reason for that is many source operators can generate values on the demand
        // and thus the operator observeOn can tell the range generate at most so many values the observeOn buffer can hold at once without overflow.
        Flowable.range(1, 1_000_000)
                .observeOn(Schedulers.computation())
                .subscribe(v -> compute(v), Throwable::printStackTrace);
        Thread.sleep(10_000);
    }


    @Test
    public void increseBufferSize() throws InterruptedException {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.computation(), false, 1024) // increase buffer size to 1024
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                });
        Thread.sleep(20000);
    }

    @Test
    public void onBackpressureBufferExample1() throws InterruptedException {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()         // unbounded buffer
                .observeOn(Schedulers.computation())
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                });
        Thread.sleep(20000);
    }

    @Test
    public void onBackpressureBufferExample2() throws InterruptedException {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer(1024)         // buffer size = 1024, throw exception of  io.reactivex.exceptions.OnErrorNotImplementedException: Buffer is full
                .observeOn(Schedulers.computation())
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                });
        Thread.sleep(20000);
    }

    @Test
    public void onBackpressureBufferHandleOverflow() throws InterruptedException {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer(1024, () -> {
                    // handling the overflow error, we just print a message on the logcat, but do nothing.
                    System.out.println("Handling overflow...");
                })
                .observeOn(Schedulers.computation())
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                });
        Thread.sleep(20000);
    }

    @Test
    public void onBackpressureBufferBufferStrategyDropLatest() throws InterruptedException {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer(1024, () -> {
                }, BackpressureOverflowStrategy.DROP_LATEST)
                .observeOn(Schedulers.computation())
                .subscribe(l -> {
                    Thread.sleep(1000);
                    System.out.println(l);
                });
        Thread.sleep(20000);
    }

    @Test
    public void onBackpressureBufferBufferStrategyDropOldest() throws InterruptedException {
        Flowable.range(1, 1_000_000)
                .onBackpressureBuffer(16, () -> {
                        },
                        BackpressureOverflowStrategy.DROP_OLDEST)
                .observeOn(Schedulers.computation())
                .subscribe(e -> {
                    System.out.println(e);
                }, Throwable::printStackTrace);
        Thread.sleep(20000);
    }

    private void compute(Integer v) {
        System.out.println(v);
    }
}