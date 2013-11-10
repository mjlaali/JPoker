package ui.shapes;

/**
 * User: Sina
 * Date: 11/9/13
 */
public class Rectangle {
    public Position topLeft;
    public int width;
    public int height;

    public Rectangle(Position topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    public Position topLeft(int dx, int dy) {
        int x = topLeft.x - dx;
        int y = topLeft.y - dy;
        return new Position(x, y);
    }

    public Position topRight(int dx, int dy) {
        int x = topLeft.x + width + dx;
        int y = topLeft.y - dy;
        return new Position(x, y);
    }

    public Position bottomRight(int dx, int dy) {
        int x = topLeft.x + width + dx;
        int y = topLeft.y + height + dy;
        return new Position(x, y);
    }

    public Position bottomLeft(int dx, int dy) {
        int x = topLeft.x - dx;
        int y = topLeft.y + height + dy;
        return new Position(x, y);
    }
}
