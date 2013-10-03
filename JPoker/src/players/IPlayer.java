package players;

import poker.Card;

public interface IPlayer extends Comparable<IPlayer> {
	public String getID();
	public void getFirstPreflop(Card card);
	public void getSecondPreflop(Card card);
	public void seeYourStack(double stack, double smallBlind, double bigBlind);
	
	public void playerRaised(IPlayer player, double raisedAmoung);

	/**
	 * Fold = any number below zero
	 * Call = exact the same number of the bet
	 * Rise = a number bigger than the bet
	 * @return the amount of money for betting  
	 */
	public double makeDecision(double bet);
}
