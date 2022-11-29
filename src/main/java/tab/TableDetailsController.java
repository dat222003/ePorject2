package tab;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import login.UserSession;
import table.Table;
import table.tableDB;

public class TableDetailsController {

    @FXML
    private JFXButton billButton;

    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private Label tableID;
    @FXML
    private JFXButton updateTableButton;


    public void setData(Table table) {
        tableID.setText("Table: " + table.getTable_id());
        switch (table.getTableStatus().toLowerCase()) {
            case "in used" -> {
                statusGroup.selectToggle(statusGroup.getToggles().get(1));
                billButton.setDisable(false);
            }
            case "available" -> statusGroup.selectToggle(statusGroup.getToggles().get(0));
            case "ordered" -> statusGroup.selectToggle(statusGroup.getToggles().get(2));
            case "unavailable" -> statusGroup.selectToggle(statusGroup.getToggles().get(3));
        }

        statusGroup.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    billButton.setDisable(newToggle != statusGroup.getToggles().get(1));
//                    updateTableButton.setDisable(billButton.isDisable());
                }
        );
    }

    private final table.tableDB tableDB = new tableDB();
    @FXML
    void deleteTable(ActionEvent event) {
        String str = tableID.getText();
        String id = str.split(" ")[1];
        if (tableDB.deleteTable(id)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Table: " + id +" Deleted");
            alert.setContentText("You will need to refresh the page to see table changes");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Action failed");
            alert.setContentText("Table wasn't Deleted");
            alert.showAndWait();
        }

    }

    @FXML
    void updateTable(ActionEvent event) {
        String str = tableID.getText();
        String id = str.split(" ")[1];
        RadioButton selectedRadioButton = (RadioButton) statusGroup.getSelectedToggle();
        String status = selectedRadioButton.getText();
        Table table = new Table(id, status);
        if (tableDB.updateTable(table)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Table: " + id +" Updated");
            alert.setContentText("You will need to refresh the page to see table changes");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Message");
            alert.setHeaderText("Action failed");
            alert.setContentText("Table wasn't Updated");
            alert.showAndWait();
        }
    }

    @FXML
    void newBill(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newBill.fxml"));
            Parent root = loader.load();
            NewBillController controller = loader.getController();
            String key = UserSession.getLocalSession();
            String userId = key.split(",")[0];
            String userName = key.split(",")[1];
            controller.setData(tableID.getText().split(" ")[1], userId, userName);
            Stage stage = new Stage();
            stage.setTitle("New Bill");
            stage.setMinWidth(1200);
            stage.setMinHeight(600);
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

