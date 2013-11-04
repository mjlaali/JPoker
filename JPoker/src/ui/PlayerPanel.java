package ui;

import players.Player;
import poker.Card;
import poker.CardDeck;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

    private CardPanel card1Panel;
    private CardPanel card2Panel;
    private Player player;
    private InfoPanel infoPanel;

    public PlayerPanel(Player player, InfoPanel infoPanel) {
        this.player = player;
        this.infoPanel = infoPanel;
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setCard1(Card card1) {
        card1Panel = new CardPanel(card1);
        card1Panel.setBounds(0, 0, cardWidth, cardHeight);
        add(card1Panel, 0);
        getParent().repaint();
    }

    public void setCard2(Card card2) {
        card2Panel = new CardPanel(card2);
        card2Panel.setBounds(15, 6, cardWidth, cardHeight);
        add(card2Panel, 0);
        getParent().repaint();
    }
}
