package tab;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import table.tableDB;

public class NewTableTabController {

    @FXML
    private Button addTableButton;

    private final table.tableDB tableDB = new tableDB();
    @FXML
    void addTable(ActionEvent event) {
        if (tableDB.addTable()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("New Table Created");
            alert.setContentText("You will need to refresh the page to see the new table");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Action failed");
            alert.setContentText("Table wasn't Created");
            alert.showAndWait();
        }
    }


}
