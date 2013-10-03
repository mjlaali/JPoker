package events;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 7:41:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventStat {
    public int count;
    public int total;

    public EventStat() {
        this.total = 0;
        this.count = 0;
    }

    public String toString() {
        return "" + count + " out of " + total;
    }

    public double getPercentage() {
        double percentage = ((double) count) * 100d / total;
        double temp = ((int) (percentage * 100d));
        return temp / 100d;
    }
}
