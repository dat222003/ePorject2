package tab;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import table.Table;
import table.tableDB;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TableTabController implements Initializable {

    @FXML
    private GridPane tableGridPane;

    private ArrayList<Table> tablesList;

    @FXML
    private JFXButton refreshButton;

    @FXML
    private HBox hBox;

    @FXML
    void reloadTable(ActionEvent event) {
        //call initialize
        initialize(null, null);

    }

    private ArrayList<Table> data() {
        tableDB tableDB = new tableDB();
        return new ArrayList<>(tableDB.getAllTable());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        hBox.setVisible(false);
        tablesList = data();
        int row = 1;
        int column = 0;
        for (Table table : tablesList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/tableDetails.fxml"));
                VBox vbox = fxmlLoader.load();
                TableDetailsController tableController = fxmlLoader.getController();
                tableController.setData(table);
                tableGridPane.add(vbox, column++, row);
                GridPane.setMargin(tableGridPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/newTableTab.fxml"));
                VBox vbox = fxmlLoader.load();
                tableGridPane.add(vbox, column++, row);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}
