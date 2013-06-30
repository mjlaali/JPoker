package ui;

import poker.Card;
import poker.ShowDown;
import poker.ShowDownElement;
import poker.GameSetting;

import java.util.List;
import java.io.IOException;

import players.Player;
import processors.Analyzer;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 1:52:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConsoleUI implements UserInterface {
    private boolean keyPressAfterEachHand;
    private boolean printOut;
    private boolean printStats;
    private Analyzer analyzer;

    public ConsoleUI(boolean keyPressAfterEachHand, boolean printHands, boolean printStats, Analyzer analyzer) {
        this.keyPressAfterEachHand = keyPressAfterEachHand;
        this.printOut = printHands;
        this.printStats = printStats;
        this.analyzer = analyzer;
    }

    public void newHand(GameSetting gameSetting) {

    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        if (printOut)
            System.out.print("poker.Board: " + flopCard1 + ", " + flopCard2 + ", " + flopCard3);
    }

    public void turnIs(Card card) {
        if (printOut)
            System.out.print(", " + card);
    }

    public void riverIs(Card card) {
        if (printOut)
            System.out.println(", " + card);
    }

    public double giveYourBet() {
        return 0;
    }

    public void playerFolded(Player foldingPlayer) {

    }

    public void playerBet(Player bettingPlayer, double bet) {

    }

    public void showDown(ShowDown showDown) {
        for (ShowDownElement showDownElement : showDown.getShowDownElements()) {
            Card[] pocketCards = showDownElement.getHandType().getPreflopCards();
            if (printOut)
                System.out.println(showDownElement.getPlayer() + " wins " + "$" + showDownElement.getWinningAmount() + "\tpocket: " + pocketCards[0] + ", " + pocketCards[1] + "\thand: " + showDownElement.getHandType());
        }
        if (keyPressAfterEachHand) {
            try {
                //noinspection ResultOfMethodCallIgnored
                System.in.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void gameEnds() {
        if (analyzer != null) {
            if (printStats)
                System.out.println(analyzer.getStatsAsString());
        }
    }
}
