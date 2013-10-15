package poker;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import poker.shuffler.DefaultShuffler;
import poker.shuffler.Shufeller;

public class Hand {
	private static ArrayList<Card> cards;
	private static Shufeller shufeller;
	
	@BeforeClass
	public static void init(){
		cards = Card.newDeck();
		shufeller = new DefaultShuffler();
	}
	
	@Test
	public void computePreflopHandProb(){
		final int MAX = 1000000;
		PreflopCards preflopCards;
		Map<String, Integer> categoriesCount = new TreeMap<>();
		for (int i = 0; i < MAX; ++i){
			ArrayList<Card> shuffleCards = shufeller.shuffle(cards);
			preflopCards = new PreflopCards(shuffleCards.get(0), shuffleCards.get(1));
			String category = preflopCards.getCategory();
			Integer cnt = categoriesCount.get(category);
			if (cnt == null)
				categoriesCount.put(category, 1);
			else
				categoriesCount.put(category, cnt + 1);
		}
		
		double poket = 0, suited = 0, suitedConnected[] = new double[3], connected[] = new double[3],
				above21 = 0, suitedAbove21 = 0;
		for (Entry<String, Integer> catCnt: categoriesCount.entrySet()){
			String category = catCnt.getKey();
			int cnt = catCnt.getValue();
			if (category.length() == 2)
				poket += cnt;
			else {
				boolean isSuited = category.charAt(2) == 's'; 
				int num1 = Card.build(category.charAt(0) + "S").rank().value;
				int num2 = Card.build(category.charAt(1) + "S").rank().value;
				
				double[] sconnected;
				if (isSuited)
					sconnected = suitedConnected;
				else
					sconnected = connected;
				
				if (num1 - num2 <= 3)
					sconnected[num1 - num2 - 1] = sconnected[num1 - num2 - 1] + cnt;
				else if (isSuited && num1 + num2 > 21)
					suitedAbove21 += cnt;
				else if (isSuited)
					suited += cnt;
				else if (num1 + num2 > 21)
					above21 += cnt;
			}
		}
		
		double poketAceProbe = (int)(categoriesCount.get("AA") / (double)MAX * 10000  + 0.5) / 100.0;
		System.out.println("Poket ace probability: " + poketAceProbe);
		Assert.assertTrue(poketAceProbe > 0.42 && poketAceProbe < 0.48);

		int biasMax = MAX / 100;
		System.out.println("Poket: " + poket / biasMax);
		for (int i = 0; i < 3; ++i)
			System.out.println("Suited Connectors(" + (i + 1) + "): " + suitedConnected[i] / biasMax);
		System.out.println("Suited above21: " + suitedAbove21 / biasMax);
		
		System.out.println("Suited: " + suited / biasMax);
		for (int i = 0; i < 3; ++i)
			System.out.println("Connectors(" + (i + 1) + "): " + connected[i] / biasMax);

		System.out.println("Above 21: " + above21 / biasMax);
	}
}
