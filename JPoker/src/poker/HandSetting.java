package poker;

import exceptions.OutOfCardsException;
import players.Player;
import players.PlayerObserver;

import java.util.*;

/**
 * User: Sina
 * Date: Mar 1, 2012
 */
public class HandSetting implements HandInfo {
	//The setting of current game and getStartingPlayers
    private GameSetting gameSetting;
    private int smallBlindIndex;
    private int bigBlindIndex;
    private int dealerIndex;
    //The deck of cards
    private CardDeck cardDeck = new CardDeck();
    private LinkedHashMap<Player, PreflopCards> playerPocketCards;
    private List<Player> startingPlayers;
    //Shared card in the board
    private BoardCards boardCards;
    private Pot activePot;

    public HandSetting(GameSetting gameSetting, int smallBlindIndex, int bigBlindIndex, int dealerIndex) {
        this.gameSetting = gameSetting;
        this.smallBlindIndex = smallBlindIndex;
        this.bigBlindIndex = bigBlindIndex;
        this.dealerIndex = dealerIndex;
        startingPlayers = getActivePlayers();
        activePot = new Pot();
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }

    public BoardCards getBoard() {
        return boardCards;
    }

    public void fold(Player player) {
        playerPocketCards.remove(player);
    }

    public void dealPreFlop() throws OutOfCardsException {
        playerPocketCards = new LinkedHashMap<>();
        List<Player> startingPlayers = getStartingPlayers();
        for (Player player : startingPlayers) {
            Card card = cardDeck.nextCard();
            player.firstCardIs(card);
            for (PlayerObserver playerObserver : gameSetting.getObservers(player)) {
                playerObserver.firstCardIs(player, card);
            }
            playerPocketCards.put(player, new PreflopCards(card));
        }
        for (Player player : startingPlayers) {
            Card card = cardDeck.nextCard();
            player.secondCardIs(card);
            for (PlayerObserver playerObserver : gameSetting.getObservers(player)) {
                playerObserver.secondCardIs(player, card);
            }
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

    public Pot getActivePot() {
        return activePot;
    }

    public List<Pot> getAllPots() {
        return activePot.getAllPots();
    }

    public int getRemainingInPot() {
        return playerPocketCards.size();
    }

    public Map<Player, PreflopCards> getPlayerPocketCards() {
        return playerPocketCards;
    }

    @Override
    public GameInfo getGameInfo() {
        return gameSetting;
    }

    @Override
    public List<Player> getActivePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        List<Player> allPlayers = gameSetting.getPlayers();
        List<Player> activePlayers = gameSetting.getActivePlayers();
        players.addAll(allPlayers.subList(smallBlindIndex, allPlayers.size()));
        if (smallBlindIndex > 0)
            players.addAll(allPlayers.subList(0, smallBlindIndex));
        players.retainAll(activePlayers);
        return players;
    }

    @Override
    public List<Player> getStartingPlayers() {
        return startingPlayers;
    }

    @Override
    public Player getSmallBlindPlayer() {
        return gameSetting.getPlayers().get(smallBlindIndex);
    }

    @Override
    public Player getBigBlindPlayer() {
        return gameSetting.getPlayers().get(bigBlindIndex);
    }

    @Override
    public Player getDealer() {
        return gameSetting.getPlayers().get(dealerIndex);
    }
}
