package players;

import poker.*;

import java.util.List;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class CheckingPlayer extends Player {
    private double latestBet;
    private HandInfo handInfo;

    public CheckingPlayer(String id) {
        super(id);
    }

    public void newHand(HandInfo handInfo) {
        this.handInfo = handInfo;
    }

    @Override
    public void firstCardIs(Card card) {
    }

    @Override
    public void secondCardIs(Card card) {
        latestBet = handInfo.getGameInfo().getBigBlind();
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        latestBet = 0;
    }

    public void turnIs(Card card) {
        latestBet = 500;
    }

    public void riverIs(Card card) {
        latestBet = 2000;
    }

    public void handle(Action action) {
    }

    public double giveYourBet() {
        return latestBet();
    }

    private double latestBet() {
        Double stackSize = handInfo.getStackSize(this, true);
        return stackSize > latestBet ? latestBet : stackSize;
    }

    @Override
    public boolean decideShowOrMuck(HandType handType) {
        return true;
    }

    public void gameEnds() {

    }

    @Override
    public void cardsShown(Player player, HandType handType) {
    }

    @Override
    public void cardsMucked(Player player) {
    }

    @Override
    public void potWon(List<Player> potWinners, double eachValue) {
    }
}
