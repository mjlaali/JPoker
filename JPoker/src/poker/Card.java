package poker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 5:53:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Card {
    public enum Rank {
        DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

        public int value;

        Rank(int value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
        	if (value < 10)
        		return "" + value;
        	else {
        		switch (value) {
        		case 10:
        			return "T";
				case 11:
					return "J";
				case 12:
					return "Q";
				case 13:
					return "K";
				case 14:
					return "A";
				default:
					return null;
				}
        	}
        		
        }
    }

    public enum Suit {
        CLUBS, DIAMONDS, HEARTS, SPADES
    }

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Card) {
            Card c = (Card) obj;
            return rank == c.rank && suit == c.suit;
        }
        return false;
    }

    private static final List<Card> protoDeck = new ArrayList<Card>();

    // Initialize prototype deck
    static {
        for (Suit suit : Suit.values())
            for (Rank rank : Rank.values())
                protoDeck.add(new Card(rank, suit));
    }

    public static ArrayList<Card> newDeck() {
        return new ArrayList<Card>(protoDeck); // Return copy of prototype deck
    }

    public static Card build(String cardStr) {
        if (cardStr.length() != 2) {
            throw new RuntimeException("Invalid card name format: " + cardStr);
        }
        Integer number;
        try {
            number = Integer.valueOf(cardStr.substring(0, 1));
        } catch (NumberFormatException e) {
            switch (cardStr.charAt(0)) {
                case 'A': case 'a': number = 14; break;
                case 'K': case 'k': number = 13; break;
                case 'Q': case 'q': number = 12; break;
                case 'J': case 'j': number = 11; break;
                default:
                    throw new RuntimeException("Invalid card name format: " + cardStr);
            }
        }
        Rank rank = null;
        for (Rank r : Rank.values()) {
            if (r.value == number) {
                rank = r;
            }
        }
        if (rank == null) {
            throw new RuntimeException("Invalid card name format: " + cardStr);
        }
        Suit suit;
        switch (cardStr.charAt(1)) {
            case 'D': suit = Suit.DIAMONDS; break;
            case 'C': suit = Suit.CLUBS; break;
            case 'H': suit = Suit.HEARTS; break;
            case 'S': suit = Suit.SPADES; break;
            default:
                throw new RuntimeException("Invalid card name format: " + cardStr);
        }
        return new Card(rank, suit);
    }
}
