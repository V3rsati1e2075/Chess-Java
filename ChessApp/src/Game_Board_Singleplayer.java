package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Board.Board;
import Game.GameEngine;
import Pieces.Pos;
import Board.Move;

import AI.AI_Player;

public class Game_Board_Singleplayer extends JFrame implements ActionListener {
    String color = "White";
    Label[][] labels;
    LabeledButton[][] cells = new LabeledButton[8][8];

    GameEngine engine;
    Pos click_1, click_2;

    SetPanel board;
    LabeledButton [][]buttons ;

    ArrayList<Pos> possible_moves;
    JLabel name1;
    JLabel name2;

    AI_Player ai;

    public Game_Board_Singleplayer(String user1, String user2) {
        name1 = new JLabel(user1);
        name1.setBackground(Color.LIGHT_GRAY);
        name1.setBounds(300,10,200,20);
        name1.setOpaque(true);
        name1.setVisible(true);

        name2 = new JLabel(user2);
        name2.setBounds(300,760,200,20);
        name2.setBackground(Color.LIGHT_GRAY);
        name2.setOpaque(true);
        name2.setVisible(true);

        engine = new GameEngine();
        labels = new Label[8][8];

        buttons = new LabeledButton[8][8];

        // Setting up the window for the game
        this.setSize(1920, 1080);
        this.setResizable(true);
        this.setLayout(null);
        this.setTitle("2D Game of Chess");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.initialize();
        this.click_1 = new Pos(-1, -1);
        this.click_2 = new Pos(-1, -1);
        this.add(name1);

        this.ai = new AI_Player('b', 4);

    }

    private void refresh() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                cells[i][j].setIcon(this.engine.game_board.board_arr[i][j].image);
                this.labels[i][j].setVisible(false);

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }
                } else if (i % 2 == 1) {
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }

                }
            }
        }
    }

    private void initialize() {
        // new ImageIcon("path", "current piece");
        Label move;


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                move = new Label("go");
                this.labels[i][j] = move;
                if (this.engine.game_board.board_arr[i][j].get_type() != 0) {
                    buttons[i][j] = new LabeledButton(i, j, this.engine.game_board.board_arr[i][j].image);
                }
                else {
                    buttons[i][j] = new LabeledButton(i, j);
                }
                cells[i][j] = this.buttons[i][j];


                cells[i][j].add(move);
                cells[i][j].addActionListener(this);
                move.setVisible(false);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }
                } else if (i % 2 == 1) {
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }

                }
            }
        }

        // Creating a panel to store the grid in
        this.board = new SetPanel(color);
        if (color == "White") {
            for (int i = 0; i < 8; i++)
                for (int j = 0; j < 8; j++)
                    this.board.add(cells[i][j]);
        } else {
            for (int i = 7; i >= 0; i--)
                for (int j = 0; j < 8; j++)
                    this.board.add(cells[i][j]);
        }

        // Add the panel to the frame after rest have been added.
        this.add(board);
        this.add(name2);

    }

    private void play_ai_move() {
        this.refresh();
        Move computer_move = ai.minimax(this.engine.game_board.copy());

        if (computer_move.start.x == -1) {
           String message;
           if (this.ai.colour == 'w'){
                message = "Game Over. \n Black Wins by checkmate." ;
            }
            else {
                message = "Game Over. \n White Wins by checkmate." ;
            }

            Object[] options = {"New Game" , "Return to main menu"};
            int choice = JOptionPane.showOptionDialog(this,
                    message,
                    "Game Over",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) { // New game
                this.engine = new GameEngine();
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        cells[i][j].setIcon(this.engine.game_board.board_arr[i][j].image);
                    }
                }
            }
            else {
                this.dispose();
                new FrameWork();
                return;
            }

            return;
        }

            boolean isValid = false;



            if ((this.engine.game_board.turn == 'w' && computer_move.end.x == 0 && this.engine.game_board.board_arr[computer_move.start.x][computer_move.start.y].get_type() == 1)
                    || (this.engine.game_board.turn == 'b' && computer_move.end.x == 7 && this.engine.game_board.board_arr[computer_move.start.x][computer_move.start.y].get_type() == 1)) { // promo
                this.engine.game_board.perform_move(computer_move.start, computer_move.end, this.ai.promotion_choice);
            }
            else if (Math.abs(computer_move.start.y - computer_move.end.y) > 1 && this.engine.game_board.board_arr[computer_move.start.x][computer_move.start.y].get_type() == 6) {
                String message;
                if (this.ai.colour == 'w'){
                    message = "Game Over. \n Black Wins by resignation." ;
                }
                else {
                    message = "Game Over. \n White Wins by resignation." ;
                }

                Object[] options = {"New Game" , "Return to main menu"};
                int choice = JOptionPane.showOptionDialog(this,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) { // New game
                    this.engine = new GameEngine();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            cells[i][j].setIcon(this.engine.game_board.board_arr[i][j].image);
                        }
                    }
                }
                else {
                    this.dispose();
                    new FrameWork();
                    return;
                }

                return;
            }
            else {
                this.engine.game_board.perform_move(computer_move.start, computer_move.end);

                if (this.engine.game_board.isEnPassantCapture){
                    this.engine.game_board.isEnPassantCapture = false;
                }
            }


            this.click_1.x = -1;
            this.click_1.y = -1;
            this.refresh();


            ArrayList <Move> all_moves = this.engine.game_board.get_moves(this.engine.game_board.turn) ;
            ArrayList<Move> moves_to_remove = new ArrayList<Move>();
            for (Move move : all_moves) {
                Board new_board = this.engine.game_board.copy();
                try {
                    if (this.engine.game_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) { //promotion
                        new_board.perform_move(move.start, move.end, 5);
                    } else if (this.engine.game_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) { // casteling
                        if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                            moves_to_remove.add(move);
                            continue;
                        }
                        new_board.castle(move.start, move.end);
                    } else {
                        new_board.perform_move(move.start, move.end);
                    }

                    if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                        moves_to_remove.add(move);
                        continue;
                    }
                }
                catch(Exception exc) {
                    moves_to_remove.add(move);
                }

            }

            for (Move move : moves_to_remove) {
                all_moves.remove(move);
            }


            if (all_moves.size() == 0){
                String message ;

                if (!this.engine.game_board.isCheck(this.engine.game_board.board_arr, this.engine.game_board.turn)){
                    message = "Game Over. \n Stalemate" ;
                }
                else if (this.engine.game_board.turn == 'w'){
                    message = "Game Over. \n Black Wins by checkmate." ;
                }
                else {
                    message = "Game Over. \n White Wins by checkmate." ;
                }

                Object[] options = {"New Game" , "Return to main menu"};
                int choice = JOptionPane.showOptionDialog(this,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) { // New game
                    this.engine = new GameEngine();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            cells[i][j].setIcon(this.engine.game_board.board_arr[i][j].image);
                        }
                    }
                }
                else {
                    this.dispose();
                    new FrameWork();
                    return;
                }

            }
        }



    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.labels[i][j].setVisible(false);

                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }
                } else if (i % 2 == 1) {
                    if (j % 2 != 0) {
                        cells[i][j].setBackground(new Color(238, 238, 213));
                    }
                    if (j % 2 == 0) {
                        cells[i][j].setBackground(new Color(125, 148, 93));
                    }

                }
            }
        }
        this.refresh();

        LabeledButton button = (LabeledButton) e.getSource(); // button.location.x


        if (this.engine.game_board.board_arr[button.location.x][button.location.y].colour == this.engine.game_board.turn) {
            this.click_1.x = button.location.x;
            this.click_1.y = button.location.y;

            this.possible_moves = this.engine.game_board.board_arr[click_1.x][click_1.y].get_moves(this.engine.game_board.board_arr);
            ArrayList<Pos> moves_to_remove = new ArrayList<Pos>();
            Board new_board;
            for (Pos move : this.possible_moves) {
                new_board = this.engine.game_board.copy();
                if (new_board.board_arr[click_1.x][click_1.y].get_type() == 1 && (move.x == 0 || move.x == 7)) { //promotion
                    new_board.perform_move(click_1, move, 5);
                }
                else if (new_board.board_arr[click_1.x][click_1.y].get_type() == 6 && (2 == Math.abs(move.y - click_1.y))) { // casteling
                    if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                        moves_to_remove.add(move);
                        continue;
                    }
                    new_board.castle(click_1, move);
                }
                else {
                    new_board.perform_move(click_1, move);
                }


                if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                    moves_to_remove.add(move);
                    continue;
                }

                cells[move.x][move.y].setBackground(new Color(255, 0, 0));
            }

            for (Pos move : moves_to_remove) {
                this.possible_moves.remove(move);
            }

        }
        else if (click_1.x != -1){
            this.refresh();
            this.click_2.x = button.location.x;
            this.click_2.y = button.location.y;

            boolean isValid = false;

            for (Pos move : this.possible_moves) {
                if (move.equals(click_2))
                    isValid = true;
            }

            if (!isValid) {
                this.possible_moves.clear();
                return;
            }

            //this.remove(this.board);
            //ty his.refresh();


            if ((this.engine.game_board.turn == 'w' && click_2.x == 0 && this.engine.game_board.board_arr[click_1.x][click_1.y].get_type() == 1)
                    || (this.engine.game_board.turn == 'b' && click_2.x == 7 && this.engine.game_board.board_arr[click_1.x][click_1.y].get_type() == 1)) { // promo


                // Object[] options = {"Queen", "Rook", "Bishop", "Knight"};

                Object[] options = {new ImageIcon("src/PieceImages/wq.png"),
                        new ImageIcon("src/PieceImages/wr.png"),
                        new ImageIcon("src/PieceImages/wb.png"),
                        new ImageIcon("src/PieceImages/wn.png")};

                int choice = JOptionPane.showOptionDialog(this,
                        "Choose a piece to promote to.",
                        "A Promo Question",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);


                this.engine.game_board.perform_move(click_1, click_2, choice);
            }
            else if (Math.abs(click_1.y - click_2.y) > 1 && this.engine.game_board.board_arr[click_1.x][click_1.y].get_type() == 6) {
                this.engine.game_board.castle(click_1, click_2);


            }
            else {
                this.engine.game_board.perform_move(click_1, click_2);

                if (this.engine.game_board.isEnPassantCapture){
                    this.engine.game_board.isEnPassantCapture = false;
                }
            }


            System.out.println(this.engine.game_board.isCheck(this.engine.game_board.board_arr, this.engine.game_board.turn));
            this.engine.game_board.print();
            this.click_1.x = -1;
            this.click_1.y = -1;
            this.refresh();


            ArrayList <Move> all_moves = this.engine.game_board.get_moves(this.engine.game_board.turn) ;
            ArrayList<Move> moves_to_remove = new ArrayList<Move>();
            for (Move move : all_moves) {
                Board new_board = this.engine.game_board.copy();
                try {
                    if (this.engine.game_board.board_arr[move.start.x][move.start.y].get_type() == 1 && (move.end.x == 0 || move.end.x == 7)) { //promotion
                        new_board.perform_move(move.start, move.end, 5);
                    } else if (this.engine.game_board.board_arr[move.start.x][move.start.y].get_type() == 6 && (2 == Math.abs(move.end.y - move.start.y))) { // casteling
                        if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                            moves_to_remove.add(move);
                            continue;
                        }
                        new_board.castle(move.start, move.end);
                    } else {
                        new_board.perform_move(move.start, move.end);
                    }

                    if (new_board.isCheck(new_board.board_arr, this.engine.game_board.turn)) {
                        moves_to_remove.add(move);
                        continue;
                    }
                }
                catch(Exception exc) {
                    moves_to_remove.add(move);
                }

            }

            for (Move move : moves_to_remove) {
                all_moves.remove(move);
            }


            if (all_moves.size() == 0){
                String message ;

                if (!this.engine.game_board.isCheck(this.engine.game_board.board_arr, this.engine.game_board.turn)){
                    message = "Game Over. \n Stalemate" ;
                }
                else if (this.engine.game_board.turn == 'w'){
                    message = "Game Over. \n Black Wins by checkmate." ;
                }
                else {
                    message = "Game Over. \n White Wins by checkmate." ;
                }

                Object[] options = {"New Game" , "Return to main menu"};
                int choice = JOptionPane.showOptionDialog(this,
                        message,
                        "Game Over",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == 0) { // New game
                    this.engine = new GameEngine();
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            cells[i][j].setIcon(this.engine.game_board.board_arr[i][j].image);
                        }
                    }
                }
                else {
                    this.dispose();
                    new FrameWork();
                    return;
                }

            }
            this.refresh();
            this.play_ai_move();

            System.out.println(this.engine.game_board.isCheck(this.engine.game_board.board_arr, this.engine.game_board.turn));
            this.engine.game_board.print();
            this.refresh();
        }
    }
    }

