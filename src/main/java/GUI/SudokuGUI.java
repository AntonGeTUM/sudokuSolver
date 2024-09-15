package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SudokuGUI  extends Application{

    @Override
    public void start(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SudokuGUI.fxml"));
        Parent root = loader.load();
        stage.setTitle("Sudoku Solver");
        stage.setScene(new Scene(root, 600, 800));
        stage.getIcons().add(new Image("/icon.JPG"));
        stage.show();
    }

    public void begin(String[] args) {
        launch(args);
    }
}
