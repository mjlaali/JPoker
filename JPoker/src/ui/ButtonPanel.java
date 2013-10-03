package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonPanel extends Panel implements MouseListener {
    private Image mainImage;
    private Image onOverImage;
    private Image activeImage;

    private ActionListener listener;
    private String actionCommand = "";

    public ButtonPanel(String mainImagePath, String onOverImagePath) {
        mainImage = Toolkit.getDefaultToolkit().getImage(mainImagePath);
        onOverImage = Toolkit.getDefaultToolkit().getImage(onOverImagePath);
        activeImage = mainImage;
        this.addMouseListener(this);
    }

    public ButtonPanel(Image mainImage, Image onOverImage) {
        this.mainImage = mainImage;
        this.onOverImage = onOverImage;
        activeImage = mainImage;
        this.addMouseListener(this);
    }


    public void setMainImage(String mainImagePath) {
        this.mainImage = Toolkit.getDefaultToolkit().getImage(mainImagePath);
        this.activeImage = mainImage;
    }

    public void setOnOverImage(String onOverImagePath) {
        this.onOverImage = Toolkit.getDefaultToolkit().getImage(onOverImagePath);
    }

    public void addActionListener(ActionListener listener) {
        this.listener = listener;
    }

    public void setActionCommand(String command) {
        actionCommand = command;
    }

    public void paint(Graphics g) {
        g.drawImage(activeImage,  0, 0, getWidth(), getHeight(), this);
    }

    public void update(Graphics g) {
        g.drawImage(activeImage,  0, 0, getWidth(), getHeight(), this);
    }

    public void mouseClicked(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        activeImage = mainImage;
        repaint();
        if (listener != null)
            listener.actionPerformed(new ActionEvent(this, 0, actionCommand));
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        activeImage = onOverImage;
        repaint();
    }

    public void mouseExited(MouseEvent e) {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        activeImage = mainImage;
        repaint();
    }
}
