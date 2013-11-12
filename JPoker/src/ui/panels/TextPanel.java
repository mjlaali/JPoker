package ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * User: Sina
 * Date: 11/12/13
 */
public class TextPanel extends JPanel {
    private String text;
    private Font font;
    private Color textColor;

    public TextPanel(String text, Font font, Color textColor) {
        this.text = text;
        this.font = font;
        this.textColor = textColor;
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public void paint(Graphics g) {
        draw(g);
    }

    public void update(Graphics g) {
        draw(g);
    }

    protected void draw(Graphics g) {
        g.setFont(font);
        g.setColor(textColor);
        g.drawString(text, 5, getHeight() / 2 + 4);
    }
}
