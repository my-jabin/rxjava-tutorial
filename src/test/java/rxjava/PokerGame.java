package rxjava;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.reactivex.Observable;
import model.rxjava.Card;
import model.rxjava.Poker;

public class PokerGame {

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
    }

    @Test
    public void exercise2() {
        // group the cards of player A by SUIT and print it
    }

    @Test
    public void exercise3() {
        // cheating: player B change his smallest card to a K;
        // dirty work, done on a background thread.
    }

    @Test
    public void exercise4() {
        // Who has the special card(heart A) is allowed to play first.
        // every player works on their own thread to check if they have the special card
    }

    @Test
    public void exercise5() {
        // Player A has the power to peek the cards of player B if player A has the special card(heart A)
        // cheating: done on a background thread.
    }

    @Test
    public void exercise6() {
        // after playing a round, call the shuffle() method
    }

    @Test
    public void exercise7() {
        // Player C has the power to play lottery if he has the special card(heart A)
        // Poker class has a method lottery(), return type is a Maybe<Integer>
        // done on a background thread.
    }
}