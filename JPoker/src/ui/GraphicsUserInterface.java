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
    private Map<Player, PlayerView> playerViewableInfos = new HashMap<Player, PlayerView>();
    private ImagePanel boardPanel;
    private String backOfCardPath;

    public GraphicsUserInterface() throws HeadlessException {
        super("Poker AI");
        init();
    }

    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(UICoordinates.boardWidth, UICoordinates.boardHeight);
        setResizable(false);
        boardPanel = new ImagePanel("images/Board.jpg");
        boardPanel.setLayout(null);
        getContentPane().add(boardPanel);
    }

    public void newHand(GameSetting gameSetting) {
        int rand = new Random().nextInt(6) + 1;
        backOfCardPath = "images/Back of Card " + rand + ".jpg";
        List<Player> players = gameSetting.getPlayers();
        if (UICoordinates.playerX1s.length < players.size()) {
            throw new RuntimeException("At most " + UICoordinates.playerX1s.length + " players are supported by this UI");
        }
        boardPanel.removeAll();
        int index = 0;
        while (index < players.size()) {
            Player player = players.get(index);
            PlayerView playerView = new PlayerView(index, player, gameSetting.getStackSize(player));
            boardPanel.add(playerView.card1ButtonPanel, 0);
            boardPanel.add(playerView.card2ButtonPanel, 0);
            playerViewableInfos.put(player, playerView);
            index++;
        }
        repaint();
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        ImagePanel flop1ImagePanel = new ImagePanel(constructPath(flopCard1));
        flop1ImagePanel.setBounds(UICoordinates.flop1X, UICoordinates.flop1Y, UICoordinates.cardWidth, UICoordinates.cardHeight);
        boardPanel.add(flop1ImagePanel);
        flop1ImagePanel.repaint();
        delay(300);
        ImagePanel flop2ImagePanel = new ImagePanel(constructPath(flopCard2));
        flop2ImagePanel.setBounds(UICoordinates.flop2X, UICoordinates.flop2Y, UICoordinates.cardWidth, UICoordinates.cardHeight);
        boardPanel.add(flop2ImagePanel);
        flop2ImagePanel.repaint();
        delay(300);
        ImagePanel flop3ImagePanel = new ImagePanel(constructPath(flopCard3));
        flop3ImagePanel.setBounds(UICoordinates.flop3X, UICoordinates.flop3Y, UICoordinates.cardWidth, UICoordinates.cardHeight);
        boardPanel.add(flop3ImagePanel);
        flop3ImagePanel.repaint();
        delay(300);
    }

    public void turnIs(Card card) {
        ImagePanel turnImagePanel = new ImagePanel(constructPath(card));
        turnImagePanel.setBounds(UICoordinates.turnX, UICoordinates.turnY, UICoordinates.cardWidth, UICoordinates.cardHeight);
        boardPanel.add(turnImagePanel);
        turnImagePanel.repaint();
        delay(300);
    }

    public void riverIs(Card card) {
        ImagePanel riverImagePanel = new ImagePanel(constructPath(card));
        riverImagePanel.setBounds(UICoordinates.riverX, UICoordinates.riverY, UICoordinates.cardWidth, UICoordinates.cardHeight);
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
        PlayerView playerView = playerViewableInfos.get(player);
        boardPanel.remove(playerView.card1ButtonPanel);
        playerView.card1ButtonPanel.setMainImage(constructPath(card));
        playerView.card1ButtonPanel.setOnOverImage(constructPath(card));
        boardPanel.add(playerView.card1ButtonPanel, 0);
        playerView.card1ButtonPanel.repaint();
        delay(300);
    }

    public void secondCardIs(Player player, Card card) {
        PlayerView playerView = playerViewableInfos.get(player);
        boardPanel.remove(playerView.card2ButtonPanel);
        playerView.card2ButtonPanel.setMainImage(constructPath(card));
        playerView.card2ButtonPanel.setOnOverImage(constructPath(card));
        boardPanel.add(playerView.card2ButtonPanel, 0);
        playerView.card2ButtonPanel.repaint();
        delay(300);
    }

    private void delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String constructPath(Card card) {
        return "images/" + card + ".jpg";
    }

    private class PlayerView {
        public ButtonPanel card1ButtonPanel;
        public ButtonPanel card2ButtonPanel;
        public Label nameLabel;
        public Label stackLabel;

        public PlayerView(int index, Player player, Double stackSize) {
            card1ButtonPanel = new ButtonPanel(backOfCardPath, backOfCardPath);
            card1ButtonPanel.setBounds(UICoordinates.playerX1s[index], UICoordinates.playerY1s[index], UICoordinates.cardWidth, UICoordinates.cardHeight);
            card2ButtonPanel = new ButtonPanel(backOfCardPath, backOfCardPath);
            card2ButtonPanel.setBounds(UICoordinates.playerX2s[index], UICoordinates.playerY2s[index], UICoordinates.cardWidth, UICoordinates.cardHeight);
            nameLabel = new Label(player.toString());
            nameLabel.setBounds(UICoordinates.playerNameLabelX[index], UICoordinates.playerNameLabelY[index], UICoordinates.playerLabelWidth, UICoordinates.playerLabelHeight);
            nameLabel.setBackground(new Color(1, 8, 13));
            nameLabel.setForeground(Color.WHITE);
            boardPanel.add(nameLabel);
            stackLabel = new Label("" + (stackSize.intValue()));
            stackLabel.setBounds(UICoordinates.playerStackLabelX[index], UICoordinates.playerStackLabelY[index], UICoordinates.playerLabelWidth, UICoordinates.playerLabelHeight);
            stackLabel.setBackground(new Color(1, 8, 13));
            stackLabel.setForeground(Color.WHITE);
            boardPanel.add(stackLabel);
        }
    }
}
