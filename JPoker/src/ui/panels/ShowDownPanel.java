package ui.panels;

import players.Player;

import java.awt.*;

/**
 * User: Admin
 * Date: 05/11/13
 */
public class ShowDownPanel extends ActionPanel {
    private double winAmount;

    public ShowDownPanel(double winAmount) {
        this.winAmount = winAmount;
    }

    protected void drawActionPanel(Graphics g) {
//        new Float().
        g.setColor(new Color(90, 18, 70));
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 10, 10);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString("Wins:", 4, getHeight() / 2 - 2);
        String winAmountText = "  " + dropExtraFloatingPoints(winAmount);
        g.drawString(winAmountText, (getWidth() / 2) - (winAmountText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
