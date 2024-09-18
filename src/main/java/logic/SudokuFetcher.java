package logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

public class SudokuFetcher {

    private final String url = "https://www.nytimes.com/puzzles/sudoku";
    private LocalDate date;
    private int[][] sudokus = new int[3][];
    private SudokuDB db;

    public SudokuFetcher() {
        date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        formatter.format(date);
        this.db = new SudokuDB(this);
    }

    public int[][] getSudokus() {
        return sudokus;
    }

    public LocalDate getDate() {
        return date;
    }

    public void fetchSudoku() {
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.getElementById("js-hook-pz-moment__game");
            String data = element.data();
            data = data.substring(data.indexOf("{"));
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            JsonArray[] arrays = new JsonArray[3];
            String[] difficulties = {"easy", "medium", "hard"};
            for (int i = 0; i < 3; i++) {
                arrays[i] = getJsonArray(jsonObject, difficulties[i]);
                sudokus[i] = new int[arrays[i].size()];
                JsonToArray(arrays[i], sudokus[i]);
            }
            db.insert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonArray getJsonArray(JsonObject object, String difficulty) {
        return object.getAsJsonObject(difficulty)
                .getAsJsonObject("puzzle_data")
                .getAsJsonArray("puzzle");
    }

    private void JsonToArray(JsonArray jsonArray, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = jsonArray.get(i).getAsInt();
        }
    }

    /*public static void main(String[] args) throws InvalidSudokuException {
        logic.SudokuFetcher fetcher = new logic.SudokuFetcher();
        fetcher.fetchSudoku();
        for (int i = 0; i < 3; i++) {
            System.out.println(new Sudoku(Sudoku.arrayToMatrix(fetcher.sudokus[i])));
        }
    }*/
}
