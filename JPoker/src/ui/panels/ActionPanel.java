package ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 04/11/13
 * Time: 6:25 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class ActionPanel extends JPanel {
    public static int panelWidth = 55, panelHeight = 30;

    public void paint(Graphics g) {
        drawActionPanel(g);
    }

    public void update(Graphics g) {
        drawActionPanel(g);
    }

    protected abstract void drawActionPanel(Graphics g);
}
