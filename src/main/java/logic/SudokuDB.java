package logic;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Arrays;

public class SudokuDB {

    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection collection;
    private SudokuFetcher fetcher;

    public SudokuDB(SudokuFetcher fetcher) {
        this.client = MongoClients.create("mongodb+srv://antonge:6zUGiSV4Ra4erB3G@cluster0.n9ovx.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");
        this.db = client.getDatabase("sudokuDB");
        this.collection = db.getCollection("sudokuCollection");
        this.fetcher = fetcher;
    }

    public void insert() {
        for (int i = 0; i < 3; i++) {
            String id = fetcher.getDate().toString() + "-" + i;
            if (collection.find(Filters.eq("id", id)).first() == null) {
                Document doc = new Document("id", id).append("puzzle", Arrays.toString(fetcher.getSudokus()[i]));
                collection.insertOne(doc);
            }
        }

    }

}
