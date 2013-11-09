package poker;

import players.Player;

import java.util.Iterator;

/**
 * User: Sina
 * Date: 11/9/13
 */
public interface HandInfo {
    public GameInfo getGameInfo();

    /*
        Returns an iterator over players who started the hand (including the ones that may have folded by now) starting
        from small blind
     */
    public Iterator<Player> playerIterator();

    /*
        Returns an iterator over players who are in the hand (excluding the ones who folded or went all in) starting
        from small blind
     */
    public Iterator<Player> inPotPlayerIterator();

    public Player getSmallBlindPlayer();

    public Player getBigBlindPlayer();

    public Player getDealer();
}
