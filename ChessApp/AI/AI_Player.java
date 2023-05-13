package AI;

import java.util.* ;

import Pieces.Piece;
import Pieces.Pos;

import Board.Board;
import Board.Move;

import javax.lang.model.type.NullType;
import javax.swing.plaf.synth.SynthOptionPaneUI;


class MultithreadingSearch implements Runnable {
    private double score;
    private int thinking_depth;
    private char start_colour;
    private Board game_board;
    private double atk_multiplier ;
    private double def_multiplier ;
    private double blank_sq_reward ;

    private boolean isFirstLayer;

    static int MAX = 1000000000;
    static int MIN = -1000000000;

    MultithreadingSearch(int depth, char c, Board game_board) {
        this.thinking_depth = depth;
        this.start_colour = c;
        this.game_board = game_board;

        this.atk_multiplier = 0.02;
        this.def_multiplier = 0.06;
        this.blank_sq_reward = 0.03;
        this.isFirstLayer = false;
    }

    MultithreadingSearch(int depth, char c, Board game_board, boolean isFirstLayer) {
        this.thinking_depth = depth;
        this.start_colour = c;
        this.game_board = game_board;

        this.atk_multiplier = 0.02;
        this.def_multiplier = 0.06;
        this.blank_sq_reward = 0.03;
        this.isFirstLayer = false;
    }
    public void run() {
        this.score = this.evaluate_board(this.game_board, this.start_colour, 1, MIN, MAX);
    }

    double get_evaluation() {
        return this.score;
    }

    private double evaluate_board(Board game_board, char turn, int depth, double alpha, double beta) {
        Board current_board;

        ArrayList<Move> moves = game_board.get_moves(turn);


        double eval = 0;
        ArrayList<Double> board_evals = new ArrayList<Double>();
        ArrayList<Thread > threads = new ArrayList<Thread >();
        ArrayList<MultithreadingSearch> thread_searches = new ArrayList<MultithreadingSearch>();
        Thread  thread;

        boolean isSet = false; // Has best_val been set

        double best_val = 0;

        for (Move move : moves) {
            current_board = game_board.copy();

            if (current_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) {
                current_board.perform_move(move.start, move.end, 5);
            }
            else if (current_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) {
                continue;
            }
            else {
                current_board.perform_move(move.start, move.end);
            }

            if (current_board.isCheck(current_board.board_arr, turn)) {
                continue;
            }


            if (this.isFirstLayer) { // Multithread the subtrees if the depth is one.
                thread_searches.add(new MultithreadingSearch(this.thinking_depth - 1, turn, current_board, false));
                thread = new Thread(thread_searches.get(thread_searches.size() - 1));
                threads.add(thread);
                thread.start();
            }
            else {
                if (depth == this.thinking_depth) {
                    eval = evaluation_function(current_board, turn);
                }
                else {
                    if (turn == 'w') {
                        eval = evaluate_board(current_board.copy(), 'b', depth + 1, alpha, beta);
                    }
                    else {
                        eval = evaluate_board(current_board.copy(), 'w', depth + 1, alpha, beta);
                    }
                }

                if (!isSet) {
                    best_val = eval;
                }

                if (turn == 'w') {
                    if (eval > best_val) {
                        best_val = eval;
                    }
                    if (alpha < best_val) {
                        alpha = best_val;
                    }
                }
                else {
                    if (eval < best_val) {
                        best_val = eval;
                    }
                    if (beta > best_val) {
                        beta = best_val;
                    }
                }


                if (beta <= alpha) {
                    return best_val;
                }

                board_evals.add(eval);
            }
        }

        if (!this.isFirstLayer) {
            return best_val;
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Nested tread exception.");
                throw new RuntimeException(e);
            }
        }

        for (MultithreadingSearch mul : thread_searches) {
            board_evals.add(mul.get_evaluation());
        }


        if (board_evals.size() == 0) {
            if (turn == 'w') {
                return -10000;
            }
            else {
                return 10000;
            }
        }

        best_val = board_evals.get(0);

        for (int i = 1; i < board_evals.size(); i++) {
            if (turn == 'w' && board_evals.get(i) > best_val) {
                best_val = board_evals.get(i);
            }
            else if (turn == 'b' && board_evals.get(i) < best_val){
                best_val = board_evals.get(i);
            }
        }


        return best_val;
    }

    private boolean isValidMove(Board new_board, Move move, char turn) {

        if (new_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) { //promotion
            new_board.perform_move(move.start, move.end, 5);
        }
        else if (new_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) { // casteling
            if (new_board.isCheck(new_board.board_arr, turn)) {
                return false;
            }
            try {
                new_board.castle(move.start, move.end);
            }
            finally {
                return false;
            }
        }
        else {
            new_board.perform_move(move.start, move.end);
        }


        if (new_board.isCheck(new_board.board_arr, turn)) {
            return false;
        }
        return true;
    }

    public double evaluation_function(Board board_obj, char turn){
        Piece[][]board = board_obj.board_arr;
        double value = 0;
        int colour_multiplier = 1;
        for (int i = 0 ; i < 8 ; i ++){
            for (int j = 0 ; j < 8 ; j++){
                if (board[i][j].colour == ' ') {
                    continue;
                }
                ArrayList <Pos> m = board[i][j].get_moves(board) ;

                if (board[i][j].get_type() == 6) {
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

        if (board_obj.isCheck(board, turn)) {
            if (turn == 'w') {
                value -= 100000;
            }
            else {
                value += 100000;
            }
        }
        return value ;
    }


}


public class AI_Player {

    public char colour;
    private int thinking_depth;

    public int promotion_choice;

    public AI_Player (char c, int depth) {
        this.colour = c;
        this.thinking_depth = depth - 1;
    }

    public Move minimax(Board game_board) {
        ArrayList<Move> moves = game_board.get_moves(this.colour);

        Board current_board;

        ArrayList<Thread > threads = new ArrayList<Thread >();
        ArrayList<MultithreadingSearch> thread_searches = new ArrayList<MultithreadingSearch>();
        Thread  thread;
        ArrayList<Move> valid_moves = new ArrayList<Move>();
        for (Move move : moves) {
            current_board = game_board.copy();


            if (! isValidMove(current_board.copy(), move, this.colour)) {
                continue;
            }

            if (current_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) {
                current_board.perform_move(move.start, move.end, 5);
            }
            else if (current_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) {
                continue;
            }
            else {
                current_board.perform_move(move.start, move.end);
            }

            //System.out.println("#############");
            //System.out.println(current_board.isCheck(current_board.board_arr, this.colour));
            //current_board.print();
            if (current_board.isCheck(current_board.board_arr, this.colour)) {
                continue;
            }
            valid_moves.add(move);
            thread_searches.add(new MultithreadingSearch(this.thinking_depth - 1, this.colour, current_board));
            thread = new Thread(thread_searches.get(thread_searches.size() - 1));
            threads.add(thread);
            thread.start();

        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("AI thread threw error on join.");
                throw new RuntimeException(e);
            }
        }


        Move best_move = new Move(new Pos(-1, -1), new Pos(-1, -1));;
        double best_val = 0;
        for (int i = 0; i < thread_searches.size(); i++) {

            if (best_move.start.x == -1) {
                best_move = valid_moves.get(i);
                best_val = thread_searches.get(i).get_evaluation();
            }
            if (this.colour == 'w' && thread_searches.get(i).get_evaluation() > best_val) {
                best_val = thread_searches.get(i).get_evaluation();
                best_move = valid_moves.get(i);
            }
            else if (this.colour == 'b' && thread_searches.get(i).get_evaluation() < best_val) {
                best_val = thread_searches.get(i).get_evaluation();
                best_move = valid_moves.get(i);
            }
        }

        System.out.println(best_move.start);
        System.out.println(best_move.end);
        return best_move;
    }

    private boolean isValidMove(Board new_board, Move move, char turn) {

        if (new_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) { //promotion
            new_board.perform_move(move.start, move.end, 5);
        }
        else if (new_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) { // casteling
            if (new_board.isCheck(new_board.board_arr, turn)) {
                return false;
            }
            try {
                new_board.castle(move.start, move.end);
            }
            finally {
                return false;
            }
        }
        else {
            new_board.perform_move(move.start, move.end);
        }


        if (new_board.isCheck(new_board.board_arr, turn)) {
           return false;
        }
        return true;
    }

}
