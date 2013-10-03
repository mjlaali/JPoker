package poker;

import poker.Card;

import java.util.*;

import exceptions.OutOfCardsException;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 8:44:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CardDeck {
    private LinkedList<Card> cards;

    public CardDeck() {
        cards = new LinkedList<Card>();
        List<Card> allCards = Card.newDeck();
        Random random = new Random();
        for (int i = 52; i > 0; i--) {
			int rand = random.nextInt(i);
            cards.add(allCards.remove(rand));
        }
    }

    public Card nextCard() throws OutOfCardsException {
        if (cards.isEmpty()) {
            throw new OutOfCardsException();
        }
        return cards.removeFirst();
    }
}
