package poker;

import players.Player;

import java.util.LinkedList;

/**
 * User: Sina
 * Date: 11/8/13
 */
public class BettingRound {
    private HandSetting handSetting;
    //Iterator over players showing who's turn it is
    private TurnIterator turnIterator;
    private LinkedList<Action> actions = new LinkedList<Action>();
    private Action lastBoardRaise = null;

    public BettingRound(HandSetting handSetting) {
        this.handSetting = handSetting;
        turnIterator = new TurnIterator(handSetting.getGameSetting(), handSetting.getSmallBlindIndex());
    }

    public boolean finished() {
        if (turnIterator.getRemainingCount() == 0)
            return true;
        if (lastBoardRaise == null)
            return turnIterator.getRemainingCount() == 1;
        for (Action action : actions) {
            if (action.getPlayer() == turnIterator.current()) {
                return lastBoardRaise.getBet() == action.getBet();
            }
        }
        return false;
    }

    public Action nextBet() {
        Player player = turnIterator.current();
        double bet = player.giveYourBet();
        Action previousAction = findLastAction(player);
        Action newAction = new Action(bet, previousAction, lastBoardRaise, player, handSetting.getGameSetting().getStackSize(player));
        actions.remove(previousAction);
        actions.add(newAction);
        if (newAction.isFold() || newAction.isAllIn()) {
            turnIterator.removeCurrent();
        }
        if (!newAction.isFold() && (lastBoardRaise == null || newAction.isRaise())) {
            lastBoardRaise = newAction;
        }
        turnIterator.moveTurn();
        return newAction;
    }

    private Action findLastAction(Player player) {
        for (Action action : actions) {
            if (action.getPlayer() == player) {
                return action;
            }
        }
        return null;
    }

    public LinkedList<Action> getActions() {
        return actions;
    }

    public Action getLastBoardRaise() {
        return lastBoardRaise;
    }

    private double getLowestAllInAmount(double lowerBound) {
        double lowestAllInAmount = -1;
        for (Action action : actions) {
            if (action.isAllIn() && action.getBet() > lowerBound && (lowestAllInAmount == -1 || action.getBet() < lowestAllInAmount)) {
                lowestAllInAmount = action.getBet();
            }
        }
        return lowestAllInAmount;
    }

    public void terminate() {
        // If there is any allin, create side pots
        double prevAllInAmount = 0;
        double allInAmount = getLowestAllInAmount(prevAllInAmount);
        Pot pot = handSetting.getPot();
        while (allInAmount != -1) {
            Pot sidePot = new Pot();
            for (Action action : actions) {
                if (action.getBet() > prevAllInAmount) {
                    sidePot.increase(Math.min(allInAmount, action.getBet()) - prevAllInAmount);
                }
                if (action.isAllIn() && action.getBet() == allInAmount) {
                    sidePot.attach(action.getPlayer());
                }
            }
            pot.add(sidePot);
            prevAllInAmount = allInAmount;
            allInAmount = getLowestAllInAmount(prevAllInAmount);
        }
        // remaining amounts are added to the main pot
        pot.clearPlayers();
        for (Action action : actions) {
            if (action.getBet() >= prevAllInAmount) {
                if (action.getBet() > prevAllInAmount) {
                    pot.increase(action.getBet() - prevAllInAmount);
                }
                if (!action.isFold() && !action.isAllIn()) {
                    pot.attach(action.getPlayer());
                }
            }
        }
    }
}
