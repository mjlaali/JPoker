package ui;

import players.Player;
import players.PlayerObserver;
import poker.Card;
import poker.GameSetting;
import poker.ShowDown;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 4, 2012
 * Time: 9:47:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphicsUserInterface extends JFrame implements UserInterface, PlayerObserver {
    private Map<Player, PlayerPanel> playerPanels = new HashMap<Player, PlayerPanel>();
    private ImagePanel boardPanel;
    private String backOfCardPath;

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

    public void newHand(GameSetting gameSetting) {
        int rand = new Random().nextInt(6) + 1;
        backOfCardPath = "images/Back of Card " + rand + ".jpg";
        List<Player> players = gameSetting.getPlayers();
        if (UICoords.playerX1s.length < players.size()) {
            throw new RuntimeException("At most " + UICoords.playerX1s.length + " players are supported by this UI");
        }
        boardPanel.removeAll();
        int i = 0;
        for (Player player : players) {
            PlayerPanel playerPanel = new PlayerPanel(player, gameSetting.getStackSize(player));
            playerPanels.put(player, playerPanel);
            boardPanel.add(playerPanel);
            playerPanel.setBounds(UICoords.playerX1s[i], UICoords.playerY1s[i], PlayerPanel.panelWidth, PlayerPanel.panelHeight);
            i++;
        }
        repaint();
    }

    //todo: remove this
    private String constructPath(Card card) {
        return "images/" + card + ".jpg";
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        ImagePanel flop1ImagePanel = new ImagePanel(constructPath(flopCard1));
        flop1ImagePanel.setBounds(UICoords.flop1X, UICoords.flop1Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop1ImagePanel);
        flop1ImagePanel.repaint();
        delay(300);
        ImagePanel flop2ImagePanel = new ImagePanel(constructPath(flopCard2));
        flop2ImagePanel.setBounds(UICoords.flop2X, UICoords.flop2Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop2ImagePanel);
        flop2ImagePanel.repaint();
        delay(300);
        ImagePanel flop3ImagePanel = new ImagePanel(constructPath(flopCard3));
        flop3ImagePanel.setBounds(UICoords.flop3X, UICoords.flop3Y, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(flop3ImagePanel);
        flop3ImagePanel.repaint();
        delay(300);
    }

    public void turnIs(Card card) {
        ImagePanel turnImagePanel = new ImagePanel(constructPath(card));
        turnImagePanel.setBounds(UICoords.turnX, UICoords.turnY, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(turnImagePanel);
        turnImagePanel.repaint();
        delay(300);
    }

    public void riverIs(Card card) {
        ImagePanel riverImagePanel = new ImagePanel(constructPath(card));
        riverImagePanel.setBounds(UICoords.riverX, UICoords.riverY, UICoords.cardWidth, UICoords.cardHeight);
        boardPanel.add(riverImagePanel);
        riverImagePanel.repaint();
        delay(300);
    }

    public void playerFolded(Player foldingPlayer) {

    }

    public void playerBet(Player bettingPlayer, double bet) {
    }

    public void showDown(ShowDown showDown) {

    }

    public void gameEnds() {

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
