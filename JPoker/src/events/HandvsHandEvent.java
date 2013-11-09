package events;

import poker.HandType;
import poker.PokerHandType;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 8:02:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandvsHandEvent extends PokerEvent {
    private PokerHandType type1;
    private PokerHandType type2;

    public HandvsHandEvent(PokerHandType type1, PokerHandType type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

    public void modifyYourStats(EventStat stat, HandType handType) {
//        for (ShowDownElement showDownElement1 : showDown.getElements()) {
//            for (ShowDownElement showDownElement2 : showDown.getElements()) {
//                if (showDownElement1 != showDownElement2) {
//                    if (showDownElement1.getHandType().getType() == type1 && showDownElement2.getHandType().getType() == type2) {
//                        stat.count++;
//                    }
//                    stat.total++;
//                }
//            }
//        }
    }

    public String getName() {
        return type1 + " vs " + type2;
    }
}
