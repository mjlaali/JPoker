package ui.panels;

import poker.Pot;

import java.awt.*;

/**
 * User: Sina
 * Date: 11/11/13
 */
public class PotPanel extends ActionPanel {
    private Pot pot;
    private boolean side;

    public PotPanel(Pot pot, boolean side) {
        this.pot = pot;
        this.side = side;
        highlighted = false;
    }

    protected void drawActionPanel(Graphics g) {
        Color background = highlighted ?  new Color(90, 18, 70) : new Color(57, 9, 45);
        g.setColor(background);
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 10, 10);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString(side ? "SidePot" : "Pot:", 4, getHeight() / 2 - 2);
        String betText = "  " + dropExtraFloatingPoints(pot.getValue());
        g.drawString(betText, (getWidth() / 2) - (betText.length() / 2) * 6, getHeight() / 2 + 12);
    }
}
