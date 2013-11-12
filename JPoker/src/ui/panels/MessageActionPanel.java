package ui.panels;

import poker.Action;

import java.awt.*;

/**
 * User: Sina
 * Date: 11/11/13
 */
public class MessageActionPanel extends ActionPanel {
    private Action action;

    public MessageActionPanel(Action action) {
        this.action = action;
    }

    @Override
    protected void drawActionPanel(Graphics g) {
        Color background = highlighted ?  new Color(84, 76, 33) : new Color(57, 51, 25);
        g.setColor(background);
        g.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 1, 10, 10);
        g.setColor(new Color(191, 186, 181));
        g.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
        Font font = new Font("Comic Sans MS", Font.PLAIN, 11);
        g.setFont(font);
        g.drawString(action.toString(), 12, getHeight() / 2 + 4);
    }
}
