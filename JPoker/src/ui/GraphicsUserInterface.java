package ui;

import players.Player;
import players.PlayerObserver;
import poker.*;
import poker.Action;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sina
 * Date: Mar 4, 2012
 */
public class GraphicsUserInterface extends JFrame implements UserInterface, PlayerObserver {
    private Map<Player, PlayerPanel> playerPanels = new HashMap<Player, PlayerPanel>();
    private ImagePanel boardPanel;
    private GameInfo currentGame;
    private ActionPanel latestActionPanel;

    public GraphicsUserInterface() throws HeadlessException {
        super("Poker AI");
        init();
    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(UICoords.boardWidth, UICoords.boardHeight);
        setResizable(false);
        boardPanel = new ImagePanel("images/Board.jpg");
        boardPanel.setLayout(null);
        getContentPane().add(boardPanel);
    }

    public void newHand(HandInfo handInfo) {
        this.currentGame = handInfo.getGameInfo();
        List<Player> players = currentGame.getPlayers();
        if (UICoords.playerX1s.length < players.size()) {
            throw new RuntimeException("At most " + UICoords.playerX1s.length + " getStartingPlayers are supported by this UI");
        }
        boardPanel.removeAll();
        int i = 0;
        for (Player player : players) {
            InfoPanel infoPanel = new InfoPanel(player, currentGame);
            boardPanel.add(infoPanel);
            int y = UICoords.playerY1s[i];
            y = (y > getHeight() / 2) ? (y + 80) : y - 35;
            infoPanel.setBounds(UICoords.playerX1s[i] + 5, y, InfoPanel.panelWidth, InfoPanel.panelHeight);

            PlayerPanel playerPanel = new PlayerPanel(player, infoPanel);
            playerPanels.put(player, playerPanel);
            boardPanel.add(playerPanel);
            playerPanel.setBounds(UICoords.playerX1s[i], UICoords.playerY1s[i], PlayerPanel.panelWidth, PlayerPanel.panelHeight);

            i++;
        }
        repaint();
    }

    private String constructPath(Card card) {
        return "images/" + card + ".jpg";
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        hideLatestAction(true);
        ImagePanel flop1ImagePanel = new ImagePanel(constructPath(flopCard1));
        flop1ImagePanel.setBounds(UICoords.flop1X, UICoords.flop1Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop1ImagePanel);
        flop1ImagePanel.repaint();
        delay(200);
        ImagePanel flop2ImagePanel = new ImagePanel(constructPath(flopCard2));
        flop2ImagePanel.setBounds(UICoords.flop2X, UICoords.flop2Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop2ImagePanel);
        flop2ImagePanel.repaint();
        delay(100);
        ImagePanel flop3ImagePanel = new ImagePanel(constructPath(flopCard3));
        flop3ImagePanel.setBounds(UICoords.flop3X, UICoords.flop3Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop3ImagePanel);
        flop3ImagePanel.repaint();
        delay(100);
    }

    public void turnIs(Card card) {
        hideLatestAction(true);
        ImagePanel turnImagePanel = new ImagePanel(constructPath(card));
        turnImagePanel.setBounds(UICoords.turnX, UICoords.turnY, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(turnImagePanel);
        turnImagePanel.repaint();
        delay(200);
    }

    public void riverIs(Card card) {
        hideLatestAction(true);
        ImagePanel riverImagePanel = new ImagePanel(constructPath(card));
        riverImagePanel.setBounds(UICoords.riverX, UICoords.riverY, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(riverImagePanel);
        riverImagePanel.repaint();
        delay(200);
    }

    @Override
    public void handle(Action action) {
        hideLatestAction(false);
        latestActionPanel = new BetActionPanel(action.getBet());
        boardPanel.add(latestActionPanel);
        locateActionPanelBounds(action.getPlayer(), latestActionPanel);
        repaint();
        delay(400);
    }

    private void locateActionPanelBounds(Player player, ActionPanel actionPanel) {
        int index = currentGame.getIndex(player);
        int x = UICoords.playerX1s[index];
        int y = UICoords.playerY1s[index];
        if (index == 0 || index == 1) {
            x = x + PlayerPanel.panelWidth + 13;
            y = y + 25;
        }
        if (index == 2 || index == 3) {
            x = x + 5;
            y = y + PlayerPanel.panelHeight + 11;
        }
        if (index == 4 || index == 5) {
            x = x - ActionPanel.panelWidth - 13;
            y = y + 25;
        }
        if (index == 6 || index == 7) {
            x = x + 5;
            y = y - ActionPanel.panelHeight - 11;
        }

        actionPanel.setBounds(x, y, ActionPanel.panelWidth, ActionPanel.panelHeight);
    }

    private void hideLatestAction(boolean repaint) {
        if (latestActionPanel != null) {
            boardPanel.remove(latestActionPanel);
            if (repaint)
                repaint();
        }
    }

    public void gameEnds() {

    }

    @Override
    public void cardsShown(Player player, HandType handType) {
        playerPanels.get(player).showCards();
    }

    @Override
    public void cardsMucked(Player player) {
    }

    @Override
    public void potWon(Iterable<Player> potWinners, double eachValue) {
        hideLatestAction(true);
        for (Player player : potWinners) {
            ShowDownPanel showDownPanel = new ShowDownPanel(player, eachValue);
            boardPanel.add(showDownPanel);
            locateActionPanelBounds(player, showDownPanel);
            repaint();
            delay(400);
        }
        delay(2000);
    }

    public void firstCardIs(Player player, Card card) {
        PlayerPanel playerPanel = playerPanels.get(player);
        playerPanel.setCard1(card);
        playerPanel.repaint();
        delay(300);
    }

    public void secondCardIs(Player player, Card card) {
        PlayerPanel playerPanel = playerPanels.get(player);
        playerPanel.setCard2(card);
        playerPanel.repaint();
        delay(300);
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
