package Pieces;

import javax.swing.*;
import java.util.ArrayList;

public class Piece {
    public Pos position; // [0 - 7][0 - 7]
    protected int type; // 0,1,2,3,4,5,6 blank, pawn, bishop, knight, rook, queen, king
    public char colour; // 'b' or 'w' or ' ' for blank space
    public boolean hasMoved; // has the piece moved from it's starting square
    protected int value;
    public char fen_char;
    public String image_file ;
    public ImageIcon image;
    public Piece() {
        this.colour = ' ';
        this.hasMoved = false;
        this.type = 0;
        this.value = 0;

    }

    public Piece(Pos p) {
        this.position = p;
        this.colour = ' ';
        this.hasMoved = false;
        this.type = 0;
        this.value = 0;
        this.image = new ImageIcon("", "Blank");
    }

    public Piece(Pos p, char c) {
        this.position = p;
        this.colour = c;
        this.hasMoved = false;

    }

    public ArrayList<Pos> get_moves(Piece[][] board) { // all valid moves for the piece
        return new ArrayList<Pos>();
    }

    public int get_type() {
        return this.type;
    }

    public int get_value() {
        return this.value;
    }

    public void move_piece(Pos p) { // move the piece
        this.hasMoved = true;
        this.position.x = p.x;
        this.position.y = p.y;
    }
}