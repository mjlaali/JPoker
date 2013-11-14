package poker;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: Sina
 * Date: 11/14/13
 */
public class ShuffledCardDeckFactory extends CardDeckFactory {
    @Override
    public CardDeck getCardDeck() {
        LinkedList<Card> cards = new LinkedList<>();
        List<Card> allCards = Card.newDeck();
        Random random = new Random();
        for (int i = 52; i > 0; i--) {
            int rand = random.nextInt(i);
            cards.add(allCards.remove(rand));
        }
        return new CardDeck(cards);
    }
}
