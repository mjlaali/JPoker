package ui.shapes;

/**
 * User: Sina
 * Date: 11/9/13
 */
public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position distance(int dx, int dy) {
        return new Position(x + dx, y + dy);
    }
}
