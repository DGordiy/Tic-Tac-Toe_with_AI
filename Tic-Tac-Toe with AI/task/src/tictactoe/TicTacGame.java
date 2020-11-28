package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

public class TicTacGame {

    private final Scanner scanner = new Scanner(System.in);

    private final char[][] cells = new char[3][3];

    public char getCell(int i, int j) {
        return cells[i][j];
    }

    public void setCell(int i, int j, char c) {
        cells[i][j] = c;
    }

    public char[][] getCellsCopy() {
        char[][] copy = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = cells[i][j];
            }
        }

        return copy;
    }

    public void start() {
        while (true) {
            System.out.println("Input command");
            String[] input = scanner.nextLine().toUpperCase().split("\\s+");
            if (input[0].equals("EXIT")) {
                return;
            } else {
                if (input.length != 3 || !input[0].equals("START")
                        || (!input[1].equals("USER") && !input[1].equals("EASY") && !input[1].equals("MEDIUM") && !input[1].equals("HARD"))
                        || (!input[2].equals("USER") && !input[2].equals("EASY") && !input[2].equals("MEDIUM") && !input[2].equals("HARD"))) {
                    System.out.println("Bad parameters!");
                } else {
                    startLevel(input[1].equals("USER") ? new UserGamer('X') : new ComputerGamer('X', input[1]), input[2].equals("USER") ? new UserGamer('O') : new ComputerGamer('O', input[2]));
                }
            }
        }
    }

    private void startLevel(Gamer user1, Gamer user2) {
        for (int i = 0; i < 9; i++) {
            cells[i / 3][i % 3] = ' ';
        }

        Gamer currentUser = user1;

        showCells();
        do {
            currentUser.makeMove(this);

            if (currentUser.equals(user1)) {
                currentUser = user2;
            } else {
                currentUser = user1;
            }

            showCells();
        } while (resultOfGame(true) == '\0');
    }

    private void showCells() {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");

            for (int j = 0; j < 3; j++) {
                System.out.print(cells[i][j] + " ");
            }

            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static char inverseLabel(char label) {
        return label == 'X' ? 'O' : 'X';
    }

    public char resultOfGame() {
        return resultOfGame(false);
    }

    private char resultOfGame(boolean showMessages) {
        char winChar = '\0';
        boolean spaces = false;

        if (cells[0][0] != ' ' && cells[0][0] == cells[1][1] && cells[0][0] == cells[2][2]) {
            winChar = cells[0][0];
        } else if (cells[0][2] != ' ' && cells[0][2] == cells[1][1] && cells[0][2] == cells[2][0]) {
            winChar = cells[0][2];
        }

        if (winChar == '\0') {
            for (int i = 0; i < 3; i++) {
                if (cells[i][0] != ' ' && cells[i][0] == cells[i][1] && cells[i][0] == cells[i][2]) {
                    winChar = cells[i][0];
                    break;
                } else if (cells[0][i] != ' ' && cells[0][i] == cells[1][i] && cells[0][i] == cells[2][i]) {
                    winChar = cells[0][i];
                    break;
                } else {
                    for (int j = 0; j < 3; j++) {
                        if (cells[i][j] == ' ') {
                            spaces = true;
                            break;
                        }
                    }
                }
            }
        }

        if (winChar == '\0') {
            if (!spaces) {
                winChar = 'd';
                if (showMessages) {
                    System.out.println("Draw");
                }
            }
        } else {
            if (showMessages) {
                System.out.println(winChar + " wins");
            }
        }

        return winChar;
    }
}
