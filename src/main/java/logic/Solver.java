package logic;

import java.util.List;

public class Solver {

    private Sudoku sudoku;

    public Solver(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public Sudoku getSudoku() {
        return this.sudoku;
    }

    public void solve(Sudoku sudoku) {
        if (sudoku.finished()) {
            this.sudoku = sudoku;
            return;
        }
        int[] next = sudoku.findEmptyField();
        int row = next[0];
        int col = next[1];
        List<Integer> temp = sudoku.possibleValues(row, col);
        if (temp.size() != 0) {
            for (int i = 0; i < temp.size(); i++) {
                sudoku.setField(new int[] {row, col, temp.get(i)});
                solve(sudoku);
                if (i == temp.size() - 1 && !sudoku.finished()) {
                    sudoku.setField(new int[] {row, col, 0});
                    return;
                }
                if (sudoku.finished()) {
                    break;
                }
            }
        }

    }

    /*public static void main(String[] args) {
        int[][] testSudoku3 = new int[][] {
                {3,0,9,2,0,0,6,0,7},
                {0,0,0,6,0,0,3,0,4},
                {1,0,0,0,0,0,0,8,0},
                {0,0,0,0,2,5,0,0,0},
                {0,0,0,9,0,0,0,0,0},
                {6,2,0,0,3,0,9,0,0},
                {4,0,6,0,0,0,0,0,0},
                {0,7,2,0,9,0,0,0,0},
                {0,0,0,7,0,0,8,5,0},
        };

        try {
            Sudoku sudoku4 = new Sudoku(testSudoku3);
            System.out.println(sudoku4);
            Solver solver3 = new Solver(sudoku4);
            solver3.solver(sudoku4);
            System.out.println(solver3.getSudoku());
        } catch (InvalidSudokuException e) {
            System.out.println(e.getMessage());
        }

    }*/

}
