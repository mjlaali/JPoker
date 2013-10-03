package poker;

import players.Player;

public class DefaultNotifiable implements Notifiable{

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
	public void playerFolded(Player foldingPlayer) {
	}

	@Override
	public void playerBet(Player bettingPlayer, double bet) {
	}

	@Override
	public void showDown(ShowDown showDown) {
	}

	@Override
	public void gameEnds() {
	}

}
