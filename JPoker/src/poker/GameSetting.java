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
    private List<Player> players = new ArrayList<>();
    private Map<Player, List<PlayerObserver>> playerObservers = new HashMap<>();
    private Map<Player, Double> playerStacks = new HashMap<>();

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

    @Override
    public List<Player> getPlayersWithNonZeroStack() {
        List<Player> players = new ArrayList<>();
        for (Player player : getPlayers()) {
            if (getStackSize(player) > 0) {
                players.add(player);
            }
        }
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


    public int getActiveRight(int index) {
        int size = players.size();
        int rightIndex = index;
        Player playerToRight;
        do {
            rightIndex = (rightIndex + size - 1) % size;
            playerToRight = players.get(rightIndex);
        } while (getStackSize(playerToRight) == 0);
        return rightIndex;
    }

    public int getActiveLeft(int index) {
        int size = players.size();
        int leftIndex = index;
        Player playerToLeft;
        do {
            leftIndex = (leftIndex + 1) % size;
            playerToLeft = players.get(leftIndex);
        } while (getStackSize(playerToLeft) == 0);
        return leftIndex;
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
