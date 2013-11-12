import exceptions.OutOfCardsException;
import poker.*;
import processors.SettingDecider;
import processors.StaticSettingDecider;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class Main {
    public static void main(String[] args) throws OutOfCardsException {
        SettingDecider settingDecider = new StaticSettingDecider();
        Dealer dealer = new Dealer(settingDecider.getGameSetting(), settingDecider.getExternalNotifiables());
        dealer.runGame(8);
    }

    public static void testHands() {
        Card card1 = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        Card card2 = new Card(Card.Rank.EIGHT, Card.Suit.HEARTS);
        Card card3 = new Card(Card.Rank.EIGHT, Card.Suit.SPADES);
        Card card4 = new Card(Card.Rank.SEVEN, Card.Suit.SPADES);
        BoardCards board = new BoardCards();
        board.cards[0] = new Card(Card.Rank.NINE, Card.Suit.HEARTS);
        board.cards[1] = new Card(Card.Rank.NINE, Card.Suit.CLUBS);
        board.cards[2] = new Card(Card.Rank.ACE, Card.Suit.CLUBS);
        board.cards[3] = new Card(Card.Rank.QUEEN, Card.Suit.SPADES);
        board.cards[4] = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        HandType handType1 = new HandTypeFinder(new PreflopCards(card1, card2), board).findHandType();
        HandType handType2 = new HandTypeFinder(new PreflopCards(card3, card4), board).findHandType();
        int i = handType1.compareTo(handType2);
    }
}
