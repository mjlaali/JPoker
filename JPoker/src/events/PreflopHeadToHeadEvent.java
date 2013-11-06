package events;

import poker.Card;
import poker.PreflopCards;
import poker.ShowDown;
import poker.ShowDownElement;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 8:26:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreflopHeadToHeadEvent extends PokerEvent {
    private PreflopCards preflopCards1;
    private PreflopCards preflopCards2;
    private boolean mindSuit;

    public PreflopHeadToHeadEvent(PreflopCards preflopCards1, PreflopCards preflopCards2, boolean mindSuit) {
        this.preflopCards1 = preflopCards1;
        this.preflopCards2 = preflopCards2;
        this.mindSuit = mindSuit;
    }

    public void modifyYourStats(EventStat stat, ShowDown showDown) {
        for (ShowDownElement showDownElement1 : showDown.getElements()) {
            Card[] cards1 = showDownElement1.getHandType().getPreflopCards();
            if (preflopCards1.equals(new PreflopCards(cards1[0], cards1[1]), mindSuit)) {
                for (ShowDownElement showDownElement2 : showDown.getElements()) {
                    if (showDownElement1 != showDownElement2) {
                        Card[] cards2 = showDownElement2.getHandType().getPreflopCards();
                        if (preflopCards2.equals(new PreflopCards(cards2[0], cards2[1]), mindSuit)) {
                            if (showDownElement1.getHandType().compareTo(showDownElement2.getHandType()) > 0) {
                                stat.count++;
                            }
                            stat.total++;
                        }
                    }
                }
            }
        }
    }

    public String getName() {
        if (mindSuit) {
            return preflopCards1 + " beats " + preflopCards2;
        } else {
            return preflopCards1.getCard1().rank() + ", " + preflopCards1.getCard2().rank() +
                    " beats " + preflopCards2.getCard1().rank() + ", " + preflopCards2.getCard2().rank();
        }
    }
}
