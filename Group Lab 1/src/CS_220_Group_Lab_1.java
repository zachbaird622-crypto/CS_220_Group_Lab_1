import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Reads a grid of characters from a text file and allows the user to search
 * for specific words in all eight directions (horizontal, vertical, and diagonal).
 *
 * @author Joshua Castro
 * @version 1.0
 */
public class CS_220_Group_Lab_1 {
    /**
     * The main entry of the program. Prompts user for a file,
     * builds a dynamic 2D grid of characters, and turns it into a continuous loop
     * for the user to guess words until they want to stop.
     *
     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the name of the file containing the grid: ");
        String filename = sc.nextLine();

        ArrayList<String> fileLines = new ArrayList<>();

        try {
            Scanner fileScan = new Scanner(new File(filename));
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine().trim();
                if (!line.isEmpty()) {
                    fileLines.add(line);
                }
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return;
        }

        // check for empty file
        if (fileLines.isEmpty()) {
            System.out.println("Error: The file is completely empty.");
            return;
        }

        // Initialize grid
        int rows = fileLines.size();
        int cols = fileLines.get(0).length();
        char[][] grid = new char[rows][cols];

        for(int r = 0; r < rows; r++){
            grid[r] = fileLines.get(r).toCharArray();
        }

        System.out.println("\nCurrent grid:");
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                System.out.print(grid[r][c] + " ");
            }
            System.out.println();
        }

        boolean playing = true;
        while (playing) {
            System.out.println("\nPlease enter your guess:");
            String guess = sc.nextLine().toUpperCase();

            boolean found = false;
            for(int r = 0; r < rows; r++){
                for(int c = 0; c < cols; c++){
                    if (grid[r][c] == guess.charAt(0)){
                        if (checkWord(grid, c, r, guess)){
                            found = true;
                        }
                    }
                }
            }

            if (found) {
                System.out.println("Right! The word '" + guess + "' is in the grid.");
            } else {
                System.out.println("Wrong! The word '" + guess + "' is not in the grid.");
            }

            System.out.print("Would you like to search for another word? (Y/N): ");
            String response = sc.nextLine();
            if (!response.equalsIgnoreCase("Y")) {
                playing = false;
                System.out.println("Thanks for playing!");
            }
        }
        sc.close();
    }

    /**
     * Checks all eight lines (horizontal, vertical, and diagonal) passing through
     * a specific starting character in a grid to see if the full word is present.
     * @param grid the 2D character array that represents the word search grid.
     * @param c the starting column index.
     * @param r the starting row index.
     * @param word the word the user is searching for.
     * @return true if the word is found starting at these coordinates, false otherwise
     */

    public static boolean checkWord(char[][] grid, int c, int r, String word){
        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            if (checkDirection(grid, c, r, dCol[i], dRow[i], word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Steps through the grid in a single specific direction to verify if consecutive
     * letters match the remaining characters of the word.
     *
     * @param grid the 2D character array representing the word search grid.
     * @param c the starting column index.
     * @param r the starting row index.
     * @param dc the change in column direction (-1, 0, or 1).
     * @param dr the change in the row direction (-1, 0, or 1).
     * @param word the word that is being searched for.
     * @return true if all letters in the word match this direction and stay within boundaries, false otherwise.
     */

    private static boolean checkDirection(char[][] grid, int c, int r, int dc, int dr, String word){
        int nextC = c + dc;
        int nextR = r + dr;

        for(int i = 1; i < word.length(); i++){
            if(nextR < 0 || nextR >= grid.length || nextC < 0 || nextC >= grid[0].length){
                return false;
            }
            if(grid[nextR][nextC] != word.charAt(i)) {
                return false;
            }
            nextC += dc;
            nextR += dr;
        }
        return true;
    }
}