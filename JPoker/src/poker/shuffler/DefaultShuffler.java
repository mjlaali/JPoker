package poker.shuffler;

import java.util.ArrayList;
import java.util.Random;

import poker.Card;

public class DefaultShuffler implements Shufeller{

	@Override
	public ArrayList<Card> shuffle(ArrayList<Card> cards) {
		ArrayList<Card> shuffleCards = new ArrayList<Card>(cards.size());
		cards = new ArrayList<>(cards);
        Random random = new Random();
        for (int i = cards.size(); i > 0; i--) {
			int rand = random.nextInt(i);
            shuffleCards.add(cards.remove(rand));
        }
		return shuffleCards;
	}
	
}
