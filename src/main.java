import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.stream.IntStream;



class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input.txt"));
        int[][] board = new int[9][9];
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                board[i][j] = scanner.nextInt();
            }
        }
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 9; ++j) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }


    }


    private static boolean solve(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    for (int k = 0; k <= 9; k++) {
                        board[i][j] = k;
                        if (isValid(board, i, j) && solve(board)) {
                            return true;
                        }
                        board[i][j] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValid(int[][] board, int row, int column) {
        return (rowConstraint(board, row)
                && columnConstraint(board, column)
                && subsectionConstraint(board, row, column));
    }

    private static boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[9];
        return IntStream.range(0, 8)
                .allMatch(column -> checkConstraint(board, row, constraint, column));
    }

    private static boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[9];
        return IntStream.range(0, 8)
                .allMatch(row -> checkConstraint(board, row, constraint, column));
    }

    private static boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[9];
        int subsectionRowStart = (row / 3) * 3;
        int subsectionRowEnd = subsectionRowStart + 3;

        int subsectionColumnStart = (column / 3) * 3;
        int subsectionColumnEnd = subsectionColumnStart + 3;

        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }

    static boolean checkConstraint(int[][] board, int i, boolean[] constraint, int j) {
        if (board[i][j] != 0) {
            if (!constraint[board[i][j] - 1]) {
                constraint[board[i][j] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }


}
