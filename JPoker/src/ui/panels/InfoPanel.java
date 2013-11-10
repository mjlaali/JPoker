package ui.panels;

import players.Player;
import poker.GameInfo;

import javax.swing.*;
import java.awt.*;

/**
 * User: Admin
 * Date: 27/10/13
 */
public class InfoPanel extends JPanel {
    public static int panelWidth = 55, panelHeight = 27;
    private Player player;
    private GameInfo currentGame;

    public InfoPanel(Player player, GameInfo currentGame) {
        setSize(100, 50);
        this.player = player;
        this.currentGame = currentGame;
    }

    public void paint(Graphics g) {
        drawInfoPanel(g);
    }

    public void update(Graphics g) {
        drawInfoPanel(g);
    }

    private void drawInfoPanel(Graphics g) {
        g.setColor(new Color(57, 51, 25));
        g.fillRect(1, 1, getWidth() - 2, getHeight() - 1);
        g.setColor(new Color(191, 186, 181));
        g.drawRect(1, 1, getWidth() - 2, getHeight() / 2 - 1);
        g.drawRect(1, getHeight() / 2, getWidth() - 2, getHeight() / 2 - 1);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 9);
        g.setFont(font);
        g.drawString(player.getId(), 4, getHeight() / 2 - 2);
        g.drawString("$" + currentGame.getStackSize(player), 5, getHeight() - 4);
    }
}
