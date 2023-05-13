package Board;

import Pieces.Pos;

public class Move {
    public Pos start, end;

    public Move(Pos s, Pos e) {
        this.start = s;
        this.end = e;
    }
}
