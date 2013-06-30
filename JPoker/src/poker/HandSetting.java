package poker;

import exceptions.OutOfCardsException;
import players.Player;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 12:45:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandSetting {
    private GameSetting gameSetting;
    private CardDeck cardDeck = new CardDeck();
    private TurnIterator turnIterator;
    private Map<Player, PreflopCards> playerPocketCards;
    private Map<Player, Double> playerBetInFronts;
    private Board board;
    private double stack = 0;

    public HandSetting(GameSetting gameSetting, int smallBlindIndex) {
        this.gameSetting = gameSetting;
        turnIterator = new TurnIterator(gameSetting.getPlayers(), smallBlindIndex);
        clearPlayerBetInFronts();
    }

    public double getStack() {
        return stack;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return turnIterator.current();
    }

    public void foldCurrent() {
        Player player = turnIterator.foldCurrent();
        playerPocketCards.remove(player);
    }

    public void dealPreflop() throws OutOfCardsException {
        playerPocketCards = new HashMap<Player, PreflopCards>();
        for (Player player : gameSetting.getPlayers()) {
            Card card = cardDeck.nextCard();
            player.firstCardIs(card);
            playerPocketCards.put(player, new PreflopCards(card));
        }
        for (Player player : gameSetting.getPlayers()) {
            Card card = cardDeck.nextCard();
            player.secondCardIs(card);
            playerPocketCards.get(player).setCard2(card);
        }
    }

    public void dealFlop() throws OutOfCardsException {
        board = new Board();
        board.cards[0] = cardDeck.nextCard();
        board.cards[1] = cardDeck.nextCard();
        board.cards[2] = cardDeck.nextCard();
    }

    public void dealTurn() throws OutOfCardsException {
        board.cards[3] = cardDeck.nextCard();
    }

    public void dealRiver() throws OutOfCardsException {
        board.cards[4] = cardDeck.nextCard();
    }

    public int getRemainingCount() {
        return turnIterator.getRemainingCount();
    }

    public Player moveNext() {
        turnIterator.moveTurn();
        return turnIterator.current();
    }

    public double setBetInFront(Player player, double bet) {
        double previousBet = playerBetInFronts.get(player);
        playerBetInFronts.put(player, bet);
        return bet - previousBet;
    }

    private void clearPlayerBetInFronts() {
        playerBetInFronts = new HashMap<Player, Double>();
        for (Player player : gameSetting.getPlayers()) {
            playerBetInFronts.put(player, (double) 0);
        }
    }

    public void collectBets() {
        for (Double betInFront : playerBetInFronts.values()) {
            stack += betInFront;
        }
        clearPlayerBetInFronts();
    }

    public ShowDown showDown() {
        // todo: handle all in requiring stack separation and over stack bet error
        Map<Player, HandType> playerHandTypes = new HashMap<Player, HandType>();
        List<Player> playersOrderedByHand = new ArrayList<Player>();
        for (Player player : playerPocketCards.keySet()) {
            HandTypeFinder handTypeFinder = new HandTypeFinder(playerPocketCards.get(player), board);
            HandType handType = handTypeFinder.findHandType();
            playerHandTypes.put(player, handType);
            int index = 0;
            boolean correctIndexFound = false;
            while (index < playersOrderedByHand.size() && !correctIndexFound) {
                HandType currentHandType = playerHandTypes.get(playersOrderedByHand.get(index));
                if (handType.compareTo(currentHandType) > 0) {
                    correctIndexFound = true;
                } else {
                    index++;
                }
            }
            playersOrderedByHand.add(index, player);
        }
        List<ShowDownElement> showDownElements = new ArrayList<ShowDownElement>();
        Iterator<Player> iterator = playersOrderedByHand.iterator();
        Player winningPlayer = iterator.next();
        showDownElements.add(new ShowDownElement(winningPlayer, playerHandTypes.get(winningPlayer), stack));
        while (iterator.hasNext()) {
            Player player = iterator.next();
            showDownElements.add(new ShowDownElement(player, playerHandTypes.get(player), 0));
        }
        return new ShowDown(showDownElements);
    }
}
