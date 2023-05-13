package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Pos p, char c){
        super(p, c);
        this.type = 2;
        this.value = 3;
        if (c == 'b') {
            this.value *= -1;
            this.fen_char = 'b';
            this.image_file = "src/PieceImages/bb.png" ;

        } else {
            this.fen_char = 'B';
            this.image_file = "src/PieceImages/wb.png" ;
        }

        this.image = new ImageIcon(image_file, String.valueOf(this.type) + this.colour);
    }

    public ArrayList<Pos> get_moves(Piece[][] board) {
        ArrayList<Pos> moves = new ArrayList<Pos>();

        // Lower Right
        int i = this.position.x + 1;
        int j = this.position.y + 1;
        while (i < 8 && j < 8) {
            if (board[i][j].type == 0) {
                moves.add(new Pos(i, j));
            } else if (board[i][j].colour != this.colour) {
                moves.add(new Pos(i, j));
                break;
            } else {
                break;
            }
            i++;
            j++;
        }

        // Lower Left
        i = this.position.x + 1;
        j = this.position.y - 1;
        while (i < 8 && j >= 0) {
            if (board[i][j].type == 0) {
                moves.add(new Pos(i, j));
            } else if (board[i][j].colour != this.colour) {
                moves.add(new Pos(i, j));
                break;
            } else {
                break;
            }
            i++;
            j--;
        }


        // Upper Right
        i = this.position.x - 1;
        j = this.position.y + 1;
        while (i >= 0 && j < 8) {
                if (board[i][j].type == 0) {
                    moves.add(new Pos(i, j));
                } else if (board[i][j].colour != this.colour) {
                    moves.add(new Pos(i, j));
                    break;
                } else {
                    break;
                }
                i--;
                j++;
        }

        // Upper Left
        i = this.position.x - 1;
        j = this.position.y - 1;
        while (i >= 0 && j >= 0) {
                if (board[i][j].type == 0) {
                    moves.add(new Pos(i, j));
                } else if (board[i][j].colour != this.colour) {
                    moves.add(new Pos(i, j));
                    break;
                } else {
                    break;
                }
                i--;
                j--;
        }

        return moves;
    }
}