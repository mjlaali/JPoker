package poker;

import exceptions.HandTypeFoundException;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 1:49:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandTypeFinder {
    private Card[] fiveCardsByValue = new Card[5];
    private PreflopCards preflopCards;
    private BoardCards board;
    private List<Card> sortedSevenCards;
    private Map<Card.Suit, List<Card>> cardsForEachColor;

    public HandTypeFinder(PreflopCards preflopCards, BoardCards board) {
        this.preflopCards = preflopCards;
        this.board = board;
        sortedSevenCards = sortByNumber(preflopCards, board);
        cardsForEachColor = categorizeByColor(sortedSevenCards);
    }

    public HandType findHandType() {
        try {
            tryStraightFlush();
            tryFourOfAKind();
            tryFullHouse();
            tryFlush();
            tryStraight();
            tryThreeOfAKind();
            tryTwoPair();
            tryPair();
            return tryHighCard();
        } catch (HandTypeFoundException e) {
            return e.getHandType();
        }
    }

    private HandType tryHighCard() throws HandTypeFoundException {
        fillFiveCardsFrom(sortedSevenCards, 0, 5, 0);
        return new HandType(PokerHandType.HIGH_CARD, fiveCardsByValue, preflopCards.asArray(), board.cards);
    }

    private void tryPair() throws HandTypeFoundException {
        int pairStartingIndex = getNOfAKindIndex(sortedSevenCards, 2);
        if (pairStartingIndex != -1) {
            fillFiveCardsFrom(sortedSevenCards, pairStartingIndex, 2, 0);
            for (int i = 0; i < 3; i++) {
                int selectionIndex = (pairStartingIndex > i) ? i : i + 2;
                fiveCardsByValue[i + 2] = sortedSevenCards.get(selectionIndex);
            }
            HandType handType = new HandType(PokerHandType.PAIR, fiveCardsByValue, preflopCards.asArray(), board.cards);
            throw new HandTypeFoundException(handType);
        }
    }

    private void tryTwoPair() throws HandTypeFoundException {
        int firstPairStartingIndex = getNOfAKindIndex(sortedSevenCards, 2);
        if (firstPairStartingIndex != -1) {
            List<Card> otherCards = new ArrayList<Card>();
            for (int i = 0; i < firstPairStartingIndex; i++) {
                otherCards.add(sortedSevenCards.get(i));
            }
            for (int i = firstPairStartingIndex + 2; i < sortedSevenCards.size(); i++) {
                otherCards.add(sortedSevenCards.get(i));
            }
            int secondPairStartingIndex = getNOfAKindIndex(otherCards, 2);
            if (secondPairStartingIndex != -1) {
                fillFiveCardsFrom(sortedSevenCards, firstPairStartingIndex, 2, 0);
                fillFiveCardsFrom(otherCards, secondPairStartingIndex, 2, 2);
                if (firstPairStartingIndex == 0) {
                    if (secondPairStartingIndex == 2) {
                        fiveCardsByValue[4] = sortedSevenCards.get(4);
                    } else {
                        fiveCardsByValue[4] = sortedSevenCards.get(2);
                    }
                } else {
                    fiveCardsByValue[4] = sortedSevenCards.get(0);
                }
                HandType handType = new HandType(PokerHandType.TWO_PAIR, fiveCardsByValue, preflopCards.asArray(), board.cards);
                throw new HandTypeFoundException(handType);
            }
        }
    }

    private void tryThreeOfAKind() throws HandTypeFoundException {
        int tripsStartingIndex = getNOfAKindIndex(sortedSevenCards, 3);
        if (tripsStartingIndex != -1) {
            fillFiveCardsFrom(sortedSevenCards, tripsStartingIndex, 3, 0);
            if (tripsStartingIndex == 0) {
                fiveCardsByValue[3] = sortedSevenCards.get(3);
                fiveCardsByValue[4] = sortedSevenCards.get(4);
            } else if (tripsStartingIndex == 1) {
                fiveCardsByValue[3] = sortedSevenCards.get(0);
                fiveCardsByValue[4] = sortedSevenCards.get(4);
            } else {
                fiveCardsByValue[3] = sortedSevenCards.get(0);
                fiveCardsByValue[4] = sortedSevenCards.get(1);
            }
            HandType handType = new HandType(PokerHandType.THREE_OF_A_KIND, fiveCardsByValue, preflopCards.asArray(), board.cards);
            throw new HandTypeFoundException(handType);
        }
    }

    private void tryStraight() throws HandTypeFoundException {
        int straightStartingIndex = fillFiveCardsIfStraight(sortedSevenCards);
        if (straightStartingIndex != -1) {
            HandType handType = new HandType(PokerHandType.STRAIGHT, fiveCardsByValue, preflopCards.asArray(), board.cards);
            throw new HandTypeFoundException(handType);
        }
    }

    private void tryFlush() throws HandTypeFoundException {
        for (Card.Suit suit : cardsForEachColor.keySet()) {
            List<Card> sameColorCards = cardsForEachColor.get(suit);
            if (sameColorCards.size() >= 5) {
                fillFiveCardsFrom(sameColorCards, 0, 5, 0);
                HandType handType = new HandType(PokerHandType.FLUSH, fiveCardsByValue, preflopCards.asArray(), board.cards);
                throw new HandTypeFoundException(handType);
            }
        }
    }

    private void tryFullHouse() throws HandTypeFoundException {
        int tripsStartingIndex = getNOfAKindIndex(sortedSevenCards, 3);
        if (tripsStartingIndex != -1) {
            List<Card> otherCards = new ArrayList<Card>();
            for (int i = 0; i < tripsStartingIndex; i++) {
                otherCards.add(sortedSevenCards.get(i));
            }
            for (int i = tripsStartingIndex + 3; i < sortedSevenCards.size(); i++) {
                otherCards.add(sortedSevenCards.get(i));
            }
            int pairStartingIndex = getNOfAKindIndex(otherCards, 2);
            if (pairStartingIndex != -1) {
                fillFiveCardsFrom(sortedSevenCards, tripsStartingIndex, 3, 0);
                fillFiveCardsFrom(otherCards, pairStartingIndex, 2, 3);
                HandType handType = new HandType(PokerHandType.FULL_HOUSE, fiveCardsByValue, preflopCards.asArray(), board.cards);
                throw new HandTypeFoundException(handType);
            }
        }
    }

    private void tryFourOfAKind() throws HandTypeFoundException {
        int fokStartingIndex = getNOfAKindIndex(sortedSevenCards, 4);
        if (fokStartingIndex != -1) {
            fillFiveCardsFrom(sortedSevenCards, fokStartingIndex, 4, 0);
            fiveCardsByValue[4] = fokStartingIndex == 0 ? sortedSevenCards.get(4) : sortedSevenCards.get(0);
            HandType handType = new HandType(PokerHandType.FOUR_OF_A_KIND, fiveCardsByValue, preflopCards.asArray(), board.cards);
            throw new HandTypeFoundException(handType);
        }
    }

    private void tryStraightFlush() throws HandTypeFoundException {
        for (Card.Suit suit : cardsForEachColor.keySet()) {
            List<Card> sameColorCards = cardsForEachColor.get(suit);
            if (sameColorCards.size() >= 5) {
                int straightStartingIndex = fillFiveCardsIfStraight(sameColorCards);
                if (straightStartingIndex != -1) {
                    HandType handType = new HandType(PokerHandType.STRAIGHT_FLUSH, fiveCardsByValue, preflopCards.asArray(), board.cards);
                    throw new HandTypeFoundException(handType);
                }
            }
        }
    }

    private int fillFiveCardsIfStraight(List<Card> sortedCards) {
        LinkedList<Card> tempSortedCards = new LinkedList<Card>(sortedCards);
        if (tempSortedCards.get(0).rank() == Card.Rank.ACE) {
            tempSortedCards.addLast(tempSortedCards.get(0));
        }
        int straightStartingIndex = 0;
        int straightCount = 0;
        Card previousCard = null;
        for (int index = 0; index < tempSortedCards.size(); index++) {
            Card card = tempSortedCards.get(index);
            if (previousCard == null || (((card.rank().value - 1) % 13) + 2) == previousCard.rank().value) {
                straightCount++;
                if (straightCount == 5) {
                    fillFiveCardsFrom(tempSortedCards, straightStartingIndex, 5, 0);
                    return straightStartingIndex;
                }
            } else if (card.rank() == previousCard.rank()) {
                tempSortedCards.remove(index--);
                continue;
            } else {
                straightStartingIndex = index;
                straightCount = 1;
            }
            previousCard = card;
        }
        return -1;
    }

    private void fillFiveCardsFrom(List<Card> sourceCards, int fromStartingIndex, int count, int toStartingIndex) {
        for (int i = 0; i < count; i++) {
            fiveCardsByValue[toStartingIndex + i] = sourceCards.get(fromStartingIndex + i);
        }
    }

    private int getNOfAKindIndex(List<Card> sortedCards, int n) {
        int count = 0;
        int fokStartingIndex = 0;
        Card previousCard = null;
        for (int index = 0; index < sortedCards.size(); index++) {
            Card card = sortedCards.get(index);
            if (previousCard == null || card.rank().value == previousCard.rank().value) {
                count++;
                if (count == n) {
                    return fokStartingIndex;
                }
            } else {
                count = 1;
                fokStartingIndex = index;
            }
            previousCard = card;
        }
        return -1;
    }

    private Map<Card.Suit, List<Card>> categorizeByColor(List<Card> sortedSevenCards) {
        Map<Card.Suit, List<Card>> cardsForEachColor = new HashMap<Card.Suit, List<Card>>();
        for (Card card : sortedSevenCards) {
            List<Card> cards = cardsForEachColor.get(card.suit());
            if (cards == null) {
                cards = new ArrayList<Card>();
                cardsForEachColor.put(card.suit(), cards);
            }
            cards.add(card);
        }
        return cardsForEachColor;
    }

    private List<Card> sortByNumber(PreflopCards preflopCards, BoardCards board) {
        List<Card> sevenCards = new ArrayList<Card>();
        sevenCards.add(preflopCards.getCard1());
        sevenCards.add(preflopCards.getCard2());
        sevenCards.addAll(Arrays.asList(board.cards));
        sevenCards = sortByNumber(sevenCards);
        return sevenCards;
    }

    private List<Card> sortByNumber(List<Card> sevenCards) {
        List<Card> sortedCards = new ArrayList<Card>();
        for (Card card : sevenCards) {
            int index;
            for (index = 0; index < sortedCards.size(); index++) {
                if (card.rank().value > sortedCards.get(index).rank().value) {
                    break;
                }
            }
            sortedCards.add(index, card);
        }
        return sortedCards;
    }
}
