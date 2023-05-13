package Pieces;

public class Pos {
    public int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pos(Pos p) {
        this.x = p.x;
        this.y = p.y;
    }

    public String toString() {
        return "(" + String.valueOf(this.x) + ", " + String.valueOf(this.y) + ")";
    }

    public boolean equals(Pos other) {
        if (other.x == this.x && other.y == this.y) {
            return true;
        }
        else {
            return false;
        }
    }
}