package logic;
import com.google.gson.JsonArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;

public class SudokuDB {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static SudokuDB instance;
    private Connection connection;

    private SudokuDB() {
        try {
            String[] access = readUserAndPW();
            if (access == null) {
                System.out.println("Could not retrieve login data.");
                return;
            }
            connection = DriverManager.getConnection(url, access[0], access[1]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SudokuDB getInstance() {
        if (instance == null) {
            instance = new SudokuDB();
        }
        return instance;
    }

    public void insert(String date, JsonArray[] arrays) {
        final String query = "insert into sudoku(date, easy, medium, hard) values(?, ?, ? , ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            statement.setString(2, arrays[0].toString());
            statement.setString(3, arrays[1].toString());
            statement.setString(4, arrays[2].toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] retrieve(String date) {
        final String query = "select * from sudoku where date=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            ResultSet set = statement.executeQuery();
            set.next();
            String[] res = new String[3];
            res[0] = set.getString(2);
            res[1] = set.getString(3);
            res[2] = set.getString(4);
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] retrieveDates() {
        final String query = "select date from sudoku";
        try {
            PreparedStatement statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet set = statement.executeQuery();
            int size = 0;
            if (set != null) {
                set.last();
                size = set.getRow();
                set.beforeFirst();
                set.next();
            }
            String[] res = new String[size];
            for (int i = 0; i < size; i++) {
                res[i] = set.getString(1);
                set.next();
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(String date) {
        final String query = "select exists(select 1 from sudoku where date = ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, date);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int[][] parseQuery(String[] queryResult) {
        int[][] res = new int[3][81];
        for (int i = 0; i < 3; i++) {
            String tmp = queryResult[i].substring(1, queryResult[i].length() - 1);
            res[i] = Arrays.stream(tmp.split(",")).mapToInt(Integer::parseInt).toArray();
        }
        return res;
    }

    private static String[] readUserAndPW() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/postgres.txt"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("-");
            }
            return sb.toString().split("-");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SudokuDB db = getInstance();
        String[] res = db.retrieveDates();
        System.out.println(Arrays.toString(res));
    }
}
