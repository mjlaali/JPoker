package poker;

import players.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Sina
 * Date: 11/8/13
 */
public class Pot {
    private double value = 0;
    private List<Player> attachedPlayers = new ArrayList<>();
    private LinkedList<Pot> sidePots = new LinkedList<>();

    public void increase(double addition) {
        value += addition;
    }

    public double getValue() {
        return value;
    }

    public void clearPlayers() {
        attachedPlayers = new ArrayList<>();
    }

    public void attach(Player player) {
        attachedPlayers.add(player);
    }

    public void add(Pot sidePot) {
        sidePots.addLast(sidePot);
    }

    /*
    Returns those getStartingPlayers who went allin in this pot
     */
    public List<Player> getAttachedPlayers() {
        return attachedPlayers;
    }

    /*
    Returns getStartingPlayers involved in pots sorted based on who should show first (side pot getStartingPlayers show first and then the last
    raiser is the first to show)
     */
    public List<Player> getPlayersInvolved() {
        List<Player> playersInvolved = new ArrayList<>();
        for (Pot sidePot : sidePots) {
            playersInvolved.addAll(0, sidePot.attachedPlayers);
        }
        playersInvolved.addAll(attachedPlayers);
        return playersInvolved;
    }

    public Iterator<Player> playerIterator() {
        List<Player> playersInvolved = getPlayersInvolved();
        return playersInvolved.iterator();
    }

    public List<Pot> getAllPots() {
        List<Pot> allPots = new ArrayList<>(sidePots);
        if (value > 0)
            allPots.add(this);
        return allPots;
    }
}
