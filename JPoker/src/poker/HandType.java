package poker;

import poker.Card;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 1:06:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandType implements Comparable<HandType> {
    private PokerHandType type;
    private Card[] fiveCardsByValue;
    private Card[] preflopCards;
    private Card[] boardCards;

    public HandType(PokerHandType type, Card[] fiveCardsByValue, Card[] pocketCards, Card[] boardCards) {
        this.type = type;
        this.fiveCardsByValue = fiveCardsByValue;
        this.preflopCards = pocketCards;
        this.boardCards = boardCards;
    }

    public PokerHandType getType() {
        return type;
    }

    public void setType(PokerHandType type) {
        this.type = type;
    }

    public Card[] getFiveCardsByValue() {
        return fiveCardsByValue;
    }

    public void setFiveCardsByValue(Card[] fiveCardsByValue) {
        this.fiveCardsByValue = fiveCardsByValue;
    }

    public Card[] getPreflopCards() {
        return preflopCards;
    }

    public void setPreflopCards(Card[] preflopCards) {
        this.preflopCards = preflopCards;
    }

    public Card[] getBoardCards() {
        return boardCards;
    }

    public void setBoardCards(Card[] boardCards) {
        this.boardCards = boardCards;
    }

    public int compareTo(HandType otherHandType) {
        if (type.getRank() < otherHandType.getType().getRank()) {
            return 1;
        } else if (type.getRank() > otherHandType.getType().getRank()) {
            return -1;
        } else {
            for (int i = 0; i < 5; i++) {
                if (fiveCardsByValue[i].rank().value > otherHandType.getFiveCardsByValue()[i].rank().value) {
                    return 1;
                } else if (fiveCardsByValue[i].rank().value < otherHandType.getFiveCardsByValue()[i].rank().value) {
                    return -1;
                }
            }
            return 0;
        }
    }

    public String sevenCardsToString() {
        String str = "";
        List<Card> sevenCards = new ArrayList<Card>();
        sevenCards.addAll(Arrays.asList(preflopCards));
        sevenCards.addAll(Arrays.asList(boardCards));
        for (Card card : sevenCards) {
            str += ", " + card;
        }
        return str.substring(2);
    }

    public String toString() {
        return type + " " + fiveCardsByValue[0] + ", " + fiveCardsByValue[1] + ", " + fiveCardsByValue[2] + ", " + fiveCardsByValue[3] + ", " + fiveCardsByValue[4];
    }
}
