package poker;

import exceptions.OutOfCardsException;

import java.util.LinkedList;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class CardDeck {
    private LinkedList<Card> cards;

    public CardDeck(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public Card nextCard() throws OutOfCardsException {
        if (cards.isEmpty()) {
            throw new OutOfCardsException();
        }
        return cards.removeFirst();
    }
}
