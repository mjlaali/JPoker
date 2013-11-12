package poker;

import players.Player;

import java.util.List;

/**
 * User: Sina
 * Date: Mar 1, 2012
 */
public interface Notifiable {
    public void newHand(HandInfo handInfo);
    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3);
    public void turnIs(Card card);
    public void riverIs(Card card);
    public void cardsShown(Player player, HandType handType);
    public void cardsMucked(Player player);
    /*
        Notifies that a list of players have won a pot, each winning the value indicated by "eachValue". When there are
        n pots developed in the hand, this method will be called n times by the dealer
     */
    public void potWon(List<Player> potWinners, double eachValue);
    /*
        Notifies that an action has been taken by one of the players including check, bet, raise, all in, blind posting,
        fold, etc
     */
    public void handle(Action action);
    public void gameEnds();
}
