package server;

import logic.InvalidSudokuException;
import logic.Solver;
import logic.Sudoku;
import logic.SudokuFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//business logic implementations
@Service
public class ServerService {

    private Solver solver;
    private SudokuFetcher fetcher;

    @Autowired
    public ServerService() throws InvalidSudokuException {
        Sudoku sudoku = new Sudoku(new int[9][9]);
        this.solver = new Solver(sudoku);
        this.fetcher = new SudokuFetcher();
        fetcher.fetchSudoku();
    }

    public Solver getSolver() {
        return solver;
    }

    public void setSudoku(int[][] matrix) throws InvalidSudokuException {
        this.solver.setSudoku(new Sudoku(matrix));
    }

    public String showSudoku() {
        return solver.getSudoku().toString();
    }

    public String showEasySudoku() {
        try {
            solver.setSudoku(new Sudoku(Sudoku.arrayToMatrix(fetcher.getSudokus()[0])));
            return solver.getSudoku().toString();
        } catch (InvalidSudokuException e) {
            return e.getMessage();
        }
    }
}
