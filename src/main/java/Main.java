import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//        Observable<String> observable = Observable.just("Hello World");
//        observable.subscribe(s -> System.out.println(s));
//
//        long startTime = System.currentTimeMillis();
//        Observable.intervalRange(1, 5, 0, 500, TimeUnit.MILLISECONDS)
//                .subscribe(emitTime -> {
//                    System.out.println(emitTime);
//                });


//        Observable.interval(100, TimeUnit.MILLISECONDS).take(5)
//                .delay(1, TimeUnit.SECONDS)
//                .subscribe(System.out::println);

//        DisposableObserver d =  Observable.interval(10,TimeUnit.MILLISECONDS)
        DisposableObserver d =   Observable.intervalRange(0,100,4000,10,TimeUnit.MILLISECONDS)
//                .delay(4,TimeUnit.SECONDS)
                .doOnSubscribe(disposable -> System.out.println("do on subscribe"))
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    protected void onStart() {
                        System.out.println("start");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                    }
                });

        Thread.sleep(10000);
    }
}
