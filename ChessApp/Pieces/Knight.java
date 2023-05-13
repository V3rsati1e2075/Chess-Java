package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Pos p, char c) {
        super(p, c);
        this.type = 3;
        this.value = 3;
        if (c == 'b') {
            this.value *= -1;
            this.fen_char = 'n';
            this.image_file = "src/PieceImages/bn.png" ;
        } else {
            this.fen_char = 'N';
            this.image_file = "src/PieceImages/wn.png" ;
        }
        this.image = new ImageIcon(image_file, String.valueOf(this.type) + this.colour);
    }

    public ArrayList<Pos> get_moves(Piece[][] board) {
        ArrayList<Pos> moves = new ArrayList<Pos>();

        int x = this.position.x, y = this.position.y;

        // +2 on x axis
        if ((x + 2) < 8) {
            // +1 on y axis
            if ((y + 1) < 8) {
                if (board[x + 2][y + 1].colour != this.colour) {
                    moves.add(new Pos(x + 2, y + 1));
                }
            }
            // -1 on y axis
            if ((y - 1) >= 0) {
                if (board[x + 2][y - 1].colour != this.colour) {
                    moves.add(new Pos(x + 2, y - 1));
                }
            }
        }

        // -2 on x axis
        if ((x - 2) >= 0) {
            // +1 on y axis
            if ((y + 1) < 8) {
                if (board[x - 2][y + 1].colour != this.colour) {
                    moves.add(new Pos(x - 2, y + 1));
                }
            }
            // -1 on y axis
            if ((y - 1) >= 0) {
                if (board[x - 2][y - 1].colour != this.colour) {
                    moves.add(new Pos(x - 2, y - 1));
                }
            }
        }

        // +1 on x axis
        if ((x + 1) < 8) {
            // +2 on y axis
            if ((y + 2) < 8) {
                if (board[x + 1][y + 2].colour != this.colour) {
                    moves.add(new Pos(x + 1, y + 2));
                }
            }
            // -2 on y axis
            if ((y - 2) >= 0) {
                if (board[x + 1][y - 2].colour != this.colour) {
                    moves.add(new Pos(x + 1, y - 2));
                }
            }
        }

        // -1 on x axis
        if ((x - 1) >= 0) {
            // +2 on y axis
            if ((y + 2) < 8) {
                if (board[x - 1][y + 2].colour != this.colour) {
                    moves.add(new Pos(x - 1, y + 2));
                }
            }
            // -2 on y axis
            if ((y - 2) >= 0) {
                if (board[x - 1][y - 2].colour != this.colour) {
                    moves.add(new Pos(x - 1, y - 2));
                }
            }
        }

        return moves;
    }
}