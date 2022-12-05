package table;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import login.DatabaseConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetAllTableService extends Service<ArrayList<Table>> {
    @Override
    protected Task<ArrayList<Table>> createTask() {
        return new GetAllTableTask();
    }

}
