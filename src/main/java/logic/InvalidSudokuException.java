package logic;

public class InvalidSudokuException extends Exception{

    public InvalidSudokuException(String errorMessage) {
        super(errorMessage);
    }
}
