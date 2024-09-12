package GUI;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import logic.InvalidSudokuException;
import logic.Solver;
import logic.Sudoku;
import logic.SudokuFetcher;

public class Controller {

    @FXML
    private Menu load;
    @FXML
    private MenuItem easy;
    @FXML
    private MenuItem medium;
    @FXML
    private MenuItem hard;
    @FXML
    private Button reset;
    @FXML
    private Button solve;
    @FXML
    private GridPane parentGrid;
    private SudokuFetcher fetcher;
    private TextField[] textFields;
    private Solver solver;

    public void initialize() {
        fetcher = new SudokuFetcher();
        fetcher.fetchSudoku();
        easy.setOnAction(e -> setSudoku(easy.getId()));
        medium.setOnAction(e -> setSudoku(medium.getId()));
        hard.setOnAction(e -> setSudoku(hard.getId()));
        reset.setOnAction(e -> reset());
        solve.setOnAction(e -> solve());
        this.textFields = collectTextFields();
    }

    private TextField[] collectTextFields() {
        TextField[] res = new TextField[81];
        int[][] quadrantIndices = {{0,1,2,9,10,11,18,19,20}, {3,4,5,12,13,14,21,22,23}, {6,7,8,15,16,17,24,25,26},
                {27,28,29,36,37,38,45,46,47}, {30,31,32,39,40,41,48,49,50}, {33,34,35,42,43,44,51,52,53},
                {54,55,56,63,64,65,72,73,74}, {57,58,59,66,67,68,75,76,77}, {60,61,62,69,70,71,78,79,80}};
        int index = 0;
        int quadrant = 0;
        for (Node pane : parentGrid.getChildren()) {
            if (pane instanceof GridPane textField) {
                for (Node field : textField.getChildren()) {
                    if (field instanceof TextField) {
                        res[quadrantIndices[quadrant][index]] = (TextField)field;
                        index = (index + 1) % 9;
                    }
                }
                quadrant++;
            }
        }
        return res;
    }

    public void setSudoku(String id) {
        int[] sudoku = switch (id) {
            case "easy" -> fetcher.getSudokus()[0];
            case "medium" -> fetcher.getSudokus()[1];
            case "hard" -> fetcher.getSudokus()[2];
            default -> new int[0];
        };
        if (sudoku.length == 0) alert(new InvalidSudokuException("Unable to load sudoku."));
        fill(sudoku);
    }

    private void fill(int[] array) {
        for (int i = 0; i < 81; i++) {
            textFields[i].setText(String.valueOf(array[i]));
        }
    }

    private void alert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(e.getMessage());
        alert.show();
    }

    public void reset() {
        for (Node pane : parentGrid.getChildren()) {
            if (pane instanceof GridPane textField) {
                for (Node field : textField.getChildren()) {
                    if (field instanceof TextField) ((TextField) field).clear();
                }
            }
        }
    }

    public void solve() {
        int[] sudoku = new int[81];
        for (int i = 0; i < 81; i++) {
            String input = textFields[i].getCharacters().toString();
            if (input.length() == 0) input = "0";
            sudoku[i] = Integer.parseInt(input);
        }
        try {
            Sudoku sud = new Sudoku(Sudoku.arrayToMatrix(sudoku));
            this.solver = new Solver(sud);
            solver.solve(sud);
            fill(Sudoku.matrixToArray(solver.getSudoku().getSudoku()));
        } catch (InvalidSudokuException e) {
            alert(new InvalidSudokuException("Invalid sudoku, unable to solve."));
        }
    }

}
