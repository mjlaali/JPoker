package ui.panels;

import poker.Action;

import java.awt.*;

/**
 * User: Admin
 * Date: 05/11/13
 */
public class BetActionPanel extends ActionPanel {
    private Action action;

    public BetActionPanel(Action action) {
        this.action = action;
    }

    protected void drawActionPanel(Graphics g) {
        Color background = highlighted ?  new Color(84, 76, 33) : new Color(57, 51, 25);
        g.setColor(background);
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 10, 10);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString(action + ":", 4, getHeight() / 2 - 2);
        String betText = "  " + dropExtraFloatingPoints(action.getBet());
        g.drawString(betText, (getWidth() / 2) - (betText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
