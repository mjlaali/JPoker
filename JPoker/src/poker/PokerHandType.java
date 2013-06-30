package poker;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 2:32:30 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PokerHandType {
    STRAIGHT_FLUSH(1), FOUR_OF_A_KIND(2), FULL_HOUSE(3), FLUSH(4), STRAIGHT(5), THREE_OF_A_KIND(6),
    TWO_PAIR(7), PAIR(8), HIGH_CARD(9);

    private int rank;

    PokerHandType(int order) {
        this.rank = order;
    }

    public int getRank() {
        return rank;
    }
}
