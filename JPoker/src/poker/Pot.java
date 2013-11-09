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
    private List<Player> attachedPlayers = new ArrayList<Player>();
    private LinkedList<Pot> sidePots = new LinkedList<Pot>();

    public void increase(double addition) {
        value += addition;
    }

    public double getValue() {
        return value;
    }

    public LinkedList<Pot> getSidePots() {
        return sidePots;
    }

    public void clearPlayers() {
        attachedPlayers = new ArrayList<Player>();
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
        List<Player> playersInvolved = new ArrayList<Player>();
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
}
