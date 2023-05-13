package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(Pos p, char c) {
        super(p, c);
        this.type = 4;
        this.value = 5;
        if (c == 'b') {
            this.value *= -1;
            this.fen_char = 'r';
            this.image_file = "src/PieceImages/br.png" ;
        } else {
            this.fen_char = 'R';
            this.image_file = "src/PieceImages/wr.png" ;
        }
        this.image = new ImageIcon(image_file, String.valueOf(this.type) + this.colour);
    }

    public ArrayList<Pos> get_moves(Piece[][] board) {
        ArrayList<Pos> moves = new ArrayList<Pos>();

        // Downwards
        for (int i = this.position.x + 1; i < 8; i++) {
            if (board[i][this.position.y].type == 0) {
                moves.add(new Pos(i, this.position.y));
            } else if (board[i][this.position.y].colour != this.colour) {
                moves.add(new Pos(i, this.position.y));
                break;
            } else {
                break;
            }
        }

        // Upwards
        for (int i = this.position.x - 1; i >= 0; i--) {
            if (board[i][this.position.y].type == 0) {
                moves.add(new Pos(i, this.position.y));
            } else if (board[i][this.position.y].colour != this.colour) {
                moves.add(new Pos(i, this.position.y));
                break;
            } else {
                break;
            }
        }

        // Right hand-side movement
        for (int j = this.position.y + 1; j < 8; j++) {
            if (board[this.position.x][j].type == 0) {
                moves.add(new Pos(this.position.x, j));
            } else if (board[this.position.x][j].colour != this.colour) {
                moves.add(new Pos(this.position.x, j));
                break;
            } else {
                break;
            }
        }

        // Left hand-side movement
        for (int j = this.position.y - 1; j >= 0; j--) {
            if (board[this.position.x][j].type == 0) {
                moves.add(new Pos(this.position.x, j));
            } else if (board[this.position.x][j].colour != this.colour) {
                moves.add(new Pos(this.position.x, j));
                break;
            } else {
                break;
            }
        }

        return moves;
    }
}