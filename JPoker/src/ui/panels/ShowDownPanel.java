package ui.panels;

import poker.ShowDownElement;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 05/11/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDownPanel extends ActionPanel {
    private ShowDownElement showDownElement;

    public ShowDownPanel(ShowDownElement showDownElement) {
        this.showDownElement = showDownElement;
    }

    protected void drawActionPanel(Graphics g) {
        g.setColor(new Color(57, 9, 45));
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 5, 5);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 5, 5);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString("Wins:", 4, getHeight() / 2 - 2);
        String winAmountText = "  " + showDownElement.getWinningAmount();
        g.drawString(winAmountText, (getWidth() / 2) - (winAmountText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
