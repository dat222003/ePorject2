package tab;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import login.UserSession;
import model.Bill;
import model.BillDB;
import model.TotalBillDB;
import table.Table;
import table.tableDB;

import java.util.Optional;

public class TableDetailsController {

    @FXML
    private JFXButton billButton;

    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private Label tableID;
    @FXML
    private JFXButton payBillButton;

    @FXML
    private JFXButton updateBillButton;

    @FXML
    private JFXButton deleteTableButton;

    public void setData(Table table) {
        tableID.setText("Table: " + table.getTable_id());
        BillDB billDB = new BillDB();
        switch (table.getTableStatus().toLowerCase()) {
            case "in used" -> {
                statusGroup.selectToggle(statusGroup.getToggles().get(1));
                if (billDB.getPendingBillWithTableId(table.getTable_id()) != null) { //bill pending for this table
                    billButton.setDisable(true);
                    updateBillButton.setDisable(false);
                    payBillButton.setDisable(false);
                } else {
                    billButton.setDisable(false);
                    updateBillButton.setDisable(true);
                }
                deleteTableButton.setDisable(true);
            }
            case "available" -> statusGroup.selectToggle(statusGroup.getToggles().get(0));
            case "ordered" -> {
                statusGroup.selectToggle(statusGroup.getToggles().get(2));
                billButton.setDisable(true);
                updateBillButton.setDisable(true);
                deleteTableButton.setDisable(true);
            }
            case "unavailable" -> {
                statusGroup.selectToggle(statusGroup.getToggles().get(3));
                billButton.setDisable(true);
                updateBillButton.setDisable(true);
            }
        }

        statusGroup.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    billButton.setDisable(newToggle != statusGroup.getToggles().get(1));
                    updateTable();
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

    void updateTable() {
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
            DialogPane dialogPane = null;
            try {
                dialogPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            NewBillController newBillController = loader.getController();
            String key = UserSession.getLocalSession();
            String userId = key.split(",")[0];
            String userName = key.split(",")[1];
            newBillController.setData(tableID.getText().split(" ")[1], userId, userName);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (newBillController.createBill()) {
                    billButton.setDisable(true);
                    updateBillButton.setDisable(false);
                    payBillButton.setDisable(false);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void updateBill(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newBill.fxml"));
            DialogPane dialogPane = null;
            try {
                dialogPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            NewBillController newBillController = loader.getController();
            String key = UserSession.getLocalSession();
            String userId = key.split(",")[0];
            String userName = key.split(",")[1];
            BillDB billDB = new BillDB();
            newBillController.setData(billDB.getPendingBillWithTableId(tableID.getText().split(" ")[1]), userId, userName);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                newBillController.updateBill();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        @FXML
        void payBill(ActionEvent event) {
        // pay bill if alert is ok
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Pay Bill");
            alert.setHeaderText("Are you sure this bill is purchased ?");
            alert.setContentText("This action cannot be undone");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    BillDB billDB = new BillDB();
                    Bill bill = billDB.getPendingBillWithTableId(tableID.getText().split(" ")[1]);
                    bill.setStatus("purchased");
                    if (billDB.updateBill(bill)) {
                        TotalBillDB totalBillDB = new TotalBillDB();
                        totalBillDB.updateTotalBill(bill);
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Update bill");
                        alert1.setHeaderText("Purchase bill successfully");
                        alert1.showAndWait();
                        payBillButton.setDisable(true);
                        updateBillButton.setDisable(true);
                        billButton.setDisable(false);
                        statusGroup.selectToggle(statusGroup.getToggles().get(0));
                        deleteTableButton.setDisable(false);
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert2.setTitle("Update bill");
                        alert2.setHeaderText("Purchase bill failed");
                        alert2.showAndWait();
                    }
                }
            });
        }


}

