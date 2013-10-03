package processors;

import events.*;
import exceptions.OutOfCardsException;
import players.Player;
import poker.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 2, 2012
 * Time: 4:21:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class Analyzer implements Notifiable {
    private List<ShowDown> allShowDowns = new ArrayList<ShowDown>();
    private LinkedHashMap<PokerEvent, EventStat> pokerEventStats;
    private List<PokerEvent> analysisEvents;

    public Analyzer() {
        analysisEvents = new ArrayList<PokerEvent>();
        for (PokerHandType pokerHandType : PokerHandType.values()) {
            analysisEvents.add(new HandTypeEvent(pokerHandType));
        }
        analysisEvents.add(new HandvsHandEvent(PokerHandType.HIGH_CARD, PokerHandType.HIGH_CARD));
        analysisEvents.add(new PreflopHeadToHeadEvent(
                new PreflopCards(Card.build("AD"), Card.build("AH")),
                new PreflopCards(Card.build("JD"), Card.build("8S")),
                false
        ));
    }

    public void newHand(GameSetting gameSetting) {

    }

    public void flopIs(Card flopCard1, Card flopCard2, Card flopCard3) {

    }

    public void turnIs(Card card) {

    }

    public void riverIs(Card card) {

    }

    public void playerFolded(Player foldingPlayer) {

    }

    public void playerBet(Player bettingPlayer, double bet) {

    }

    public void showDown(ShowDown showDown) {
        allShowDowns.add(showDown);
    }

    public void gameEnds() {

    }

    private void computeStats() {
        pokerEventStats = new LinkedHashMap<PokerEvent, EventStat>();
        for (PokerEvent pokerEvent : analysisEvents) {
            pokerEventStats.put(pokerEvent, new EventStat());
        }
        for (ShowDown showDown : allShowDowns) {
            for (PokerEvent pokerEvent : analysisEvents) {
                EventStat stat = pokerEventStats.get(pokerEvent);
                pokerEvent.modifyYourStats(stat, showDown);
            }
        }
    }

    public String getStatsAsString() {
        String statString = "";
        computeStats();
        for (PokerEvent pokerEvent : pokerEventStats.keySet()) {
            EventStat stat = pokerEventStats.get(pokerEvent);
            double twoDigitPrecision = stat.getPercentage();
            statString += pokerEvent + " rate : " + twoDigitPrecision + "%" + "\t\t precision: " + stat.total + " incidents" + "\n";
        }
        return statString;
    }

    public static void main(String[] args) throws OutOfCardsException {
        SettingDecider settingDecider = new StaticSettingDecider();
        Dealer dealer = new Dealer(settingDecider.getGameSetting(), settingDecider.getExternalNotifiables());
        dealer.runGame(10000);
    }
}
