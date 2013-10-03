package exceptions;

import poker.HandType;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 1, 2012
 * Time: 7:04:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class HandTypeFoundException extends Throwable {
    private HandType handType;

    public HandTypeFoundException(HandType handType) {
        this.handType = handType;
    }

    public HandType getHandType() {
        return handType;
    }
}
