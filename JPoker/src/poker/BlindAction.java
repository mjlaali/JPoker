package poker;

import players.Player;

/**
 * User: Sina
 * Date: 11/10/13
 */
public class BlindAction extends Action {
    public BlindAction(Player player, double bet) {
        this.player = player;
        this.bet = bet;
        this.previousBoardRaise = null;
        this.previousSelfAction = null;
        this.type = BLIND;
    }
}
