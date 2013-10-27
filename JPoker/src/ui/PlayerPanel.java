package ui;

import players.Player;
import poker.Card;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 25/10/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerPanel extends JPanel {
    static int panelWidth = 63, panelHeight = 74;
    static int cardWidth = 48, cardHeight = 68;
    static int rand = new Random().nextInt(6) + 1;
    static String backOfCardPath = "images/Back of Card " + rand + ".jpg";

    private CardPanel card1Panel;
    private CardPanel card2Panel;
    private JLabel nameLabel;
    private JLabel stackLabel;
    Player player;

    public PlayerPanel(Player player, Double stackSize) {
        this.player = player;
        setBackground(new Color(0, 0, 0, 0));

        stackLabel = new JLabel("" + (stackSize.intValue()));
        stackLabel.setBounds(0, 0, cardWidth + 15, 20);
//        stackLabel.setBackground(new Color(0, 0, 0, 0));
//        stackLabel.setForeground(Color.WHITE);
        add(stackLabel);

        nameLabel = new JLabel(player.toString());
        nameLabel.setBounds(0, 21, cardWidth + 15, 20);
//        nameLabel.setBackground(new Color(0, 0, 0, 0));
//        nameLabel.setForeground(Color.WHITE);
        add(nameLabel);
    }

    private String constructImagePath(Card card) {
        return "images/" + card + ".jpg";
    }

    public void setCard1(Card card1) {
        card1Panel = new CardPanel(constructImagePath(card1), backOfCardPath);
        card1Panel.setBounds(0, 0, cardWidth, cardHeight);
        add(card1Panel);
//        setBackground(new Color(110, 110, 0, 0));
        repaint();
        getParent().repaint();
    }

    public void setCard2(Card card2) {
        card2Panel = new CardPanel(constructImagePath(card2), backOfCardPath);
        card2Panel.setBounds(15, 6, cardWidth, cardHeight);
        add(card2Panel);
        repaint();
        getParent().repaint();
    }

    //    public void paint(Graphics g) {
//        card1Panel.paint(g);
//        card2Panel.paint(g);
//    }

//    public void update(Graphics g) {
//        card1Panel.update(g);
//        card2Panel.update(g);
//    }

}
