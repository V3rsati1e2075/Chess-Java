package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class King extends Piece {
    public King(Pos p, char c) {
        super(p, c);
        this.type = 6;
        this.value = 10000;
        if (c == 'b') {
            this.value *= -1;
            this.fen_char = 'k';
            this.image_file = "src/PieceImages/bk.png" ;
        } else {
            this.fen_char = 'K';
            this.image_file = "src/PieceImages/wk.png" ;
        }
        this.image = new ImageIcon(image_file, String.valueOf(this.type) + this.colour);
    }

    public ArrayList<Pos> get_moves(Piece[][] board) {
        ArrayList<Pos> moves = new ArrayList<Pos>();
        int x = this.position.x;
        int y = this.position.y;

        // Regular moves

        if (x + 1 < 8) { // down
            if (board[x + 1][y].colour != this.colour) {
                moves.add(new Pos(x + 1, y));
            }
        }

        if (x + 1 < 8 && y - 1 >= 0) { // down and left
            if (board[x + 1][y - 1].colour != this.colour) {
                moves.add(new Pos(x + 1, y - 1));
            }
        }

        if (y - 1 >= 0) { // left
            if (board[x][y - 1].colour != this.colour) {
                moves.add(new Pos(x, y - 1));
            }
        }

        if (x - 1 >= 0 && y - 1 >= 0) { // up and left
            if (board[x - 1][y - 1].colour != this.colour) {
                moves.add(new Pos(x - 1, y - 1));
            }
        }

        if (x - 1 >= 0) { // up
            if (board[x - 1][y].colour != this.colour) {
                moves.add(new Pos(x - 1, y));
            }
        }

        if (x - 1 >= 0 && y + 1 < 8) { // up and right
            if (board[x - 1][y + 1].colour != this.colour) {
                moves.add(new Pos(x - 1, y + 1));
            }
        }

        if (y + 1 < 8) { // right
            if (board[x][y + 1].colour != this.colour) {
                moves.add(new Pos(x, y + 1));
            }
        }

        if (x + 1 < 8 && y + 1 < 8) { // right and down
            if (board[x + 1][y + 1].colour != this.colour) {
                moves.add(new Pos(x + 1, y + 1));
            }
        }

        // castle maneuver

        boolean checkq = true;
        if (this.colour == 'b') {
            for (int i = 1; i < 4; i++) {
                if (board[0][i].colour == ' ') { // Queen Side castle (black)
                    continue;
                } else {
                    checkq = false ;
                    break;
                }
            }
            if (checkq) {
                if (this.hasMoved == false && board[0][0].hasMoved == false) {
                    moves.add(new Pos(0, 2));
                }
            }

            boolean checkk = true ; // King side castle
            for (int i = 5; i < 7; i++) {
                if (board[0][i].colour == ' ') {
                    continue;
                } else {
                    checkk = false ;
                    break;
                }
            }

            if (checkk) {
                if (this.hasMoved == false && board[0][7].hasMoved == false) {
                    moves.add(new Pos(0, 6));
                }
            }
        }

        checkq = true;
        if (this.colour == 'w') {
            for (int i = 1; i < 4; i++) {
                if (board[7][i].colour == ' ') { // Queen Side castle (white)
                    continue;
                } else {
                    checkq = false ;
                    break;
                }
            }
            if (checkq) {
                if (this.hasMoved == false && board[7][0].hasMoved == false) {
                    moves.add(new Pos(7, 2));
                }
            }

            boolean checkk = true ; // King side castle
            for (int i = 5; i < 7; i++) {
                if (board[7][i].colour == ' ') {
                    continue;
                } else {
                    checkk = false;
                    break;
                }
            }

            if (checkk) {
                if (this.hasMoved == false && board[7][7].hasMoved == false) {
                    moves.add(new Pos(7, 6));
                }
            }
        }
        return moves;
    }
}