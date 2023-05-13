package AI;

import java.util.* ;

import Pieces.Piece;
import Pieces.Pos;

import Board.Board;
import Board.Move;


public class TestAI {

    public static void main(String[] args) {
        Board game_board = new Board(), current_board;

        game_board.perform_move(new Pos(6, 4), new Pos(4, 4));
        char colour = 'b';

        ArrayList<Move> moves = game_board.get_moves(colour);
        Move best_move = new Move(new Pos(-1, -1), new Pos(-1, -1));
        double best_val;
        if (colour == 'w') {
            best_val = -10000;
        }
        else {
            best_val = 10000;
        }
        for (Move move : moves) {
            current_board = game_board.copy();

            if (current_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) {
                current_board.perform_move(move.start, move.end, 5);
            }
            else if (current_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) {
                current_board.castle(move.start, move.end);
            }
            else {
                current_board.perform_move(move.start, move.end);
            }


            double eval = evaluate_board(current_board.copy(), colour, 1);
            if (colour == 'w' && eval > best_val) {
                best_val = eval;
                best_move = move;
            }
            else if (colour == 'b' && eval < best_val){
                best_val = eval;
                best_move = move;
            }

        }
        current_board = game_board.copy();

        System.out.println(best_val);
        System.out.println(best_move.start);
        System.out.println(best_move.end);
        current_board.print();
    }

    static double evaluate_board(Board game_board, char colour, int depth) {
        Board current_board;
        double best_val;
        ArrayList<Move> moves = game_board.get_moves(colour);

        if (colour == 'w') {
            best_val = -10000;
        }
        else {
            best_val = 10000;
        }
        double eval;
        for (Move move : moves) {
            current_board = game_board.copy();

            if (current_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) {
                current_board.perform_move(move.start, move.end, 5);
            }
            else if (current_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) {
                current_board.castle(move.start, move.end);
            }
            else {
                current_board.perform_move(move.start, move.end);
            }

            if (depth == 3) {
                System.out.println("----------------");
                eval = evaluation_function(current_board.board_arr);
                current_board.print();
                System.out.println(eval);
            }
            else {
                if (colour == 'w') {
                    eval = evaluate_board(current_board.copy(), 'b', depth + 1);
                }
                else {
                    eval = evaluate_board(current_board.copy(), 'w', depth + 1);
                }
            }


            if (colour == 'w' && eval > best_val) {
                best_val = eval;
            }
            else if (colour == 'b' && eval < best_val){
                best_val = eval;
            }
        }

        return best_val;
    }

    public static double evaluation_function(Piece[][] board){

        double atk_multiplier = 0.2;
        double def_multiplier = 0.6;
        double blank_sq_reward = 0.3;


        double value = 0;
        int colour_multiplier = 1;
        for (int i = 0 ; i < 8 ; i ++){
            for (int j = 0 ; j < 8 ; j++){
                if (board[i][j].colour == ' ') {
                    continue;
                }
                ArrayList <Pos> m = board[i][j].get_moves(board) ;

                if (board[i][j].get_type() == 6) {
                    if (board[i][j].colour == 'w') {
                        colour_multiplier = -1;
                    }
                    else {
                        colour_multiplier = 1;
                    }

                    for (int k = 0 ; k < m.size() ; k++) {
                        if (board[m.get(k).x][m.get(k).y].colour == ' ') {
                            value += colour_multiplier * blank_sq_reward;
                        }
                    }
                    value += board[i][j].get_value();
                    continue;
                }


                if (board[i][j].colour == 'w') {
                    colour_multiplier = 1;
                }
                else {
                    colour_multiplier = -1;
                }
                for (int k = 0 ; k < m.size() ; k++){
                    if (board[m.get(k).x][m.get(k).y].get_type() == 6) {
                        value +=  board[m.get(k).x][m.get(k).y].get_value();
                        continue;
                    }
                    if (board[m.get(k).x][m.get(k).y].colour == ' ' ){
                        value += colour_multiplier * blank_sq_reward ;
                    }
                    else if (board[m.get(k).x][m.get(k).y].colour != board[i][j].colour){
                        value += atk_multiplier * board[m.get(k).x][m.get(k).y].get_value()  ;
                    }
                    else {
                        value += def_multiplier * board[m.get(k).x][m.get(k).y].get_value()  ;
                    }
                }

                value += board[i][j].get_value();
            }
        }
        return value ;
    }

}
