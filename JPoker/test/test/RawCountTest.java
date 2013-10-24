package test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

interface Validator{
	public boolean isValid(int[] cards);
}

class OutValidator implements Validator{
	int out;
	public OutValidator(int out) {
		this.out = out;
	}
	
	@Override
	public boolean isValid(int[] cards) {
		for (int card: cards)
			if (card == out)
				return true;
		return false;
	}
}

class PairOutValidator implements Validator{
	private int a, b;
	public PairOutValidator(int a, int b) {
		this.a = a;
		this.b = b;
		
	}
	
	@Override
	public boolean isValid(int[] cards) {
		int state = 0;
		for (int card: cards){
			if (card == a){
				state += 1;
			} else if (card == b)
				state += 2;
		}
			
		return state == 3;
	}
	
}

class StraightValidator implements Validator{
	int[] cardsMin, cardsMax;
	int[] pow2;
	
	public StraightValidator(int[] cardsBox) {
		cardsMin = new int[cardsBox.length];
		cardsMax = new int[cardsBox.length];
		cardsMin[0] = 0;
		for (int i = 0; i < cardsMin.length; ++i){
			if (cardsBox[i] != 0){
				if (i - 1 >= 0)
					cardsMin[i] = cardsMax[i - 1];
				cardsMax[i] = cardsMin[i] + cardsBox[i];
			} else{
				cardsMin[i] = cardsMin[i - 1];
				cardsMax[i] = cardsMax[i - 1];
			}
		}
		
		pow2 = new int[cardsBox.length + 1];
		pow2[0] = 1;
		for (int i = 1; i < pow2.length; ++i){
			pow2[i] = pow2[i - 1] * 2;
		}
	}
	
	@Override
	public boolean isValid(int[] cards) {
		int state = 0;
		for (int card: cards){
			for (int i = 0; i < cardsMin.length; ++i){
				if (cardsMax[i] > card && card >= cardsMin[i] && (state & pow2[i]) == 0){
					state = state | pow2[i];
					break;
				}
			}
		}
		
		return state == (pow2[pow2.length - 1] - 1);
	}
	
}



public class RawCountTest {
	private List<Integer> remaindedCard = new LinkedList();
	private int cardnumber;
	private Random random;
	

	public RawCountTest() {
		random = new Random(new Date().getTime());
	}
	
	public void setCardnumber(int cardnumber) {
		this.cardnumber = cardnumber;
	}
	
	public int[] run(int draw){
		reset();
		int[] cards = new int[draw];
		for (int i = 0; i < draw; ++i)
			cards[i] = pickACard();
		
		return cards;
	}
	
	public int countEvent(long times, int draw, int cardnumber, Validator validator){
		setCardnumber(cardnumber);
		int count = 0;
		for (long i = 0; i < times; ++i){
			int[] cards = run(draw);
			if (validator.isValid(cards))
				++count;
		}
		return count;
	}
	
	public void reset(){
		remaindedCard.clear();
		for (int i = 0; i < cardnumber; ++i)
			remaindedCard.add(i);
	}

	private int pickACard() {
		int idx = random.nextInt(remaindedCard.size());
		return remaindedCard.remove(idx);
	}
	
	public static void main(String[] args) {
		int count = 1000000;
		int draw = 3;
		int cardNumber = 50;
		RawCountTest rawCountTest = new RawCountTest();
		int countEvent = rawCountTest.countEvent(count, draw, cardNumber, new StraightValidator(new int[]{11, 0, 0}));
		
		System.out.println((double)countEvent * 100 / count);
	}
}
