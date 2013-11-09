package poker;

import players.Player;

import java.util.List;

/**
 * User: Sina
 * Date: 11/9/13
 */
public interface HandInfo {
    public GameInfo getGameInfo();

    /*
        Returns an list of getStartingPlayers who started the hand (including the ones that may have folded by now) starting
        from small blind
     */
    public List<Player> getStartingPlayers();

    public Player getSmallBlindPlayer();

    public Player getBigBlindPlayer();

    public Player getDealer();
}
