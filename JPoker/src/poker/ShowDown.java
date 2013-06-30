package poker;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 7:32:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShowDown {
    private List<ShowDownElement> showDownElements;

    public ShowDown(List<ShowDownElement> showDownElements) {
        this.showDownElements = showDownElements;
    }

    public List<ShowDownElement> getShowDownElements() {
        return showDownElements;
    }
}
