package poker;

import players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class TurnIterator {
    private List<Player> playersWithStack = new ArrayList<Player>();
    private int currentIndex;
    private List<Integer> foldOrAllInPlayerIndices = new ArrayList<Integer>();

    public TurnIterator(GameSetting gameSetting, int startingSmallBlindIndex) {
        int index = 0;
        for (Player player : gameSetting.getPlayers()) {
            if (gameSetting.getStackSize(player) == 0)
                foldOrAllInPlayerIndices.add(index);
            playersWithStack.add(player);
            index++;
        }
        currentIndex = startingSmallBlindIndex;
        passFoldedPlayers();
    }

    public Player current() {
        return playersWithStack.get(currentIndex);
    }

    public Player removeCurrent() {
        foldOrAllInPlayerIndices.add(currentIndex);
        return playersWithStack.get(currentIndex);
    }

    public void moveTurn() {
        if (getRemainingCount() > 0) {
            currentIndex = (currentIndex + 1) % playersWithStack.size();
            passFoldedPlayers();
        }
    }

    private void passFoldedPlayers() {
        while (foldOrAllInPlayerIndices.contains(currentIndex)) {
            currentIndex = (currentIndex + 1) % playersWithStack.size();
        }
    }

    public int getRemainingCount() {
        return playersWithStack.size() - foldOrAllInPlayerIndices.size();
    }
}
