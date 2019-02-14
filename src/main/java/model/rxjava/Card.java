package model.rxjava;

import java.util.Arrays;
import java.util.Objects;

public class Card implements Comparable<Card> {

    private static final String suits[] = {"\u2665", "\u2666", "\u2663", "\u2660"};  // heart, diamond, club, spade
    private RANK rank;
    private int rankInt;
    private SUIT suit;

    public Card(RANK rank, SUIT suit) {
        this.rank = rank;
        this.rankInt =  rank.ordinal() + 1;
        this.suit = suit;
    }

    public Card(int rank, SUIT suit) {
        rankInt = rank;
        this.rank = RANK.values()[(rank - 1) % 13];
        this.suit = suit;
    }

    public SUIT getSuit(){
        return this.suit;
    }

    public void setRank(RANK rank){
        this.rank = rank;
        this.rankInt = rank.ordinal();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return
                rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rank, suit);
        result = 31 * result + Arrays.hashCode(suits);
        return result;
    }

    @Override
    public String toString() {
        String s;
        switch (suit) {
            case HEART:
                s = suits[0];
                break;
            case DIAMOND:
                s = suits[1];
                break;
            case CLUB:
                s = suits[2];
                break;
            default:
                SPADE:
                s = suits[3];
                break;
        }

        String r;
        switch (rank) {
            case TWO:
                r = "2";
                break;
            case THREE:
                r = "3";
                break;
            case FOUR:
                r = "4";
                break;
            case FIVE:
                r = "5";
                break;
            case SIX:
                r = "6";
                break;
            case SEVEN:
                r = "7";
                break;
            case EIGHT:
                r = "8";
                break;
            case NINE:
                r = "9";
                break;
            case TEN:
                r = "10";
                break;
            default:
                r = rank.toString();
                break;

        }
        return s + r;
    }


    @Override
    public int compareTo(Card c) {
        if(this.rankInt != c.rankInt){
            return this.rankInt - c.rankInt;
        }else{
            return this.suit.ordinal() - c.suit.ordinal();
        }
    }

    public enum SUIT {HEART, DIAMOND, CLUB, SPADE}

    public enum RANK {A, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, J, Q, K}

}