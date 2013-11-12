package poker;

import players.Player;

import java.util.List;

/**
 * User: Sina
 * Date: 11/9/13
 */
public interface GameInfo {
    public double getSmallBlind();
    public double getBigBlind();
    public List<Player> getPlayers();
    public List<Player> getActivePlayers();
    public int getIndex(Player player);
    /*
        Will not include bets in front of the player in the current hand (for that see HandInfo.getStackSize())
     */
    public Double getStackSize(Player player);
}
