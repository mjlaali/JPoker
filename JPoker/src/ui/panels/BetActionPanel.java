package ui.panels;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 05/11/13
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class BetActionPanel extends ActionPanel {
    private double bet;

    public BetActionPanel(double bet) {
        this.bet = bet;
    }

    protected void drawActionPanel(Graphics g) {
        g.setColor(new Color(57, 51, 25));
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 5, 5);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 5, 5);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString("Bet:", 4, getHeight() / 2 - 2);
        String betText = "  " + bet;
        g.drawString(betText, (getWidth() / 2) - (betText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
