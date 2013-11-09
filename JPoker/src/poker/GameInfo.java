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
    public List<Player> getPlayersWithNonZeroStack();
    public int getIndex(Player player);
    public Double getStackSize(Player player);
}
