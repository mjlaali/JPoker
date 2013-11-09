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
    //The deck of cards
    private CardDeck cardDeck = new CardDeck();
    private LinkedHashMap<Player, PreflopCards> playerPocketCards;
    //Shared card in the board
    private BoardCards boardCards;
    private Pot pot;

    public HandSetting(GameSetting gameSetting, int smallBlindIndex) {
        this.gameSetting = gameSetting;
        this.smallBlindIndex = smallBlindIndex;
        pot = new Pot();
    }

    public int getSmallBlindIndex() {
        return smallBlindIndex;
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
        playerPocketCards = new LinkedHashMap<Player, PreflopCards>();
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

    public Pot getPot() {
        return pot;
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
    public List<Player> getStartingPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        List<Player> gamePlayers = gameSetting.getPlayersWithNonZeroStack();
        players.addAll(gamePlayers.subList(smallBlindIndex, gamePlayers.size()));
        if (smallBlindIndex > 0)
            players.addAll(gamePlayers.subList(0, smallBlindIndex));
        return players;
    }

    @Override
    public Player getSmallBlindPlayer() {
        return gameSetting.getPlayers().get(smallBlindIndex);
    }

    @Override
    public Player getBigBlindPlayer() {
        int index = (smallBlindIndex + 1) % gameSetting.getPlayers().size();
        return gameSetting.getPlayers().get(index);
    }

    @Override
    public Player getDealer() {
        int size = gameSetting.getPlayers().size();
        int index = (smallBlindIndex + size - 1) % size;
        return gameSetting.getPlayers().get(index);
    }
}
