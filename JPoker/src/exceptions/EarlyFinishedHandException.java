package exceptions;

import players.Player;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 10:10:19 PM
 * To change this template use File | Settings | File Templates.
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
