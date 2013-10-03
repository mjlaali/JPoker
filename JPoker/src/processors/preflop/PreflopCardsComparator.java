package processors.preflop;

import java.util.Comparator;

import poker.PreflopCards;

public class PreflopCardsComparator implements Comparator<PreflopCards>{

	@Override
	public int compare(PreflopCards o1, PreflopCards o2) {
		return getHandString(o1).compareTo(getHandString(o2));
	}
	
	public String getHandString(PreflopCards hand){
		String suit = hand.getCard1().suit() == hand.getCard2().suit() ? " S" : "";
		if (hand.getCard1().rank().value > hand.getCard2().rank().value)
			return "<" + hand.getCard1().rank() + " " + hand.getCard2().rank() + suit + ">";
		return "<" + hand.getCard2().rank() + " " + hand.getCard1().rank() + suit + ">";
				
	}
	
//	public static void main(String[] args) {
//		PreflopCards preflopCards = new PreflopCards(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.KING, Suit.HEARTS));
//		PreflopCardsComparator preflopCardsComparator = new PreflopCardsComparator();
//		System.out.println(preflopCardsComparator.getHandString(preflopCards));
//	}

}
