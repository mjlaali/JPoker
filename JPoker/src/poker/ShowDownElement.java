package poker;

import players.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 1:03:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDownElement {
    private Player player;
    private HandType handType;
    private double winningAmount;

    public ShowDownElement(Player player, HandType handType, double winningAmount) {
        this.player = player;
        this.handType = handType;
        this.winningAmount = winningAmount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HandType getHandType() {
        return handType;
    }

    public void setHandType(HandType handType) {
        this.handType = handType;
    }

    public double getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(double winningAmount) {
        this.winningAmount = winningAmount;
    }
}
