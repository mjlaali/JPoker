package poker;

import players.Player;
import players.PlayerObserver;

import java.util.*;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class GameSetting implements GameInfo {
    private double smallBlind;
    private double bigBlind;
    private List<Player> players = new ArrayList<Player>();
    private Map<Player, List<PlayerObserver>> playerObservers = new HashMap<Player, List<PlayerObserver>>();
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

    public List<PlayerObserver> getObservers(Player player) {
        return playerObservers.get(player);
    }

    public void addPlayerStack(Player player, double stack, List<PlayerObserver> observers) {
        players.add(player);
        playerStacks.put(player, stack);
        playerObservers.put(player, observers);
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

    public Double getStackSize(Player player) {
        return playerStacks.get(player);
    }

    public double deductStack(Player player, double amount) {
        return increaseStack(player, -amount);
    }

    public double increaseStack(Player player, double amount) {
        double stack = playerStacks.get(player) + amount;
        playerStacks.put(player, stack);
        return stack;
    }
}
