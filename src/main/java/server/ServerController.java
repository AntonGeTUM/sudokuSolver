package server;


import logic.InvalidSudokuException;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//REST interface to business logic
@RestController
public class ServerController {

    private ServerService service;

    @Autowired
    public ServerController(ServerService service) {
        this.service = service;
    }

    @GetMapping("sudoku-solver")
    public ResponseEntity<String> sudokuStart() {
        return ResponseEntity.ok("Sudoku Solver");
    }

    @GetMapping("sudoku-solver/today/easy")
    public ResponseEntity<String> getEasySudoku() {
        return ResponseEntity.ok(service.showEasySudoku());
    }

    @PutMapping("sudoku-solver")
    public ResponseEntity<String> submitSudoku(@RequestParam int[][] matrix) {
        try {
            service.setSudoku(matrix);
            return ResponseEntity.ok(service.showSudoku());
        } catch (InvalidSudokuException e) {
            return ResponseEntity.status(400).body("Invalid Sudoku");
        }
    }

}
