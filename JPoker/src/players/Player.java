package players;

import poker.Card;
import poker.HandType;
import poker.Notifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public abstract class Player implements Notifiable {
	private String id;

	protected Player(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public abstract void firstCardIs(Card card);

	public abstract void secondCardIs(Card card);

    /*
    Called by dealer to ask for this player's bet when it's this player's turn in a betting round
     */
	public abstract double giveYourBet();

    /*
    Called by dealer to ask if this player wants to show cards. True indicates show and false indicates muck
     */
    public abstract boolean decideShowOrMuck(HandType handType);

    public String toString() {
        return id;
    }
}
