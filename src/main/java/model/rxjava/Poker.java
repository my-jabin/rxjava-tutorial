package model.rxjava;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public class Poker {

    private static final int SIZE = 52;
    private static List<Card> allCard = new ArrayList<>();
    private Map<String, List<Card>> result = new HashMap<>();

    static {
        for (int i = 1; i < 14; i++) {
            allCard.add(new Card(i, Card.SUIT.HEART));
            allCard.add(new Card(i, Card.SUIT.DIAMOND));
            allCard.add(new Card(i, Card.SUIT.CLUB));
            allCard.add(new Card(i, Card.SUIT.SPADE));
        }
    }

    private int count;
    private ArrayList<String> nameList;

    public Poker(int count, String... names) {
        if (count != names.length)
            throw new IllegalArgumentException("Illegal argument.");
        this.count = count;
        this.nameList = new ArrayList<>(Arrays.asList(names));
        shuffle();
    }

    public void shuffle() {
        if (count != nameList.size())
            throw new IllegalArgumentException("Illegal argument.");
        Collections.shuffle(allCard);

        int c = SIZE / count;
        int r = SIZE % count;

        int from = 0;
        for (int i = 0; i < count; i++) {
            int to = from + c;
            if (i < r) {
                to++;
            }
            result.put(nameList.get(i), allCard.subList(from, to));
            from = to;
        }
        // result.forEach((s, cards) -> System.out.println(s + "  " + cards.size()));
    }

    public Observable<Card> getCardsByName(String name) {
        if (!nameList.contains(name))
            throw new IllegalArgumentException("Please provides valid name");
        return Observable.fromIterable(result.getOrDefault(name, null));
    }

    public Maybe<Integer> lottery() {
        Random r = new Random();
        boolean win = r.nextBoolean();
        return win ? Maybe.just(r.nextInt(1000)) : Maybe.empty();
    }
}