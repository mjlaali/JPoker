package poker;

import exceptions.OutOfCardsException;
import players.Player;

import java.util.*;
import java.util.Map.Entry;

/**
 * User: Sina
 * Date: Mar 1, 2012
 */
public class HandSetting {
	//The setting of current game and players
    private GameSetting gameSetting;
    private int smallBlindIndex;
    //The deck of cards
    private CardDeck cardDeck = new CardDeck();
    private Map<Player, PreflopCards> playerPocketCards;
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
        playerPocketCards = new HashMap<Player, PreflopCards>();
        Iterator<Player> iterator = playerIterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            Card card = cardDeck.nextCard();
            player.firstCardIs(card);
            playerPocketCards.put(player, new PreflopCards(card));
        }
        iterator = playerIterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
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

    public Pot getPot() {
        return pot;
    }

    public int getRemainingInPot() {
        return playerPocketCards.size();
    }

    public Map<Player, PreflopCards> getPlayerPocketCards() {
        return playerPocketCards;
    }

    private Iterator<Player> playerIterator() {
        return new Iterator<Player>() {
            private int index = smallBlindIndex;
            boolean done = false;

            @Override
            public boolean hasNext() {
                return !done;
            }

            @Override
            public Player next() {
                Player player = gameSetting.getPlayers().get(index);
                index = (index + 1) % gameSetting.getPlayers().size();
                if (index == smallBlindIndex) {
                    done = true;
                }
                return player;
            }

            @Override
            public void remove() {
            }
        };
    }
}
