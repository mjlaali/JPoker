package players;

import poker.*;

import java.util.List;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public class CheckingPlayer extends Player {
    private double latestBet;

    public CheckingPlayer(String id, List<PlayerObserver> playerObservers) {
        super(id, playerObservers);
    }

    public void newHand(GameSetting gameSetting) {
    }

    public void preflopIs(Card card1, Card card2) {
        latestBet = 0;
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        latestBet = 4500;
    }

    public void turnIs(Card card) {
        latestBet = 1000;
    }

    public void riverIs(Card card) {
        latestBet = 0;
    }

    public void handle(Action action) {
    }

    public double giveYourBet() {
        return latestBet;
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
    public void potWon(Iterable<Player> potWinners, double eachValue) {
    }
}
