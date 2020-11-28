package tictactoe;

import java.util.Random;

public class ComputerGamer implements Gamer {

    private enum LEVELS {
        easy,
        medium,
        hard
    }

    private final Random random = new Random();

    private char label;
    private LEVELS level;

    private ComputerGamer() {}

    public ComputerGamer(char label, String level) {
        this.label = label;
        this.level = LEVELS.valueOf(level.toLowerCase());
    }

    @Override
    public void makeMove(TicTacGame game) {
        System.out.printf("Making move level \"%s\"%n", level);

        switch (level) {
            case hard: {
                char[][] cellsCopy = game.getCellsCopy();

                int[] move = makeMoveHard(game, new ComputerGamer(TicTacGame.inverseLabel(label), "hard"));

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        game.setCell(i, j, cellsCopy[i][j]);
                    }
                }

                game.setCell(move[0], move[1], label);

                break;
            }
            case medium: {
                //Test for win
                for (int i = 0; i < 9; i++) {
                    if (game.getCell(i / 3, i % 3) == ' ') {
                        game.setCell(i / 3, i % 3, label);
                        if (game.resultOfGame() != label) {
                            game.setCell(i / 3, i % 3, ' ');
                        } else {
                            return;
                        }
                    }
                }

                //Test for loss
                for (int i = 0; i < 9; i++) {
                    if (game.getCell(i / 3, i % 3) == ' ') {
                        game.setCell(i / 3, i % 3, TicTacGame.inverseLabel(label));
                        if (game.resultOfGame() == TicTacGame.inverseLabel(label)) {
                            game.setCell(i / 3, i % 3, label);
                            return;
                        } else {
                            game.setCell(i / 3, i % 3, ' ');
                        }
                    }
                }
            }
            //Easy level
            default: {
                int i;
                do {
                    i = random.nextInt(9);
                } while (game.getCell(i / 3, i % 3) != ' ');

                game.setCell(i / 3, i % 3, label);
            }
        }
    }

    private int[] makeMoveHard(TicTacGame game, ComputerGamer opponent) {
        int bestScore = -100;
        int[] bestMove = new int[3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (game.getCell(i, j) == ' ') {
                    game.setCell(i, j, label);

                    int score = minMax(game, opponent);

                    game.setCell(i, j, ' ');

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestMove[2] = score;
                    }
                }
            }
        }

        game.setCell(bestMove[0], bestMove[1], label);
        return bestMove;
    }

    private int minMax(TicTacGame game, ComputerGamer opponent) {
        char resultOfGame = game.resultOfGame();

        if (resultOfGame == label) {
            return 10;
        } else {
            //If next move of opponent is loss
            for (int i = 0; i < 9; i++) {
                if (game.getCell(i / 3, i % 3) == ' ') {
                    game.setCell(i / 3, i % 3, opponent.label);
                    char resOfNextMove = game.resultOfGame();
                    game.setCell(i / 3, i % 3, ' ');
                    if (resOfNextMove == opponent.label) {
                        return -10;
                    }
                }
            }

            if (resultOfGame != 'd') {
                int[] move = opponent.makeMoveHard(game, this);
                return move[2];
            }
        }

        return 0;
    }
}
