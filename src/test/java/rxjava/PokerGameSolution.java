package rxjava;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import model.rxjava.Card;
import model.rxjava.Poker;

public class PokerGameSolution {

    static Observable<Card> playerA;
    static Observable<Card> playerB;
    static Observable<Card> playerC;
    static Card specialCard = new Card(Card.RANK.A, Card.SUIT.HEART);
    private static Poker poker;

    @BeforeAll
    public static void before() {
        poker = new Poker(3, "Alice", "Bob", "Christian");
        playerA = poker.getCardsByName("Alice");
        playerB = poker.getCardsByName("Bob");
        playerC = poker.getCardsByName("Christian");

        System.out.print("Alice: ");
        playerA
                .subscribe(card -> {
                    System.out.print(card + " ");
                });
        System.out.println();

        System.out.print("Bob: ");
        playerB
                .subscribe(card -> {
                    System.out.print(card + " ");
                });
        System.out.println();

        System.out.print("Christian: ");
        playerC
                .subscribe(card -> {
                    System.out.print(card + " ");
                });
        System.out.println();
    }

    @Test
    public void exercise1() {
        // find out the smallest card player A have
        playerA.sorted().take(1).subscribe(card -> System.out.println(card));
    }

    @Test
    public void exercise2() {
        // group the cards of player A by SUIT and print it
        playerA
                .groupBy(card -> card.getSuit())
                .concatMapSingle(suitCardGroupedObservable -> suitCardGroupedObservable.toList())
                .subscribe(cards -> System.out.println(cards));
    }

    @Test
    public void exercise3() {
        // cheating: player B change his smallest card to a K;
        // dirty work, done on a background thread.
        playerB
                .subscribeOn(Schedulers.io())
                .sorted()
                .take(1)
                .map(card -> {
                    System.out.println("the smallest card is " + card + " ---- on thread" + Thread.currentThread().getName());
                    card.setRank(Card.RANK.K);
                    return card;
                })
                .subscribe(card -> System.out.println("after magic I got a " + card + " ---- on thread" + Thread.currentThread().getName()));
        playerB
                .doOnComplete(() -> System.out.println())
                .subscribe(card -> System.out.print(card + " "));
    }

    @Test
    public void exercise4() {
        // Who has the special card(heart A) is allowed to play first.
        // every player works on their own thread to check if they have the special card
        playerA
                .subscribeOn(Schedulers.newThread())
                .contains(specialCard)      // Single<Boolean>
                .subscribe((b, throwable) -> System.out.println(b ? "First one to play" : "wait for the first player"));

        playerB
                .subscribeOn(Schedulers.newThread())
                .contains(specialCard)      // Single<Boolean>
                .subscribe((b, throwable) -> System.out.println(b ? "First one to play" : "wait for the first player"));

        playerC
                .subscribeOn(Schedulers.newThread())
                .contains(specialCard)      // Single<Boolean>
                .subscribe((b, throwable) -> System.out.println(b ? "First one to play" : "wait for the frist player"));
    }

    @Test
    public void exercise5() {
        // Player A has the power to peek the cards of player B if player A has the special card(heart A)
        // cheating: done on a background thread.
        playerA
                .subscribeOn(Schedulers.newThread())
                .contains(specialCard)      // Single<Boolean>
                .flatMapObservable(b -> {
                    System.out.println("Does A have the special card? " + b);
                    return b ? poker.getCardsByName("Bob") : Observable.empty();
                })
                .doOnComplete(() -> System.out.println())
                .subscribe(card -> System.out.print(card + " "));
    }

    @Test
    public void exercise6() {
        // after playing a round, call the shuffle() method
        Completable.fromRunnable(() -> {
            System.out.println("Playing....");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).subscribe(() -> poker.shuffle());
    }

    @Test
    public void exercise7() {
        // Player C has the power to play lottery if he has the special card(heart A)
        // Poker class has a method lottery(), return type is a Maybe<Integer>
        // done on a background thread.
        playerC
                .subscribeOn(Schedulers.newThread())
                .contains(specialCard)      // Single<Boolean>
                .flatMapMaybe(b -> b ? poker.lottery() : Maybe.error(new Exception("You are not allowed to play lottery")))
                .subscribe(win -> System.out.println("I win " + win + " euro"),
                        e -> System.out.println(e),
                        () -> System.out.println("Sorry, you got nothing"));
    }
}