package exceptions;

import players.Player;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class EarlyFinishedHandException extends Throwable {
    private Player winner;

    public EarlyFinishedHandException(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }
}
