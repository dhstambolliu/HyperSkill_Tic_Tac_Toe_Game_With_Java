package tictactoe;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {
    public String[][] getRows(String[] gameState) {
        String[][] rows = new String[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(gameState, i * 3, rows[i], 0, 3);
        }
        return rows;
    }

    public String[][] getCols(String[] gameState) {
        String[][] cols = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cols[i][j] = gameState[i + 3 * j];
            }
        }
        return cols;
    }

    public boolean isLineOf(String player, String[] line) {
        String[] expectedLine = new String[]{player, player, player};
        return Arrays.equals(line, expectedLine);
    }

    public boolean isLinesOf(String player, String[][] lines) {
        for (String[] line : lines) {
            if (isLineOf(player, line)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRowOf(String player, String[] gameState) {
        return isLinesOf(player, getRows(gameState));
    }

    public boolean isColOf(String player, String[] gameState) {
        return isLinesOf(player, getCols(gameState));
    }

    public boolean isRowOfXs(String[] gameState) {
        return isRowOf("X", gameState);
    }

    public boolean isRowOfOs(String[] gameState) {
        return isRowOf("O", gameState);
    }

    public boolean isColOfXs(String[] gameState) {
        return isColOf("X", gameState);
    }

    public boolean isColOfOs(String[] gameState) {
        return isColOf("O", gameState);
    }

    public String[] getRightDiagonal(String[] gameState) {
        return new String[]{gameState[0], gameState[4], gameState[8]};
    }

    public String[] getLeftDiagonal(String[] gameState) {
        return new String[]{gameState[2], gameState[4], gameState[6]};
    }

    public boolean isRightDiagonalOf(String player, String[] gameState) {
        return isLineOf(player, getRightDiagonal(gameState));
    }

    public boolean isLeftDiagonalOf(String player, String[] gameState) {
        return isLineOf(player, getLeftDiagonal(gameState));
    }

    public boolean isRightDiagonalOfXs(String[] gameState) {
        return isRightDiagonalOf("X", gameState);
    }

    public boolean isRightDiagonalOfOs(String[] gameState) {
        return isRightDiagonalOf("O", gameState);
    }

    public boolean isLeftDiagonalOfXs(String[] gameState) {
        return isLeftDiagonalOf("X", gameState);
    }

    public boolean isLeftDiagonalOfOs(String[] gameState) {
        return isLeftDiagonalOf("O", gameState);
    }

    public boolean isDiagonalOfXs(String[] gameState) {
        return isRightDiagonalOfXs(gameState) || isLeftDiagonalOfXs(gameState);
    }

    public boolean isDiagonalOfOs(String[] gameState) {
        return isRightDiagonalOfOs(gameState) || isLeftDiagonalOfOs(gameState);
    }

    public boolean isXWins(String[] gameState) {
        return isRowOfXs(gameState) || isColOfXs(gameState) || isDiagonalOfXs(gameState);
    }

    public boolean isOWins(String[] gameState) {
        return isRowOfOs(gameState) || isColOfOs(gameState) || isDiagonalOfOs(gameState);
    }

    public boolean movesAvailable(String[] gameState) {
        return Arrays.asList(gameState).contains(" ");
    }

    public boolean hasNoWinner(String[] gameState) {
        return !isXWins(gameState) && !isOWins(gameState);
    }

    public int count(String player, String[] gameState) {
        int count = 0;
        for (String move : gameState) {
            if (move.equals(player)) {
                count++;
            }
        }
        return count;
    }

    public boolean wrongNumberOfMoves(String[] gameState) {
        int movesDiff = count("X", gameState) - count("O", gameState);
        return movesDiff < -1 || movesDiff > 1;
    }

    public boolean isDraw(String[] gameState) {
        return hasNoWinner(gameState) && !movesAvailable(gameState);
    }

    public boolean isNotFinished(String[] gameState) {
        return hasNoWinner(gameState) && movesAvailable(gameState);
    }

    public boolean isImpossible(String[] gameState) {
        return isXWins(gameState) && isOWins(gameState) || wrongNumberOfMoves(gameState);
    }

    public void printBoard(String[] gameState) {
        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.printf("%s ", gameState[3 * i + j]);
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public String getStatus(String[] gameState) {
        String status = "Unknown game state";

        if (isImpossible(gameState)) {
            status = "Impossible";
        } else if (isNotFinished(gameState)) {
            status = "Game not finished";
        } else if (isXWins(gameState)) {
            status = "X wins";
        } else if (isOWins(gameState)) {
            status = "O wins";
        } else if (isDraw(gameState)) {
            status = "Draw";
        }

        return status;
    }

    public Coordinates readUserMove() throws OutBoundsMoveException {
        Scanner scanner = new Scanner(System.in);
        int y = scanner.nextInt() - 1;
        int x = scanner.nextInt() - 1;
        return new Coordinates(x, y);
    }

    public void makeUserMove(Coordinates coordinates, String[] gameState, String player)
            throws OccupiedCoordinateException {
        String target = gameState[coordinates.y * 3 + coordinates.x];

        if (target.equals("X") || target.equals("O")) {
            throw new OccupiedCoordinateException();
        }

        gameState[coordinates.y * 3 + coordinates.x] = player;
    }

    public void processUserMove(String[] gameState, String player) {
        try {
            System.out.print("Enter the coordinates: ");
            Coordinates coordinates = readUserMove();
            makeUserMove(coordinates, gameState, player);
        } catch (OutBoundsMoveException e) {
            System.out.println("Coordinates should be from 1 to 3!");
            processUserMove(gameState, player);
        } catch (OccupiedCoordinateException e) {
            System.out.println("This cell is occupied! Choose another one!");
            processUserMove(gameState, player);
        } catch (InputMismatchException e) {
            System.out.println("You should enter numbers!");
            processUserMove(gameState, player);
        }
    }

    public static String[] emptyBoard() {
        String[] board = new String[9];
        Arrays.fill(board, " ");
        return board;
    }

    public void play() {
        String[] gameState = emptyBoard();
        String gameStatus = getStatus(gameState);
        String nextPlayer = "X";

        while (gameStatus.equals("Game not finished")) {
            printBoard(gameState);
            processUserMove(gameState, nextPlayer);
            gameStatus = getStatus(gameState);
            nextPlayer = nextPlayer.equals("X") ? "O" : "X";
        }

        printBoard(gameState);
        System.out.println(gameStatus);
    }

}
