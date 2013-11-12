package ui;

import players.Player;
import players.PlayerObserver;
import poker.Action;
import poker.*;
import ui.panels.*;
import ui.shapes.Position;
import ui.shapes.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sina
 * Date: Mar 4, 2012
 */
public class GraphicsUserInterface extends JFrame implements UserInterface, PlayerObserver, KeyListener {
    private Map<Player, PlayerPanel> playerPanels = new HashMap<>();
    private ImagePanel boardPanel;
    private GameInfo currentGame;
    private HandInfo currentHand;
    private Map<Player, ActionPanel> actionPanels = new HashMap<>();
    private List<PotPanel> potPanels = new ArrayList<>();
    private ActionPanel latestActionPanel;
    private TextPanel pausePanel;
    private int nextPot = 0;
    private boolean pause = false;

    public GraphicsUserInterface() throws HeadlessException {
        super("Poker AI");
        init();
    }

    private void init() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(UICoords.boardWidth, UICoords.boardHeight);
        setResizable(false);
        addKeyListener(this);
        boardPanel = new ImagePanel("images/Board.jpg");
        boardPanel.setLayout(null);
        getContentPane().add(boardPanel);
    }

    public void newHand(HandInfo handInfo) {
        this.currentGame = handInfo.getGameInfo();
        this.currentHand = handInfo;
        nextPot = 0;
        List<Player> players = currentGame.getPlayers();
        if (UICoords.playerPositions.length < players.size()) {
            throw new RuntimeException("At most " + UICoords.playerPositions.length + " getStartingPlayers are supported by this UI");
        }
        boardPanel.removeAll();
        pausePanel = new TextPanel("  ENTER > PAUSE", new Font("Comic Sans MS", Font.BOLD, 12), new Color(191, 186, 181));
        pausePanel.setBounds(boardPanel.getWidth() - 135, boardPanel.getHeight() - 30, 135, 30);
        boardPanel.add(pausePanel);
        addDealerButton();
        int i = 0;
        for (Player player : players) {
            checkPause();
            InfoPanel infoPanel = new InfoPanel(player, currentGame);
            boardPanel.add(infoPanel);
            Position p = UICoords.playerPositions[i];
            int y = (p.y > getHeight() / 2) ? (p.y + 80) : p.y - 35;
            infoPanel.setBounds(p.x + 5, y, InfoPanel.panelWidth, InfoPanel.panelHeight);

            PlayerPanel playerPanel = new PlayerPanel(player, infoPanel);
            playerPanels.put(player, playerPanel);
            boardPanel.add(playerPanel);
            playerPanel.setBounds(p.x, p.y, PlayerPanel.panelWidth, PlayerPanel.panelHeight);

            i++;
        }
        repaint();
        checkPause();
    }

    private void addDealerButton() {
        ImagePanel dealerButtonPanel = new ImagePanel("images/DealerButton.jpg");
        Rectangle bounds = locateDealerButton(currentHand.getDealer());
        dealerButtonPanel.setBounds(bounds.topLeft.x, bounds.topLeft.y, bounds.width, bounds.height);
        boardPanel.add(dealerButtonPanel);
        dealerButtonPanel.repaint();
    }

    private Rectangle locateDealerButton(Player dealer) {
        Rectangle apBounds = locateActionPanelBounds(dealer);
        Position center;
        int index = currentGame.getIndex(dealer);
        if (index == 0) {
            center = apBounds.topLeft(-10, 18);
        } else if (index == 1) {
            center = apBounds.bottomLeft(-10, 18);
        } else if (index == 2 || index == 3) {
            center = apBounds.topRight(17, -15);
        } else if (index == 4) {
            center = apBounds.bottomRight(-10, 18);
        } else if (index == 5) {
            center = apBounds.topRight(-10, 18);
        } else /* if (index == 6 || index == 7) */ {
            center = apBounds.bottomLeft(17, -15);
        }
        Position topLeft = center.distance(-8, -8);
        return new Rectangle(topLeft, 16, 16);

    }

    private String constructPath(Card card) {
        return "images/" + card + ".jpg";
    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
        buildPot(true);
        ImagePanel flop1ImagePanel = new ImagePanel(constructPath(flopCard1));
        flop1ImagePanel.setBounds(UICoords.flop1X, UICoords.flop1Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop1ImagePanel);
        flop1ImagePanel.repaint();
        delay(200, true);
        ImagePanel flop2ImagePanel = new ImagePanel(constructPath(flopCard2));
        flop2ImagePanel.setBounds(UICoords.flop2X, UICoords.flop2Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop2ImagePanel);
        flop2ImagePanel.repaint();
        delay(100, true);
        ImagePanel flop3ImagePanel = new ImagePanel(constructPath(flopCard3));
        flop3ImagePanel.setBounds(UICoords.flop3X, UICoords.flop3Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop3ImagePanel);
        flop3ImagePanel.repaint();
        delay(100, true);
    }

    public void turnIs(Card card) {
        buildPot(true);
        ImagePanel turnImagePanel = new ImagePanel(constructPath(card));
        turnImagePanel.setBounds(UICoords.turnX, UICoords.turnY, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(turnImagePanel);
        turnImagePanel.repaint();
        delay(200, true);
    }

    public void riverIs(Card card) {
        buildPot(true);
        ImagePanel riverImagePanel = new ImagePanel(constructPath(card));
        riverImagePanel.setBounds(UICoords.riverX, UICoords.riverY, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(riverImagePanel);
        riverImagePanel.repaint();
        delay(200, true);
    }

    @Override
    public void handle(Action action) {
        shadowLatestAction(false);
        if (action.isRaise() || action.isAllIn() || action.isCall() || action.isBlind()) {
            latestActionPanel = new BetActionPanel(action);
        } else {
            latestActionPanel = new MessageActionPanel(action);
        }
        ActionPanel prevActionPanel = actionPanels.get(action.getPlayer());
        if (prevActionPanel != null) {
            boardPanel.remove(prevActionPanel);
        }
        actionPanels.put(action.getPlayer(), latestActionPanel);
        boardPanel.add(latestActionPanel);
        Rectangle r = locateActionPanelBounds(action.getPlayer());
        latestActionPanel.setBounds(r.topLeft.x, r.topLeft.y, r.width, r.height);
        repaint();
        delay(500, true);
    }

    private Rectangle locateActionPanelBounds(Player player) {
        int index = currentGame.getIndex(player);
        Rectangle rect = new Rectangle(UICoords.playerPositions[index], PlayerPanel.panelWidth, PlayerPanel.panelHeight);
        Position p = null;
        if (index == 0 || index == 1) {
            p = rect.topRight(13, -25);
        }
        if (index == 2 || index == 3) {
            p = rect.bottomLeft(-5, 11);
        }
        if (index == 4 || index == 5) {
            p = rect.topLeft(13 + ActionPanel.panelWidth, -25);
        }
        if (index == 6 || index == 7) {
            p = rect.topLeft(-5, 11 + ActionPanel.panelHeight);
        }
        return new Rectangle(p, ActionPanel.panelWidth, ActionPanel.panelHeight);
    }

    private void shadowLatestAction(boolean repaint) {
        if (latestActionPanel != null) {
            latestActionPanel.setHighlighted(false);
            if (repaint)
                repaint();
        }
    }

    private void buildPot(boolean repaint) {
        for (ActionPanel actionPanel: actionPanels.values()) {
            boardPanel.remove(actionPanel);
        }
        for (PotPanel potPanel : potPanels) {
            boardPanel.remove(potPanel);
        }
        potPanels.clear();
        boolean side = false;
        List<Pot> allPots = currentHand.getAllPots();
        double potCount = allPots.size();
        double potIndex = 0;
        for (Pot pot : allPots) {
            PotPanel potPanel = new PotPanel(pot, side);
            double potY = UICoords.potY - ((potCount - 1) / 2 - potIndex) * ActionPanel.panelHeight;
            potPanel.setBounds(UICoords.potX, (int) potY, ActionPanel.panelWidth, ActionPanel.panelHeight);
            boardPanel.add(potPanel);
            potPanels.add(potPanel);
            side = true;
            potIndex++;
        }
        if (repaint)
            repaint();
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
    public void potWon(List<Player> potWinners, double eachValue) {
        buildPot(true);
        for (Player player : potWinners) {
            ShowDownPanel showDownPanel = new ShowDownPanel(eachValue);
            if (nextPot > 0)
                potPanels.get(nextPot - 1).setHighlighted(false);
            potPanels.get(nextPot).setHighlighted(true);
            boardPanel.add(showDownPanel, 0);
            Rectangle r = locateActionPanelBounds(player);
            showDownPanel.setBounds(r.topLeft.x, r.topLeft.y, r.width, r.height);
            repaint();
            delay(800, true);
        }
        nextPot++;
        delay(1200, true);
    }

    public void firstCardIs(Player player, Card card) {
        PlayerPanel playerPanel = playerPanels.get(player);
        playerPanel.setCard1(card);
        playerPanel.repaint();
        delay(300, true);
    }

    public void secondCardIs(Player player, Card card) {
        PlayerPanel playerPanel = playerPanels.get(player);
        playerPanel.setCard2(card);
        playerPanel.repaint();
        delay(300, true);
    }

    private void delay(int millis, boolean checkPause) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        if (checkPause)
            checkPause();
    }

    private void checkPause() {
        while (pause) {
            delay(400, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            pause = !pause;
            if (pause) {
                pausePanel.setText("ENTER > UNPAUSE");
                pausePanel.setTextColor(Color.RED);
                boardPanel.repaint();
            } else {
                pausePanel.setText("  ENTER > PAUSE");
                pausePanel.setTextColor(new Color(191, 186, 181));
                boardPanel.repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
