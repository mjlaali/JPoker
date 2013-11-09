package exceptions;

import players.Player;

/**
 * User: Sina
 * Date: 11/8/13
 */
public class InvalidBetException extends RuntimeException {
    public InvalidBetException(Player player, double bet) {
        super("Invalid bet of " + bet + " by " + player);
    }
}
