package tictactoe;

public class Coordinates {
    public final int x;
    public final int y;

    Coordinates(int x, int y) throws OutBoundsMoveException {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new OutBoundsMoveException();
        }
        this.x = x;
        this.y = y;
    }
}
