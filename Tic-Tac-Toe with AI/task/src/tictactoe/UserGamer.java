package tictactoe;

import java.util.Scanner;

public class UserGamer implements Gamer {

    private final Scanner scanner = new Scanner(System.in);
    private char label;

    private UserGamer() {}

    public UserGamer(char label) {
        this.label = label;
    }

    @Override
    public void makeMove(TicTacGame game) {
        System.out.print("Enter the coordinates: ");
        String[] coordinates = scanner.nextLine().split("\\s+");
        boolean isOK = false;
        if (coordinates.length == 2) {
            try {
                int x = Integer.parseInt(coordinates[0]);
                int y = Integer.parseInt(coordinates[1]);

                if (x >= 1 && x <= 3 && y >= 1 && y <= 3) {
                    if (game.getCell(3 - y, x - 1) == ' ') {
                        game.setCell(3 - y, x - 1, label);
                        isOK = true;
                    } else {
                        System.out.println("This cell is occupied! Choose another one!");
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            }
        } else {
            System.out.println("You should enter numbers!");
        }

        if (!isOK) {
            makeMove(game);
        }
    }
}
