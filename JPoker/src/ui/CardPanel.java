package ui;

import poker.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class CardPanel extends JPanel implements MouseListener {
    private Image frontImage;
    private boolean frontActive;
    private static int rand = new Random().nextInt(6) + 1;
    private static Image backImage = Toolkit.getDefaultToolkit().getImage("images/Back of Card " + rand + ".jpg");

    public CardPanel(Card card) {
        frontImage = Toolkit.getDefaultToolkit().getImage(constructImagePath(card));
        frontActive = false;
        setBackground(new Color(0, 0, 0, 0));
        this.addMouseListener(this);
    }

    private static String constructImagePath(Card card) {
        return "images/" + card + ".jpg";
    }

    private Image activeImage() {
        return frontActive ? frontImage : backImage;
    }

    public void paint(Graphics g) {
        g.drawImage(activeImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public void update(Graphics g) {
        g.drawImage(activeImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public void mouseClicked(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        frontActive = !frontActive;
        getParent().repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
