package players;

import poker.Card;
import poker.HandType;
import poker.Notifiable;
import poker.PreflopCards;

import java.util.List;
import java.util.ArrayList;

/**
 * User: Sina
 * Date: Feb 29, 2012
 */
public abstract class Player implements Notifiable {
	private String id;
	private List<PlayerObserver> playerObservers;
	protected Card card1;
	protected Card card2;

	protected Player(String id, List<PlayerObserver> playerObservers) {
		this.id = id;
		this.playerObservers = playerObservers == null ? new ArrayList<PlayerObserver>() : playerObservers;
	}

	public String getId() {
		return id;
	}

	public String toString() {
		return id;
	}

	public void firstCardIs(Card card) {
		card1 = card;
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.firstCardIs(this, card1);
		}
	}

	public void secondCardIs(Card card) {
		card2 = card;
		preflopIs(card1, card2);
		for (PlayerObserver playerObserver : playerObservers) {
			playerObserver.secondCardIs(this, card2);
		}
	}

	public PreflopCards getPreFlopCards(){
		return new PreflopCards(card1, card2);
	}

	public abstract void preflopIs(Card card1, Card card2);

    /*
    Called by dealer to ask for this player's bet when it's this player's turn in a betting round
     */
	public abstract double giveYourBet();

    /*
    Called by dealer to ask if this player wants to show cards. True indicates show and false indicates muck
     */
    public abstract boolean decideShowOrMuck(HandType handType);
}
