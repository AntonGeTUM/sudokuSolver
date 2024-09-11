import java.util.*;

public class Sudoku {

    private final int[][] sudoku;

    public Sudoku(int[][] a) throws InvalidSudokuException {
        if (a.length == 9 && a[0].length == 9) {
            this.sudoku = a;
        } else {
            throw new InvalidSudokuException("Sudoku passed to constructor is invalid");
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("=============================\n");
        for (int i = 0; i < sudoku.length; i++) {
            StringBuilder line = new StringBuilder("||");
            for (int j = 0; j < sudoku[0].length; j++) {
                line.append(" ").append(sudoku[i][j]);
                if (j == 2 || j == 5 || j == 8) {
                    line.append(" ||");
                }

            }
            if (i == 2 || i == 5 || i == 8) {
                line.append("\n=============================");
            }
            line.append("\n");
            res.append(line);
        }
        return res.toString();
    }

    private int findQuadrant(int a) {
        return switch(a) {
            case 0, 1, 2 -> 0;
            case 3, 4, 5 -> 3;
            case 6, 7, 8 -> 6;
            default -> -1;
        };
    }

    public boolean finished() {
        for (int[] row : sudoku) {
            for (int i : row) {
                if (i == 0) return false;
            }
        }
        return true;
    }

    public int[] findEmptyField() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[0].length; j++) {
                if (sudoku[i][j] == 0) return new int[] {i, j};
            }
        }
        return new int[0];
    }

    public void set(int[] a) {
        if (a.length == 3) {
            sudoku[a[0]][a[1]] = a[2];
        }
    }
    /*
    public boolean validRow(int row) {
        boolean[] temp = new boolean[9];
        for (int i = 0; i < 9; i++) {
            int val = sudoku[row][i];
            if (val != 0) {
                if (temp[val - 1]) return false;
                else temp[val - 1] = true;
            }
        }
        return true;
    }

    public boolean validColumn(int column) {
        boolean[] temp = new boolean[9];
        for (int i = 0; i < 9; i++) {
            int val = sudoku[i][column];
            if (val != 0) {
                if (temp[val - 1]) return false;
                else temp[val - 1] = true;
            }
        }
        return true;
    }

    public boolean validQuadrant(int row, int col) {
        boolean[] temp = new boolean[9];
        int rowIndex = findQuadrant(row);
        int colIndex = findQuadrant(col);
        for (int i = rowIndex; i <= rowIndex + 2; i++) {
            for (int j = colIndex; j <= colIndex + 2; j++) {
                int val = sudoku[i][j];
                if (val != 0) {
                    if (temp[val - 1]) return false;
                    else temp[val - 1] = true;
                }
            }
        }
        return true;
    }*/

    public List<Integer> possibleValues(int row, int column) {
        List<Integer> res = new ArrayList<>();
        int[] tempArray = new int[] {1,2,3,4,5,6,7,8,9};

        //if (validRow(row) && validColumn(column) && validQuadrant(row, column)) {
            for (int i = 0; i < 9; i++) {
                if (sudoku[row][i] != 0) {
                    tempArray[sudoku[row][i] - 1] = 0;
                }
                if (sudoku[i][column] != 0) {
                    tempArray[sudoku[i][column] - 1] = 0;
                }
            }

            int rowIndex = findQuadrant(row);
            int colIndex = findQuadrant(column);
            for (int i = rowIndex; i <= rowIndex + 2; i++) {
                for (int j = colIndex; j <= colIndex + 2; j++) {
                    if (sudoku[i][j] != 0) {
                        tempArray[sudoku[i][j] - 1] = 0;
                    }
                }
            }
        //}

        for (int i : tempArray) {
            if (i != 0) res.add(i);
        }
        return res;
    }

}