package ui;

/**
 * Created by IntelliJ IDEA.
 * User: Sina
 * Date: Mar 5, 2012
 * Time: 2:23:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class UICoords {
    static int boardWidth = 794, boardHeight = 570;
    static int cardWidth = 48, cardHeight = 68;
    static int secondCardDx = 15, secondCardDy = 6;

    static int[] playerX1s = new int[8];
    static int[] playerY1s = new int[8];
    static int[] playerX2s = new int[8];
    static int[] playerY2s = new int[8];

    static int flopDx = 3;
    static int flop1X = 268, flop1Y = 221;
    static int flop2X = flop1X + cardWidth + flopDx, flop2Y = flop1Y;
    static int flop3X = flop2X + cardWidth + flopDx, flop3Y = flop2Y;
    static int turnX = flop3X + cardWidth + flopDx, turnY = flop3Y;
    static int riverX = turnX + cardWidth + flopDx, riverY = turnY;

    static int[] playerNameLabelX = new int[8];
    static int[] playerNameLabelY = new int[8];
    static int[] playerStackLabelX = new int[8];
    static int[] playerStackLabelY = new int[8];
    static int playerLabelWidth = cardWidth + secondCardDx, playerLabelHeight = 20;
    static int playerLabelDx = 0, playerNameLabelDy = 1;

    static {
        playerX1s[0] = 12; playerY1s[0] = 310;
        playerX1s[1] = 12; playerY1s[1] = 130;
        playerX1s[2] = 232; playerY1s[2] = 45;
        playerX1s[3] = 483; playerY1s[3] = 45;
        playerX1s[4] = 708; playerY1s[4] = 130;
        playerX1s[5] = 708; playerY1s[5] = 310;
        playerX1s[6] = 483; playerY1s[6] = 400;
        playerX1s[7] = 232; playerY1s[7] = 400;

        for (int i = 0; i < playerX2s.length; i++) {
            playerX2s[i] = playerX1s[i] + secondCardDx;
            playerY2s[i] = playerY1s[i] + secondCardDy;
        }

        for (int i = 0; i < playerNameLabelX.length; i++) {
            playerNameLabelX[i] = playerX1s[i] + playerLabelDx;
            playerStackLabelX[i] = playerNameLabelX[i];
            if (playerY1s[i] < boardHeight / 2) {
                playerNameLabelY[i] = playerY1s[i] - playerNameLabelDy - playerLabelHeight;
                playerStackLabelY[i] = playerNameLabelY[i] - playerLabelHeight;
            } else {
                playerNameLabelY[i] = playerY2s[i] + cardHeight + playerNameLabelDy;
                playerStackLabelY[i] = playerNameLabelY[i] + playerLabelHeight;
            }
        }
    }
}
