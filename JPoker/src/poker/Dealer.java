package poker;

import players.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.EarlyFinishedHandException;
import exceptions.OutOfCardsException;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Feb 29, 2012
 * Time: 6:21:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dealer {
    private GameSetting gameSetting;
    private Notifier notifier;

    public Dealer(GameSetting gameSetting, List<Notifiable> externalNotifiables) {
        this.gameSetting = gameSetting;
        List<Notifiable> notifiables = new ArrayList<Notifiable>();
        notifiables.addAll(gameSetting.getPlayers());
        notifiables.addAll(externalNotifiables);
        notifier = new Notifier(notifiables);
    }

    public void runGame(int maxCount) throws OutOfCardsException {
        boolean tableOpen = true;
        int handCount = 0;
        int smallBlindIndex = 0;
        
        while (tableOpen) {
            HandSetting handSetting = new HandSetting(gameSetting, smallBlindIndex);
            notifier.notifyNewHandStarted();
            handSetting.dealPreflop();
            //noinspection EmptyCatchBlock
            try {
                doBettingRound(handSetting);
                handSetting.dealFlop();
                notifier.showFlopToEveryOne(handSetting.getBoard().cards[0], handSetting.getBoard().cards[1], handSetting.getBoard().cards[2]);
                doBettingRound(handSetting);
                handSetting.dealTurn();
                notifier.showTurnToEveryOne(handSetting.getBoard().cards[3]);
                doBettingRound(handSetting);
                handSetting.dealRiver();
                notifier.showRiverToEveryOne(handSetting.getBoard().cards[4]);
                doBettingRound(handSetting);
                ShowDown showDown = handSetting.showDown();
                notifier.announceShowDown(showDown);
            } catch (EarlyFinishedHandException e) {
                handSetting.collectBets();
                ShowDownElement showDownElement = new ShowDownElement(e.getWinner(), null, handSetting.getStack());
                List<ShowDownElement> showDownElements = new ArrayList<ShowDownElement>();
                showDownElements.add(showDownElement);
                notifier.announceShowDown(new ShowDown(showDownElements));
            }
            smallBlindIndex = (smallBlindIndex + 1) % gameSetting.getPlayers().size();
            handCount++;
            if (maxCount != -1 && handCount >= maxCount) {
                tableOpen = false;
            }
        }
        notifier.notifyGameEnds();
    }

    private void doBettingRound(HandSetting handSetting) throws EarlyFinishedHandException {
        // TODO: handle all in requiring stack separation and over stack bet error
        double lastBet = 0;
        Player raiser = null;
        Player currentPlayer = handSetting.getCurrentPlayer();
        while (currentPlayer != raiser) {
            double bet = currentPlayer.giveYourBet();
            if (bet < lastBet) { // negative is going to mean fold and less than lastBet is possibly hack attempt
                handSetting.foldCurrent();
                notifier.announceFold(currentPlayer);
                if (handSetting.getRemainingCount() == 1) {
                    throw new EarlyFinishedHandException(handSetting.moveNext());
                }
            } else {
                if (bet > lastBet || raiser == null) {
                    raiser = currentPlayer;
                    lastBet = bet;
                }
                double stackDeduction = handSetting.setBetInFront(currentPlayer, bet);
                gameSetting.deductStack(currentPlayer, stackDeduction);
                notifier.announceBet(currentPlayer, bet);
            }
            currentPlayer = handSetting.moveNext();
        }
        handSetting.collectBets();
    }

    class Notifier {
        private List<Notifiable> notifiables;

        public Notifier(List<Notifiable> notifiables) {
            this.notifiables = notifiables;
        }

        private void announceFold(Player foldingPlayer) {
            for (Notifiable notifiable : notifiables) {
                notifiable.playerFolded(foldingPlayer);
            }
        }

        private void announceShowDown(ShowDown showDownElements) {
            for (Notifiable notifiable : notifiables) {
                notifiable.showDown(showDownElements);
            }
        }

        private void announceBet(Player bettingPlayer, double bet) {
            for (Notifiable notifiable : notifiables) {
                notifiable.playerBet(bettingPlayer, bet);
            }
        }

        public void notifyNewHandStarted() {
            for (Notifiable notifiable : notifiables) {
                notifiable.newHand(gameSetting);
            }
        }

        private void showFlopToEveryOne(Card card1, Card card2, Card card3) {
            for (Notifiable notifiable : notifiables) {
                notifiable.flopIs(card1, card2, card3);
            }
        }

        private void showTurnToEveryOne(Card card) {
            for (Notifiable notifiable : notifiables) {
                notifiable.turnIs(card);
            }
        }

        private void showRiverToEveryOne(Card card) {
            for (Notifiable notifiable : notifiables) {
                notifiable.riverIs(card);
            }
        }

        public void notifyGameEnds() {
            for (Notifiable notifiable : notifiables) {
                notifiable.gameEnds();
            }
        }
    }
}
