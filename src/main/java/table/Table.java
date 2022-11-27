package table;

public class Table {

    private String client_id,
                    table_id,
                    tableName,
                    tableStatus,
                    bill_id;

    public Table() {
    }

    public Table(String client_id, String table_id, String tableName, String tableStatus, String bill_id) {
        this.client_id = client_id;
        this.table_id = table_id;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.bill_id = bill_id;
    }

    public Table(String table_id, String tableName, String tableStatus, String bill_id) {
        this.table_id = table_id;
        this.tableName = tableName;
        this.tableStatus = tableStatus;
        this.bill_id = bill_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    @Override
    public String toString() {
        return "Table{" +
                "client_id='" + client_id + '\'' +
                ", table_id='" + table_id + '\'' +
                ", table_name='" + tableName + '\'' +
                ", table_status='" + tableStatus + '\'' +
                ", bill_id='" + bill_id + '\'' +
                '}';
    }
}
