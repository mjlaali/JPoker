package ui.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ariyan
 * utilities.Date: May 19, 2005
 * Time: 3:24:40 PM
 * To change this template use Options | File Templates.
 */
public class ImagePanel extends JPanel {
    private Image image;

    public ImagePanel(String path) {
        super();
        this.image = Toolkit.getDefaultToolkit().getImage(path);
    }

    public ImagePanel(Image image) {
        super();
        this.image = image;
    }

    public void loadImage(Image image) {
        this.image = image;
        repaint();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(image,  0, 0, getWidth(), getHeight(), this);
    }

    public void update(Graphics g) {
        g.drawImage(image,  0, 0, getWidth(), getHeight(), this);
    }

    public Image getCurrentImage() {
        return image;
    }

    public Rectangle getRectangle() {
        return getBounds();
    }
}
