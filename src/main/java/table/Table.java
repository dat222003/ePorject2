package table;

public class Table {

    private String client_id,
                    table_id,
                    tableStatus;

    public Table() {
    }

    public Table(String client_id, String table_id, String tableStatus) {
        this.client_id = client_id;
        this.table_id = table_id;
        this.tableStatus = tableStatus;
    }

    public Table(String table_id, String tableStatus) {
        this.table_id = table_id;
        this.tableStatus = tableStatus;
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

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }


    @Override
    public String toString() {
        return "Table{" +
                "client_id='" + client_id + '\'' +
                ", table_id='" + table_id + '\'' +
                ", table_status='" + tableStatus + '\'' +
                '}';
    }
}
