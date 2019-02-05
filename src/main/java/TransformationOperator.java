import io.reactivex.Observable;

public class TransformationOperator {

    public static void main(String[] args) {
//        map();
        //flatMap();
       // buffer();
        groupby();
    }

    public static void map(){
        Observable<Integer> observable = Observable.range(1, 3);
        observable.map(i -> "String: "+i)
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }
    public static void flatMap(){
        Observable<Integer> observable = Observable.range(1, 3);
        observable.flatMap( i -> Observable.range(0,i))
                .subscribe(
                        result -> System.out.println(result),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    public static void buffer(){
        Observable<Integer> observable = Observable.range(0, 10);
        observable.buffer(3,2)
                .subscribe(
                        list -> System.out.println(list),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }

    public static void groupby(){
        Observable<Integer> observable = Observable.range(0, 10);
        observable.groupBy(v ->  v% 2 == 0)
                .subscribe(
                        group -> group.takeLast(1).subscribe(v-> System.out.println(v)),
                        e -> System.out.println("Error: " + e),
                        () -> System.out.println("Completed")
                );
    }
}
