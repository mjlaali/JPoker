package processor.preflop;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import exceptions.OutOfCardsException;

import players.CheckingPlayer;
import players.PlayerObserver;
import poker.Dealer;
import poker.GameSetting;
import poker.Notifiable;
import processors.preflop.PreflopAnalyzer;

public class HandRanking {
	
	@Test
	public void testRanking() throws OutOfCardsException{
		PreflopAnalyzer preflopAnalyzer = new PreflopAnalyzer();
		GameSetting gameSetting = new GameSetting(1, 2);
	    for (int i = 0; i < 2; i++) {
	        gameSetting.addPlayerStack(new CheckingPlayer("Player " + (i + 1), new LinkedList<PlayerObserver>()), 10000);
	    }
	    LinkedList<Notifiable> externalNotifiables = new LinkedList<Notifiable>();
	    externalNotifiables.add(preflopAnalyzer);
		Dealer dealer = new Dealer(gameSetting, externalNotifiables);
	    dealer.runGame(1000000);

	    List<String> pokerHands = preflopAnalyzer.report(null);
	    
	    Assert.assertEquals(169, pokerHands.size());
	    
	    //according to the http://www.cs.indiana.edu/~kapadia/nofoldem/
	    Assert.assertEquals("[<A A>, <K K>, <Q Q>, <J J>, <T T>]", pokerHands.subList(0, 5).toString());

	}
}
