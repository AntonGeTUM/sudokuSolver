import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    @Test
    public void validSudokuSize() {
        int[][] a = new int[9][9];
        assertDoesNotThrow(() -> new Sudoku(a));
    }

    @Test
    public void invalidSudokuSize() {
        int[][] a = new int[8][8];
        assertThrows(InvalidSudokuException.class, () -> new Sudoku(a));
    }

    @Test
    public void finishedSudoku() {
        int[][] a = {
                {3, 4, 9, 2, 5, 8, 6, 1, 7},
                {2, 5, 8, 6, 7, 1, 3, 9, 4},
                {1, 6, 7, 3, 4, 9, 2, 8, 5},
                {8, 9, 3, 4, 2, 5, 1, 7, 6},
                {7, 1, 4, 9, 8, 6, 5, 2, 3},
                {6, 2, 5, 1, 3, 7, 9, 4, 8},
                {4, 8, 6, 5, 1, 2, 7, 3, 9},
                {5, 7, 2, 8, 9, 3, 4, 6, 1},
                {9, 3, 1, 7, 6, 4, 8, 5, 2}
        };
        try {
            Sudoku sudoku = new Sudoku(a);
            assertTrue(sudoku.finished());
        } catch (InvalidSudokuException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void unfinishedSudoku() {
        int[][] a = {
                {3, 4, 9, 2, 5, 8, 6, 1, 7},
                {2, 5, 8, 6, 7, 1, 3, 9, 4},
                {1, 6, 7, 3, 4, 9, 2, 8, 5},
                {8, 9, 3, 4, 2, 5, 1, 7, 6},
                {7, 1, 4, 9, 0, 6, 5, 2, 3},
                {6, 2, 5, 1, 3, 7, 9, 4, 8},
                {4, 8, 6, 5, 1, 2, 7, 3, 9},
                {5, 7, 2, 8, 9, 3, 4, 6, 1},
                {9, 3, 1, 7, 6, 4, 8, 5, 2}
        };
        try {
            Sudoku sudoku = new Sudoku(a);
            assertFalse(sudoku.finished());
        } catch (InvalidSudokuException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findEmptyFieldWithNoEmptyField() {
        int[][] a = {
                {3, 4, 9, 2, 5, 8, 6, 1, 7},
                {2, 5, 8, 6, 7, 1, 3, 9, 4},
                {1, 6, 7, 3, 4, 9, 2, 8, 5},
                {8, 9, 3, 4, 2, 5, 1, 7, 6},
                {7, 1, 4, 9, 8, 6, 5, 2, 3},
                {6, 2, 5, 1, 3, 7, 9, 4, 8},
                {4, 8, 6, 5, 1, 2, 7, 3, 9},
                {5, 7, 2, 8, 9, 3, 4, 6, 1},
                {9, 3, 1, 7, 6, 4, 8, 5, 2}
        };
        try {
            Sudoku sudoku = new Sudoku(a);
            assertArrayEquals(sudoku.findEmptyField(), new int[0]);
        } catch (InvalidSudokuException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void findFirstEmptyField() {
        int[][] a = {               //i
                {3, 4, 9, 2, 5, 8, 6, 0, 7}, //j
                {2, 5, 0, 6, 7, 1, 3, 9, 4},
                {1, 6, 7, 3, 4, 9, 2, 8, 5},
                {8, 9, 3, 4, 2, 0, 1, 7, 6},
                {7, 1, 4, 9, 8, 6, 5, 2, 3},
                {6, 0, 5, 0, 0, 7, 9, 0, 8},
                {4, 8, 6, 5, 1, 0, 7, 3, 9},
                {5, 7, 2, 8, 9, 3, 4, 6, 1},
                {9, 3, 1, 7, 0, 4, 8, 0, 2}
        };
        try {
            Sudoku sudoku = new Sudoku(a);
            assertArrayEquals(sudoku.findEmptyField(), new int[] {0, 7});
        } catch (InvalidSudokuException e) {
            System.out.println(e.getMessage());
        }
    }

}
