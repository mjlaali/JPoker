package poker;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import players.IPlayer;

public class PowerfulDealer {
	private List<Notifiable> viewer;
	private Map<IPlayer, Double> playersStack;
	private Set<IPlayer> playersAvailable;
	
	private double pot;
	private CardDeck shuffleCards;
	
	public void playAGame(){
		shuffleCards = new CardDeck();
		
		preflop();
		bet();
		turn();
		bet();
		river();
		bet();
		showDown();
	}

	private void preflop() {
		playersAvailable = new TreeSet<>();
		for (Entry<IPlayer, Double> playerStatck: playersStack.entrySet()){
			if (playerStatck.getValue() > 0)
				playersAvailable.add(playerStatck.getKey());
		}
	}

	private void turn() {
		
	}

	private void river() {
		
	}

	private void bet() {
		
	}

	private void showDown() {
		
	}
}
