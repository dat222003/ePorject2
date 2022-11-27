package table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllTableTask extends Task<ArrayList<Table>> {

    @Override
    protected ArrayList<Table> call() throws Exception {
        ArrayList<Table> tableList = new ArrayList<>();
        tableDB tableDB = new tableDB();
        ArrayList<Table> arrayList = tableDB.getAllTable();
        for (int i = 0; i < arrayList.size(); i++) {
            tableList.add(arrayList.get(i));
            updateValue(tableList);
            updateProgress(i, arrayList.size());
        }
        return tableList;
    }
}
