package poker;

import players.Player;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 12:10:18 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Notifiable {
    public void newHand(GameSetting gameSetting);
    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3);
    public void turnIs(Card card);
    public void riverIs(Card card);
    public void playerFolded(Player foldingPlayer);
    public void playerBet(Player bettingPlayer, double bet);
    public void showDown(ShowDown showDown);
    public void gameEnds();
}
