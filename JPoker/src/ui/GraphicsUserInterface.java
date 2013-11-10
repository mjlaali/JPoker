package ui;

import players.Player;
import players.PlayerObserver;
import poker.*;
import poker.Action;
import ui.panels.*;
import ui.shapes.*;
import ui.shapes.Rectangle;

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
    private Map<Player, PlayerPanel> playerPanels = new HashMap<>();
    private ImagePanel boardPanel;
    private GameInfo currentGame;
    private ActionPanel latestActionPanel;
    private HandInfo thisHand;

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
        this.thisHand = handInfo;
        List<Player> players = currentGame.getPlayers();
        if (UICoords.playerPositions.length < players.size()) {
            throw new RuntimeException("At most " + UICoords.playerPositions.length + " getStartingPlayers are supported by this UI");
        }
        boardPanel.removeAll();
        addDealerButton();
        int i = 0;
        for (Player player : players) {
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
    }

    private void addDealerButton() {
        ImagePanel dealerButtonPanel = new ImagePanel("images/DealerButton.jpg");
        Rectangle bounds = locateDealerButton(thisHand.getDealer());
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
        hideLatestAction(true);
        ImagePanel flop1ImagePanel = new ImagePanel(constructPath(flopCard1));
        flop1ImagePanel.setBounds(UICoords.flop1X, UICoords.flop1Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop1ImagePanel);
        flop1ImagePanel.repaint();
        delay(200);
        ImagePanel flop2ImagePanel = new ImagePanel(constructPath(flopCard2));
        flop2ImagePanel.setBounds(UICoords.flop2X, UICoords.flop2Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop2ImagePanel);
        flop2ImagePanel.repaint();
        delay(100);
        ImagePanel flop3ImagePanel = new ImagePanel(constructPath(flopCard3));
        flop3ImagePanel.setBounds(UICoords.flop3X, UICoords.flop3Y, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(flop3ImagePanel);
        flop3ImagePanel.repaint();
        delay(100);
    }

    public void turnIs(Card card) {
        hideLatestAction(true);
        ImagePanel turnImagePanel = new ImagePanel(constructPath(card));
        turnImagePanel.setBounds(UICoords.turnX, UICoords.turnY, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(turnImagePanel);
        turnImagePanel.repaint();
        delay(200);
    }

    public void riverIs(Card card) {
        hideLatestAction(true);
        ImagePanel riverImagePanel = new ImagePanel(constructPath(card));
        riverImagePanel.setBounds(UICoords.riverX, UICoords.riverY, PlayerPanel.cardWidth, PlayerPanel.cardHeight);
        boardPanel.add(riverImagePanel);
        riverImagePanel.repaint();
        delay(200);
    }

    @Override
    public void handle(Action action) {
        hideLatestAction(false);
        latestActionPanel = new BetActionPanel(action.getBet());
        boardPanel.add(latestActionPanel);
        Rectangle r = locateActionPanelBounds(action.getPlayer());
        latestActionPanel.setBounds(r.topLeft.x, r.topLeft.y, r.width, r.height);
        repaint();
        delay(400);
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
            Rectangle r = locateActionPanelBounds(player);
            showDownPanel.setBounds(r.topLeft.x, r.topLeft.y, r.width, r.height);
            repaint();
            delay(1000);
        }
        delay(4500);
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
