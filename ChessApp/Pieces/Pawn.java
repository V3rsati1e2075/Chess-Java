package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Pawn extends Piece {
    public Pawn(Pos p, char c) {
        super(p, c);
        this.type = 1;
        this.value = 1;
        if (c == 'b') {
            this.value *= -1;
            this.fen_char = 'p';
            this.image_file = "src/PieceImages/bp.png" ;
        } else {
            this.fen_char = 'P';
            this.image_file = "src/PieceImages/wp.png" ;
        }
        this.image = new ImageIcon(image_file, String.valueOf(this.type) + this.colour);
    }

    public ArrayList<Pos> get_moves(Piece[][] board) {
        ArrayList<Pos> moves = new ArrayList<Pos>();
        int x = this.position.x;
        int y = this.position.y;

        // check for 2 steps forward
        if (this.hasMoved == false) {
            if (this.colour == 'b') {
                if (board[3][y].colour == ' ' && board[2][y].colour == ' ') {
                    moves.add(new Pos(3, y));
                }
            } else {
                if (board[4][y].colour == ' ' && board[5][y].colour == ' ') {
                    moves.add(new Pos(4, y));
                }
            }
        }

        if (this.colour == 'b') {
            if (board[x + 1][y].colour == ' ') {
                moves.add(new Pos(x + 1, y));
            }
        } else {
            if (board[x - 1][y].colour == ' ') {
                moves.add(new Pos(x - 1, y));
            }
        }


        // check for captures

        if (this.colour == 'b') {
            if (y - 1 >= 0  && x + 1 <= 7) {
                if (board[x + 1][y - 1].colour == 'w') { // left capture (Black)
                    moves.add(new Pos(x + 1, y - 1));
                }
            }
            if (y + 1 < 8 && x + 1 < 8){
                if (board[x + 1][y + 1].colour == 'w') { // right capture (Black)
                    moves.add(new Pos(x + 1, y + 1));
                }
            }
        } else {
            if ((y - 1 >= 0 ) && x - 1 >= 0) {
                if (board[x - 1][y - 1].colour == 'b') { // left capture (White)
                    moves.add(new Pos(x - 1, y - 1));
                }
            }
            if (y + 1 < 8 && x - 1 >= 0){
                if (board[x - 1][y + 1].colour == 'b') { // right capture (White)
                    moves.add(new Pos(x - 1, y + 1));
                }
            }
        }

        // En Passant

        if (this.colour == 'b') {
            if ((y - 1 >= 0 && y + 1 < 8)) { // Right (Black)
                if (board[x][y + 1].colour == 'w') {
                    if (x == 4) {
                        moves.add(new Pos(x + 1, y + 1));
                    }
                }

                if (board[x][y - 1].colour == 'w') { // Left (Black)
                    if (x == 4) {
                        moves.add(new Pos(x + 1, y - 1));
                    }
                }
            }
        } else {
            if ((y - 1 >= 0 && y + 1 < 8)) { // Right (White)
                if (board[x][y + 1].colour == 'b') {
                    if (x == 3) {
                        moves.add(new Pos(x - 1, y + 1));
                    }
                }

                if (board[x][y - 1].colour == 'b') { // Left (White)
                    if (x == 3) {
                        moves.add(new Pos(x - 1, y - 1));
                    }
                }
            }
        }

        return moves;
    }
}