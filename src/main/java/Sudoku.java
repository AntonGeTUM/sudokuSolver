import java.util.*;

public class Sudoku {

    private final int[][] sudoku;

    public Sudoku(int[][] a) throws InvalidSudokuException {
        if (a.length != 9) {
            throw new InvalidSudokuException("Sudoku passed to constructor is invalid");
        }
        for (int i = 0; i < 9; i++) {
            if (a[i].length != 9) {
                throw new InvalidSudokuException("Sudoku passed to constructor is invalid");
            }
        }
        if (!validRowsAndColumns(a)) {
            throw new InvalidSudokuException("Sudoku passed to constructor is invalid");
        }
        if (!validQuadrants(a)) {
            throw new InvalidSudokuException("Sudoku passed to constructor is invalid");
        }

        this.sudoku = a;
    }

    public boolean validRowsAndColumns(int[][] matrix) {
        for (int i = 0; i < 9; i++) {
            boolean[] usedValuesRow = new boolean[9];
            boolean[] usedValuesColumn = new boolean[9];
            for (int j = 0; j < 9; j++) {
                int valRow = matrix[i][j];
                if (valRow != 0) {
                    if (usedValuesRow[valRow - 1]) return false;
                    else usedValuesRow[valRow - 1] = true;
                }
                int valCol = matrix[j][i];
                if (valCol != 0) {
                    if (usedValuesColumn[valCol - 1]) return false;
                    else usedValuesColumn[valCol - 1] = true;
                }
            }
        }
        return true;
    }

    public boolean validQuadrants(int[][] matrix) {
        int[] quadrantIndices = {0, 3, 6};
        for (int rowIndex : quadrantIndices) {
            for (int colIndex : quadrantIndices) {
                boolean[] temp = new boolean[9];
                for (int i = rowIndex; i <= rowIndex + 2; i++) {
                    for (int j = colIndex; j <= colIndex + 2; j++) {
                        int val = matrix[i][j];
                        if (val != 0) {
                            if (temp[val - 1]) return false;
                            else temp[val - 1] = true;
                        }
                    }
                }
            }
        }
        return true;
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
        return switch (a) {
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
                if (sudoku[i][j] == 0) return new int[]{i, j};
            }
        }
        return new int[0];
    }

    public void setField(int[] a) {
        if (a.length == 3) {
            sudoku[a[0]][a[1]] = a[2];
        }
    }

    public List<Integer> possibleValues(int row, int column) {
        List<Integer> res = new ArrayList<>();
        int[] tempArray = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

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

        for (int i : tempArray) {
            if (i != 0) res.add(i);
        }
        return res;
    }

}