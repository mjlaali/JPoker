package poker;

import players.Player;

/**
 * User: Sina
 * Date: Mar 1, 2012
 */
public interface Notifiable {
    public void newHand(GameSetting gameSetting);
    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3);
    public void turnIs(Card card);
    public void riverIs(Card card);
    public void cardsShown(Player player, HandType handType);
    public void cardsMucked(Player player);
    public void potWon(Iterable<Player> potWinners, double eachValue);
    public void handle(Action action);
    public void gameEnds();
}
