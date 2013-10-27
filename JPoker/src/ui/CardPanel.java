package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class CardPanel extends JPanel implements MouseListener {
    private Image frontImage;
    private Image backImage;
    private boolean frontActive;

    public CardPanel(String frontImagePath, String backImagePath) {
        frontImage = Toolkit.getDefaultToolkit().getImage(frontImagePath);
        backImage = Toolkit.getDefaultToolkit().getImage(backImagePath);
        frontActive = false;
        setBackground(new Color(0, 0, 0, 0));
        this.addMouseListener(this);
    }

    private Image activeImage() {
        return frontActive ? frontImage : backImage;
    }

    public void paint(Graphics g) {
        g.drawImage(activeImage(),  0, 0, getWidth(), getHeight(), this);
    }

    public void update(Graphics g) {
        g.drawImage(activeImage(),  0, 0, getWidth(), getHeight(), this);
    }

    public void mouseClicked(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        frontActive = !frontActive;
        repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }
}
