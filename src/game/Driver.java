package game;

import java.util.*;
import java.io.*;

public class Driver{
  private static void testIndividualMethods() {
        String[] methods = { "updateOpenSpaces", "addRandomTile", "swipeLeft", "mergeLeft", "transpose", "flipRows", "makeMove" }, options = { "Test a new input file", "Test another method on the same input file", "Quit" };
        int controlChoice = 1;
        do {
            Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
            System.out.print("Enter a board input file name => ");
            String filename = sc.next();
            do {
                int method = getMethodChoice(methods);
                int[][] boardArray = readBoard(filename);
                Board board = new Board(boardArray);
                System.out.println("Base Board:");
                board.print();
                testMethod(method, board);
                controlChoice = getControlChoice(options);
            } while (controlChoice == 2);
        } while (controlChoice == 1);
    }
  private static int[][] readBoard(String filename) {
        Scanner sc = new Scanner(System.in);
        try {
            sc = new Scanner(new File(filename), "UTF-8");
            sc.useLocale(Locale.US);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] boardArray = new int[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                boardArray[i][j] = sc.nextInt();
        sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        return boardArray;
    }
  private static int getControlChoice(String[] options) {
        System.out.println("What would you like to do?");
        for (int i = 0; i < 3; i++)
            System.out.println(i + 1 + ". " + options[i]);
        Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        System.out.print("Enter a number => ");
        int number = sc.nextInt();
        return number;
    }
  private static int getMethodChoice(String[] methods) {
        System.out.println("What method would you like to test?");
        for (int i = 0; i < 7; i++)
            System.out.println(i + 1 + ". " + methods[i]);
        Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        System.out.print("Enter a number => ");
        int number = sc.nextInt();
        return number;
    }
   private static int getActionChoice() {
        System.out.println("What would you like to do?");
        System.out.println("1. Test individual methods");
        System.out.println("2. Play full game");
        System.out.print("Enter a number => ");
        Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        System.out.print("Enter a number => ");
        int number = sc.nextInt();
        return number;
    }
  private static void testMethod(int methodNumber, Board board) {
        switch (methodNumber) {
            case 1:
                board.updateOpenSpaces();
                System.out.println("Open Spaces: (** on board)");
                board.printOpenSpaces();
                break;
            case 2:
                board.updateOpenSpaces();
                board.addRandomTile();
                System.out.println("New Board:");
                board.print();
                break;
            case 3:
                board.swipeLeft();
                System.out.println("New Board:");
                board.print();
                break;
            case 4:
                board.mergeLeft();
                System.out.println("New Board:");
                board.print();
                break;
            case 5:
                board.transpose();
                System.out.println("New Board:");
                board.print();
                break;
            case 6:
                board.flipRows();
                System.out.println("New Board:");
                board.print();
                break;
            case 7:
                System.out.println("Actions: w = up, a = left, s = down, d = right");
                HashMap<Character, String> keyMap = new HashMap<>() {
                    {
                        put('w', "U");
                        put('a', "L");
                        put('s', "D");
                        put('d', "R");
                        put('W', "U");
                        put('A', "L");
                        put('S', "D");
                        put('D', "R");
                    }
                };
                System.out.print("Enter an action => ");
                Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
                String toRead = sc.next(), action = keyMap.getOrDefault(toRead.charAt(0), "Invalid");
                if (!action.equals("Invalid"))
                    board.makeMove(action.charAt(0));
                System.out.println("New Board:");
                board.print();
                break;
        }
    }
   private static void playFullGame() {
        Board board = new Board();
        board.updateOpenSpaces();
        board.addRandomTile();
        board.updateOpenSpaces();
        board.addRandomTile();
        System.out.println("Base Board:");
        board.print();
        System.out.println("Actions: w = up, a = left, s = down, d = right, q = exit (hit ENTER to enter)");
        HashMap<Character, String> keyMap = new HashMap<>() {
            {
                put('w', "U");
                put('a', "L");
                put('s', "D");
                put('d', "R");
                put('W', "U");
                put('A', "L");
                put('S', "D");
                put('D', "R");
                put('q', "Exit");
            }
        };
        Scanner sc = new Scanner(new BufferedInputStream(System.in), "UTF-8");
        while (true) {
            String toRead = sc.next(), action = keyMap.getOrDefault(toRead.charAt(0), "Invalid");
            if (action.equals("Exit"))
                break;
            if (!action.equals("Invalid")) {
                int[][] oldBoard = new int[4][4];
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++)
                        oldBoard[i][j] = board.getBoard()[i][j];
                board.makeMove(action.charAt(0));
                board.updateOpenSpaces();
                if (!board.isGameLost() && !boardsMatch(oldBoard, board.getBoard()))
                    board.addRandomTile();
                System.out.println("New Board: " + action);
                board.print();
            }
        }
    }
  private static boolean boardsMatch(int[][] board1, int[][] board2) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (board1[i][j] != board2[i][j])
                    return false;
        return true;
    }
  public static void main(String[] args) {
        int action = getActionChoice();
        switch (action) {
            case 1: // Individual methods
                testIndividualMethods();
                break;
            case 2: // Play full game
                playFullGame();
                break;
            default:
                System.out.println("Not a valid option!");
                break;
        }
        System.exit(0);
    }
}
