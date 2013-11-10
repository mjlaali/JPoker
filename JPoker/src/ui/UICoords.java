package ui;

import ui.panels.PlayerPanel;
import ui.shapes.Position;

/**
 * User: Sina
 * Date: Mar 5, 2012
 */
public class UICoords {
    static int boardWidth = 794, boardHeight = 570;

    static Position[] playerPositions = new Position[8];

    static int flopDx = 3;
    static int flop1X = 268, flop1Y = 221;
    static int flop2X = flop1X + PlayerPanel.cardWidth + flopDx, flop2Y = flop1Y;
    static int flop3X = flop2X + PlayerPanel.cardWidth + flopDx, flop3Y = flop2Y;
    static int turnX = flop3X + PlayerPanel.cardWidth + flopDx, turnY = flop3Y;
    static int riverX = turnX + PlayerPanel.cardWidth + flopDx, riverY = turnY;

    static {
        playerPositions[0] = new Position(12, 310);
        playerPositions[1] = new Position(12, 130);
        playerPositions[2] = new Position(232, 45);
        playerPositions[3] = new Position(483, 45);
        playerPositions[4] = new Position(708, 130);
        playerPositions[5] = new Position(708, 310);
        playerPositions[6] = new Position(483, 420);
        playerPositions[7] = new Position(232, 420);
    }
}
