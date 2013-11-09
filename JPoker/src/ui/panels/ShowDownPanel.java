package ui.panels;

import players.Player;
import poker.ShowDownElement;

import java.awt.*;

/**
 * User: Admin
 * Date: 05/11/13
 */
public class ShowDownPanel extends ActionPanel {
    private Player player;
    private double winAmount;

    public ShowDownPanel(Player player, double winAmount) {
        this.player = player;
        this.winAmount = winAmount;
    }

    protected void drawActionPanel(Graphics g) {
        g.setColor(new Color(57, 9, 45));
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 5, 5);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 5, 5);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString("Wins:", 4, getHeight() / 2 - 2);
        String winAmountText = "  " + winAmount;
        g.drawString(winAmountText, (getWidth() / 2) - (winAmountText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
