package poker;

import poker.Card;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 12:18:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreflopCards {
    private Card card1;
    private Card card2;

    public PreflopCards(Card card1) {
        this.card1 = card1;
    }

    public PreflopCards(Card card1, Card card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    public Card getCard1() {
        return card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public Card[] asArray() {
        return new Card[]{card1, card2};
    }

    public String toString() {
        return card1 + ", " + card2;
    }

    public boolean equals(PreflopCards pc, boolean mindSuit) {
        if (mindSuit) {
            return card1.equals(pc.card1) && card2.equals(pc.card2) || card1.equals(pc.card2) && card2.equals(pc.card1);
        } else {
            return card1.rank() == pc.card1.rank() && card2 == pc.card2 || card1.rank() == pc.card2.rank() && card2.rank() == pc.card1.rank();
        }
    }

    public boolean equals(Object obj) {
        if (obj instanceof PreflopCards) {
            PreflopCards pc = (PreflopCards) obj;
            return equals(pc, true);
        } else if (obj instanceof Card[]) {
            Card[] cards = (Card[]) obj;
            if (cards.length == 2) {
                return equals(new PreflopCards(cards[0], cards[1]));
            }
        }
        return false;
    }
}
