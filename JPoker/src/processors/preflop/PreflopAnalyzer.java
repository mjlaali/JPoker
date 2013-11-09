package processors.preflop;

import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import players.CheckingPlayer;
import players.Player;
import players.PlayerObserver;
import poker.*;
import exceptions.OutOfCardsException;

public class PreflopAnalyzer implements Notifiable {
	private PreflopCardsComparator comparator = new PreflopCardsComparator();
	private Map<PreflopCards, Integer> winCount = new TreeMap<PreflopCards, Integer>(comparator);
	private Map<PreflopCards, Integer> totalCount = new TreeMap<PreflopCards, Integer>(comparator);
	private int total = 0;

    @Override
    public void newHand(GameSetting gameSetting) {
    }

    @Override
    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {
    }

    @Override
    public void turnIs(Card card) {
    }

    @Override
    public void riverIs(Card card) {
    }

    @Override
    public void handle(Action action) {
    }

    @Override
    public void gameEnds() {
    }

    @Override
    public void cardsShown(Player player, HandType handType) {
    }

    @Override
    public void cardsMucked(Player player) {
    }

    @Override
    public void potWon(Iterable<Player> potWinners, double eachValue) {
    }

    private void addOne(PreflopCards preFlopCards, Map<PreflopCards, Integer> toAdd) {
		Integer old = toAdd.get(preFlopCards);
		if (old == null)
			toAdd.put(preFlopCards, 1);
		else
			toAdd.put(preFlopCards, old + 1);
	}
	
	public List<String> report(PrintStream reportOutput){
		System.out.println("Total game = " + total);

		Map<Double, List<PreflopCards>> rankedHands = new TreeMap<Double, List<PreflopCards>>();
		for (Entry<PreflopCards, Integer> aHandWinCount: winCount.entrySet()){
			double winingProb = aHandWinCount.getValue() * 1.0 / totalCount.get(aHandWinCount.getKey());
			List<PreflopCards> list = rankedHands.get(winingProb);
			if (list == null){
				list = new LinkedList<PreflopCards>();
				rankedHands.put(winingProb, list);
			}
			list.add(aHandWinCount.getKey());
		}

		List<Double> keys = new LinkedList<Double>(rankedHands.keySet());
		Collections.reverse(keys);
		
		List<String> rankedHand = new LinkedList<String>();
		int rank = 1;
		for (Double key: keys){
			if (reportOutput != null){
				reportOutput.println();
				reportOutput.println(rank + ":" + key);
			}
			for (PreflopCards aHand: rankedHands.get(key)){
				String strHand = comparator.getHandString(aHand);
				if (reportOutput != null) {
					reportOutput.print(strHand + " ## ");
				}
				rankedHand.add(strHand);
			}
			++rank;
		}
		return rankedHand;
	}

	
	public static void main(String[] args) throws OutOfCardsException {
		PreflopAnalyzer preflopAnalyzer = new PreflopAnalyzer();
		GameSetting gameSetting = new GameSetting(1, 2);
        for (int i = 0; i < 2; i++) {
            gameSetting.addPlayerStack(new CheckingPlayer("Player " + (i + 1), new LinkedList<PlayerObserver>()), 10000);
        }
        LinkedList<Notifiable> externalNotifiables = new LinkedList<Notifiable>();
        externalNotifiables.add(preflopAnalyzer);
		Dealer dealer = new Dealer(gameSetting, externalNotifiables);
        dealer.runGame(1000000);

        preflopAnalyzer.report(System.out);
	}
}
