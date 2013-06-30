package events;

import events.EventStat;
import poker.ShowDown;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 7:24:00 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class PokerEvent {
    public abstract void modifyYourStats(EventStat stat, ShowDown showDown);

    public abstract String getName();

    public String toString() {
        return getName();
    }
}
