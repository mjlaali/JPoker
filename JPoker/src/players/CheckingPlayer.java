package players;

import poker.Card;
import poker.ShowDown;
import poker.GameSetting;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 11:31:28 PM
 * To change this template use File | Settings | File Templates.
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
        System.out.println("CheckingPlayer.flopIs");
        latestBet = 1000;
    }

    public void turnIs(Card card) {
        latestBet = 0;
    }

    public void riverIs(Card card) {
        latestBet = 0;
    }

    public double giveYourBet() {
        return latestBet;
    }

    public void playerFolded(Player foldingPlayer) {
    }

    public void playerBet(Player bettingPlayer, double bet) {
        latestBet = bet;
    }

    public void showDown(ShowDown showDown) {
    }

    public void gameEnds() {

    }
}
