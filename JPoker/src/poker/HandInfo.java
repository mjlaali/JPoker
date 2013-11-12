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
        Returns a list of getStartingPlayers who started the hand (including the ones that may have folded by now) starting
        from small blind
     */
    public List<Player> getStartingPlayers();

    /*
        Returns a list of currently active players (who haven't fold and haven't gone all in) starting from small blind
     */
    public List<Player> getActivePlayers();

    public Player getSmallBlindPlayer();

    public Player getBigBlindPlayer();

    public List<Pot> getAllPots();

    public Player getDealer();
}
