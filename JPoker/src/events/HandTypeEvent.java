package events;

import poker.PokerHandType;
import poker.ShowDownElement;
import poker.ShowDown;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 7:50:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandTypeEvent extends PokerEvent {
    private PokerHandType type;

    public HandTypeEvent(PokerHandType type) {
        this.type = type;
    }

    public void modifyYourStats(EventStat stat, ShowDown showDown) {
        for (ShowDownElement showDownElement : showDown.getShowDownElements()) {
            if (showDownElement.getHandType().getType() == type) {
                stat.count++;
            }
            stat.total++;
        }
    }

    public String getName() {
        return type.toString();
    }
}
