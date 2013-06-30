package poker;

import players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 7:29:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class TurnIterator {
    private List<Player> players;
    private int smallBlindIndex;
    private int currentIndex;
    private List<Integer> foldedPlayerIndices = new ArrayList<Integer>();

    public TurnIterator(List<Player> players, int startingSmallBlindIndex) {
        this.players = players;
        currentIndex = this.smallBlindIndex = startingSmallBlindIndex;
    }

    public Player current() {
        return players.get(currentIndex);
    }

    public Player foldCurrent() {
        foldedPlayerIndices.add(currentIndex);
        return players.get(currentIndex);
    }

    public void moveTurn() {
        currentIndex = (currentIndex + 1) % players.size();
        passFoldedPlayers();
    }

    public void betRoundDone() {
        currentIndex = smallBlindIndex;
        passFoldedPlayers();
    }

    private void passFoldedPlayers() {
        while (foldedPlayerIndices.contains(currentIndex)) {
            currentIndex = (currentIndex + 1) % players.size();
        }
    }

    public int getRemainingCount() {
        return players.size() - foldedPlayerIndices.size();
    }
}
