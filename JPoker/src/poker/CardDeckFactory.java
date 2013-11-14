package poker;

/**
 * User: Sina
 * Date: 11/14/13
 */
public abstract class CardDeckFactory {
    public abstract CardDeck getCardDeck();

    public static CardDeckFactory getCardDeckFactory() {
        return new ShuffledCardDeckFactory();
    }
}
