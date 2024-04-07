import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        char[] board = new char[9];
        char[] players = {'X', 'O'};
        enum GameState { PLAY, DRAW, WINNER}

        prepareBoard(board);

        int activePlayer = 0;

        GameState actualGameState = GameState.PLAY;

        while(actualGameState == GameState.PLAY) {
            printBoard(board);

            System.out.println("Player " + players[activePlayer] + " is playing, enter number (1 - " + board.length + ")");

            int position = scanNumber(scanner);
            if(!verifyPosition(board, position-1)) continue;

            board[position-1] = players[activePlayer];

            if (checkWinner(board)) {
                actualGameState = GameState.WINNER;
            } else if (isDraw(board)) {
                actualGameState = GameState.DRAW;
            }

            if( actualGameState != GameState.PLAY) {
                printBoard(board);

                if (actualGameState == GameState.WINNER) {
                    System.out.println("Game is over. The winner is " + players[activePlayer] + "!");
                } else if (actualGameState == GameState.DRAW) {
                    System.out.println("Game is over. The result is draw!");
                }

                if(newGame(scanner)) {
                    activePlayer = 0;
                    prepareBoard(board);
                    actualGameState = GameState.PLAY;
                    continue;
                }
                
            }

            activePlayer = switchPlayer(activePlayer);
        }
    }

    public static boolean isDraw(char[] board) {
        for (char c : board) {
            if (c == ' ') return false;
        }

        return true;
    }

    public static boolean checkWinner(char[] board) {
        int threshold = (int) Math.sqrt(board.length);

        //check rows
        for (int i = 0; i <= board.length-threshold; i=i+threshold) {
            if(board[i] == ' ') continue;
            if(board[i] == board[i+1] && board[i] == board[i+2]) return true;
        }

        //check columns;
        for (int i = 0; i < threshold; i++) {
            if(board[i] == ' ') continue;
            if(board[i] == board[threshold + i] && board[i] == board[threshold * 2 + i]) return true;
        }

        //check diagonals;
        if (
                board[0] != ' '
                        && board[0] == board[threshold + 1]
                        && board[0] == board[threshold * 2 + 2]
        )  {
            return true;
        }

        //chek last diagonal
        return board[2] != ' '
                && board[2] == board[threshold + 1]
                && board[2] == board[threshold * 2];
    }

    public static boolean verifyPosition(char[] board, int position) {
        if (position < 0 || position >= board.length) {
            System.out.println("Invalid position number! Must be between 1 and " + board.length);
            return false;
        }

        if (board[position] != ' ') {
            System.out.println("Position " + position + " is occupied!");
            return false;
        }

        return true;
    }

    public static int switchPlayer(int activePlayer) {
        return activePlayer == 1 ? 0 : 1;
    }

    public static void prepareBoard(char[] board) {
        Arrays.fill(board, ' ');
    }

    public static void printBoard(char[] board) {
        int threshold = (int) Math.sqrt(board.length);

        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(" " + board[i] + " ");
            if ((i + 1) % threshold == 0) {
                System.out.println();
                if (i < board.length - 1) {
                    System.out.println("-----------");
                }
            } else {
                System.out.print("|");
            }

        }
    }

    public static int scanNumber(Scanner scanner) {
        int number;
        while (true) {
            System.out.print("Insert number: ");
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Input is not a number: " + e.getMessage());
                scanner.nextLine();
            }
        }

        return number;
    }

    public static boolean newGame(Scanner scanner) {
        System.out.println();

        while (true) {
            System.out.print("Do you want to play new game (y/n) ?: ");
            String usertInput = scanner.nextLine().toLowerCase();

            if(usertInput.equals("y")) return true;
            if(usertInput.equals("n")) return false;

            System.out.println("Pls choose only y or n");
        }
    }

}