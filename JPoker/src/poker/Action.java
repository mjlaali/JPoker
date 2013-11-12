package poker;

import exceptions.InvalidBetException;
import players.Player;

/**
 * User: Sina
 * Date: 11/8/13
 */
public class Action {
    public static byte FOLD = 1;
    public static byte CHECK = 2;
    public static byte CALL = 3;
    public static byte RAISE = 4;
    public static byte BLIND = 5;

    protected Player player;
    protected double bet;
    protected Action previousSelfAction;
    protected Action previousBoardRaise;
    protected byte type;
    protected boolean allInAction;

    public Action(double bet, Action previousSelfAction, Action previousBoardRaise, Player player, Double stackSize) {
        this.player = player;
        this.bet = bet;
        this.previousBoardRaise = previousBoardRaise;
        this.previousSelfAction = previousSelfAction;
        double stack = (previousSelfAction == null) ? stackSize : previousSelfAction.getBet() + stackSize;
        if (bet > stack || (bet < stack) && (previousBoardRaise != null && bet < previousBoardRaise.getBet())) {
            throw new InvalidBetException(player, bet);
        }
        allInAction = (bet == stack);
        if (bet < 0) {
            type = FOLD;
            this.bet = previousSelfAction == null? 0 : previousSelfAction.getBet();
        } else if (bet == 0) {
            type = CHECK;
        } else if (previousBoardRaise == null || bet > previousBoardRaise.getBet()) {
            type = RAISE;
        } else {
            type = CALL;
        }
    }

    protected Action() {
    }

    public Player getPlayer() {
        return player;
    }

    /*
    Returns the total amount in this betting round in front of the acting player
     */
    public double getBet() {
        return bet;
    }

    /*
    Returns the delta between previous and current bet by this player in the same betting round. If this is the player's
    first action in this betting round, returns the total amount of bet which will be equal to getBet()
     */
    public double getBetDelta() {
        if (previousSelfAction == null) {
            return bet;
        } else {
            return bet - previousSelfAction.getBet();
        }
    }

    public boolean isFold() {
        return type == FOLD;
    }

    public boolean isCheck() {
        return type == CHECK;
    }

    public boolean isCall() {
        return type == CALL;
    }

    public boolean isRaise() {
        return type == RAISE;
    }

    public boolean isBet() {
        return isRaise() && (previousBoardRaise == null || previousBoardRaise.isCheck());
    }

    public boolean isReraise() {
        return isRaise() && previousBoardRaise != null && previousBoardRaise.isRaise();
    }

    public boolean isAllIn() {
        return allInAction;
    }

    public boolean isBlind() {
        return type == BLIND;
    }

    /*
    Returns the previous Action object associated to the same player in this betting round
     */
    public Action getPreviousSelfAction() {
        return previousSelfAction;
    }

    /*
    Returns the previous raise (including check by the starting player) action in this betting round (by any player)
     */
    public Action getPreviousBoardRaise() {
        return previousBoardRaise;
    }

    @Override
    public String toString() {
        return  isBlind() ? "Blind" :
                (isAllIn() ? "AllIn" :
                (isReraise() ? "ReRaise" :
                (isBet() ? "Bet" :
                (isRaise() ? "Raise" :
                (isCall() ? "Call" : "Check")))));
    }
}
