package players;

import poker.Card;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 4, 2012
 * Time: 11:50:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PlayerObserver {
    public void firstCardIs(Player player, Card card);

    public void secondCardIs(Player player, Card card);
}
