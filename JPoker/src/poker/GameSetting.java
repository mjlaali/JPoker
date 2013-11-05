package poker;

import players.Player;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 6:23:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameSetting {
    private double smallBlind;
    private double bigBlind;
    private List<Player> players = new ArrayList<Player>();
    private Map<Player, Double> playerStacks = new HashMap<Player, Double>();

    public GameSetting(double smallBlind, double bigBlind) {
        this.bigBlind = bigBlind;
        this.smallBlind = smallBlind;
    }

    public double getSmallBlind() {
        return smallBlind;
    }

    public double getBigBlind() {
        return bigBlind;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayerStack(Player player, double stack) {
        players.add(player);
        playerStacks.put(player, stack);
    }

    public double deductStack(Player player, double amount) {
        double stack = playerStacks.get(player) - amount;
        playerStacks.put(player, stack);
        return stack;
    }

    public Double getStackSize(Player player) {
        return playerStacks.get(player);
    }

    public int getIndex(Player player) {
        int index = 0;
        for (Player p : players) {
            if (p == player)
                return index;
            index++;
        }
        return -1;
    }
}
