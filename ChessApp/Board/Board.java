package Board;

import java.util.*;

import Pieces.Pos;
import Pieces.Piece;
import Pieces.Rook;
import Pieces.Bishop;
import Pieces.Queen;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.King;


public class Board {
    public Piece[][] board_arr;
    private Pos black_king_pos, white_king_pos;
    public char turn;

    public boolean isEnPassantCapture;
    public Board() {
        turn = 'w';
        this.board_arr = new Piece[8][8];

        this.black_king_pos = new Pos(0, 4);
        this.white_king_pos = new Pos(7, 4);
        this.board_arr[0][0] = new Rook(new Pos(0, 0), 'b');
        this.board_arr[0][1] = new Knight(new Pos(0, 1), 'b');
        this.board_arr[0][2] = new Bishop(new Pos(0, 2), 'b');
        this.board_arr[0][3] = new Queen(new Pos(0, 3), 'b');
        this.board_arr[0][4] = new King(new Pos(0, 4), 'b');
        this.board_arr[0][5] = new Bishop(new Pos(0, 5), 'b');
        this.board_arr[0][6] = new Knight(new Pos(0, 6), 'b');
        this.board_arr[0][7] = new Rook(new Pos(0, 7), 'b');
        for (int i = 0; i < 8; i++) {
            this.board_arr[1][i] = new Pawn(new Pos(1, i), 'b');
        }

        for (int i = 2; i < 6; i++) {

            for (int j = 0; j < 8; j++) {
                this.board_arr[i][j] = new Piece(new Pos(i, j));
            }

        }


        this.board_arr[7][0] = new Rook(new Pos(7, 0), 'w');
        this.board_arr[7][1] = new Knight(new Pos(7, 1), 'w');
        this.board_arr[7][2] = new Bishop(new Pos(7, 2), 'w');
        this.board_arr[7][3] = new Queen(new Pos(7, 3), 'w');
        this.board_arr[7][4] = new King(new Pos(7, 4), 'w');
        this.board_arr[7][5] = new Bishop(new Pos(7, 5), 'w');
        this.board_arr[7][6] = new Knight(new Pos(7, 6), 'w');
        this.board_arr[7][7] = new Rook(new Pos(7, 7), 'w');
        for (int i = 0; i < 8; i++) {
            this.board_arr[6][i] = new Pawn(new Pos(6, i), 'w');
        }

        this.isEnPassantCapture = false;
    }

    public Board(Pos p1, Pos p2) {
        this();
        this.white_king_pos = p1;
        this.black_king_pos = p2;
    }



    public void perform_move(Pos start, Pos end) {


        if (this.board_arr[start.x][start.y].colour == 'w') {
            // En passant
            if (this.board_arr[start.x][start.y].get_value() == 1) {
                if (this.board_arr[end.x][end.y].colour == ' ' && (end.y == start.y - 1 || end.y == start.y + 1)) {
                    board_arr[start.x][end.y] = new Piece(new Pos(start.x, end.y));
                    this.isEnPassantCapture = true;
                }
            }
        }
        else if (this.board_arr[start.x][start.y].colour == 'b'){
            if (this.board_arr[start.x][start.y].get_value() == 1) {
                // En passant
                if (this.board_arr[end.x][end.y].colour == ' ' && (end.y == start.y - 1 || end.y == start.y + 1)) {
                    board_arr[start.x][end.y] = new Piece(new Pos(start.x, end.y));
                    this.isEnPassantCapture = true;
                }
            }
        }


        // Actually moving the piece on the board
        this.board_arr[end.x][end.y] = this.board_arr[start.x][start.y];
        this.board_arr[end.x][end.y].move_piece(end);

        this.board_arr[start.x][start.y] = new Piece(start);

        if (this.board_arr[end.x][end.y].get_type() == 6) {
            if (this.board_arr[end.x][end.y].colour == 'w') {
                this.white_king_pos = new Pos(end);
            }
            else {
                this.black_king_pos = new Pos(end);
            }
        }

        if (this.turn == 'w') {
            this.turn = 'b';
        }
        else {
            this.turn = 'w';
        }
    }

    public void castle(Pos start, Pos end) {
        this.board_arr[end.x][end.y] = this.board_arr[start.x][start.y];
        this.board_arr[end.x][end.y].move_piece(end);
        this.board_arr[start.x][start.y] = new Piece(start);
            if (start.y - end.y == 2) { // castling queen-side
                this.board_arr[end.x][3] = this.board_arr[end.x][0];
                this.board_arr[end.x][3].move_piece(new Pos(end.x, 3));




                this.board_arr[start.x][0] = new Piece(start);
            }
            else if (start.y - end.y == -2) { // castling king-side

                this.board_arr[end.x][5] = this.board_arr[end.x][7];
                this.board_arr[end.x][5].move_piece(new Pos(end.x, 5));
                this.board_arr[start.x][7] = new Piece(start);
            }

            if (this.board_arr[end.x][end.y].colour == 'w') {
                this.white_king_pos = new Pos(end);
            }
            else {
                this.black_king_pos = new Pos(end);
            }


            if (this.turn == 'w') {
                this.turn = 'b';
            }
            else {
                this.turn = 'w';
            }
    }

    public void perform_move(Pos start, Pos end, int promo_choice) { // only for promotion
        // Actually moving the piece on the board
        this.board_arr[start.x][start.y] = new Piece(start);



        if (this.turn == 'w') {
            int p = promo_choice;
            if (p == 2) {
                board_arr[end.x][end.y] = new Bishop(new Pos(end.x, end.y), 'w');
            } else if (p == 3) {
                board_arr[end.x][end.y] = new Knight(new Pos(end.x, end.y), 'w');
            } else if (p == 1) {
                board_arr[end.x][end.y] = new Rook(new Pos(end.x, end.y), 'w');
            } else {
                board_arr[end.x][end.y] = new Queen(new Pos(end.x, end.y), 'w');
            }
        } else {
            int q = promo_choice;
            if (q == 2) {
                board_arr[end.x][end.y] = new Bishop(new Pos(end.x, end.y), 'b');
            } else if (q == 3) {
                board_arr[end.x][end.y] = new Knight(new Pos(end.x, end.y), 'b');
            } else if (q == 1) {
                board_arr[end.x][end.y] = new Rook(new Pos(end.x, end.y), 'b');
            } else {
                board_arr[end.x][end.y] = new Queen(new Pos(end.x, end.y), 'b');
            }

        }
        if (this.turn == 'w') {
            this.turn = 'b';
        }
        else {
            this.turn = 'w';
        }
    }

    public ArrayList<Move> get_moves(char turn) {
        ArrayList<Move> validmoves = new ArrayList<Move>();



        ArrayList<Pos> positions; // all valid positions we can move to
        Pos start, end;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board_arr[i][j].colour == turn) {
                    positions = this.board_arr[i][j].get_moves(this.board_arr);
                    for (int pos_num = 0; pos_num < positions.size(); pos_num++) {
                        start = board_arr[i][j].position;
                        end = new Pos(positions.get(pos_num));

                        validmoves.add(new Move(board_arr[i][j].position, end));
                    }
                }
            }
        }
        return validmoves;
    }

    // Check if king is in check at a given position
    public boolean isCheck(Piece[][] board, char turn) {
        Pos position;
        if (turn == 'w') {
            position = this.white_king_pos;
        }
        else {
            position = this.black_king_pos;
        }

        ArrayList<Pos> positions; // all valid positions we can move to
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board_arr[i][j].colour != turn && this.board_arr[i][j].get_type() != 0) {
                    positions = this.board_arr[i][j].get_moves(this.board_arr);

                    for (Pos possible_pos : positions) {
                        if (position.equals(possible_pos)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }



    public String get_fen() {
        String fen = "";
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board_arr[i][j].get_type() == 0) { // blank space
                    count++;
                } else {
                    if (count != 0) {
                        fen += String.valueOf(count);
                        count = 0;
                    }

                    fen += board_arr[i][j].fen_char;
                }
            }

            if (count != 0) {
                fen += String.valueOf(count);
            }
            count = 0;
            if (i != 7) {
                fen += "/";
            }
        }
        return fen;
    }

    public Board copy() {
        Board new_board = new Board(this.white_king_pos, this.black_king_pos);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (this.board_arr[i][j].get_type()) {
                    case 0:
                        new_board.board_arr[i][j] = new Piece();
                        break;
                    case 1:
                        new_board.board_arr[i][j] = new Pawn(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                    case 2:
                        new_board.board_arr[i][j] = new Bishop(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                    case 3:
                        new_board.board_arr[i][j] = new Knight(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                    case 4:
                        new_board.board_arr[i][j] = new Rook(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                    case 5:
                        new_board.board_arr[i][j] = new Queen(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                    case 6:
                        new_board.board_arr[i][j] = new King(new Pos(i, j), this.board_arr[i][j].colour);
                        break;
                }
            }
        }

        return new_board;
    }

    public void print() {
        System.out.println("----------------------------");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(this.board_arr[i][j].fen_char);
                System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println("\n\n");
    }
}