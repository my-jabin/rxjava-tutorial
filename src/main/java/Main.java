import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class Main {

    public static void main(String[] args) throws InterruptedException {

//        DisposableObserver d =  Observable.interval(10,TimeUnit.MILLISECONDS)
        DisposableObserver d = Observable.intervalRange(0, 100, 4000, 10, TimeUnit.MILLISECONDS)
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
