package poker;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import players.Player;
import exceptions.OutOfCardsException;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 12:45:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandSetting {
	//The setting of game and players
    private GameSetting gameSetting;
    //A shuffle cards
    private CardDeck cardDeck = new CardDeck();
    //an iterator over player which show a turn
    private TurnIterator turnIterator;
    
    private Map<Player, PreflopCards> playerPocketCards;
    private Map<Player, Double> playerBetInFronts;
    //Shared card in the board
    private BoardCards boardCards;
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

    public BoardCards getBoard() {
        return boardCards;
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
        boardCards = new BoardCards();
        boardCards.cards[0] = cardDeck.nextCard();
        boardCards.cards[1] = cardDeck.nextCard();
        boardCards.cards[2] = cardDeck.nextCard();
    }

    public void dealTurn() throws OutOfCardsException {
        boardCards.cards[3] = cardDeck.nextCard();
    }

    public void dealRiver() throws OutOfCardsException {
        boardCards.cards[4] = cardDeck.nextCard();
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
        if (stack == 0)
        	stack = 1;
    }

    public ShowDown showDown() {
        //TODO: handle all in requiring stack separation and over stack bet error
        Map<HandType, List<Player>> playerHandTypes = new TreeMap<HandType, List<Player>>();
        for (Player player : playerPocketCards.keySet()) {
            HandTypeFinder handTypeFinder = new HandTypeFinder(playerPocketCards.get(player), boardCards);
            HandType handType = handTypeFinder.findHandType();
            List<Player> playersWithHand = playerHandTypes.get(handType);
            if (playersWithHand == null){
            	playersWithHand = new LinkedList<Player>();
            	playerHandTypes.put(handType, playersWithHand);
            }
            playersWithHand.add(player);
        }

        List<ShowDownElement> showDownElements = new LinkedList<ShowDownElement>();
        int index = 1;
        for (Entry<HandType, List<Player>> aHandWithPlayers: playerHandTypes.entrySet()){
        	double playerWinningStack = 0;
        	if (index == playerHandTypes.size()) //these player have best hands of this game
        		playerWinningStack = stack / aHandWithPlayers.getValue().size();
        	for (Player player: aHandWithPlayers.getValue()){
        		showDownElements.add(new ShowDownElement(player, aHandWithPlayers.getKey(), playerWinningStack));
        	}
        	++index;
        }
        
        Collections.reverse(showDownElements);

        return new ShowDown(showDownElements);
    }
}
