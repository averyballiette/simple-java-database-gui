package com.example.cryptogui.controller;


import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.cryptogui.model.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private TableView<List<String>> resultsTable;

    @FXML
    private TextArea queryTextArea;

    private final DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void initialize() {
        resultsTable.setPlaceholder(new Label("No data loaded"));
    }

    @FXML
    public void handleRunQuery() {
        String query = queryTextArea.getText().trim();
        if (query.isEmpty()) return;

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            loadResultSetToTable(rs);

        } catch (SQLException e) {
            showError("Error executing query: " + e.getMessage());
        }
    }

    @FXML
    public void handleLoadTransactions() {
        String query = "SELECT * FROM Transactions";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            loadResultSetToTable(rs);

        } catch (SQLException e) {
            showError("Failed to load Transactions: " + e.getMessage());
        }
    }

    @FXML
    public void handleLoadWallets() {
        String query = "SELECT * FROM Wallets";
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            loadResultSetToTable(rs);

        } catch (SQLException e) {
            showError("Failed to load Wallets: " + e.getMessage());
        }
    }

    @FXML
    public void handleClearTable() {
        resultsTable.getItems().clear();
        resultsTable.getColumns().clear();
    }


    private void loadResultSetToTable(ResultSet rs) throws SQLException {
        resultsTable.getItems().clear();
        resultsTable.getColumns().clear();

        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        for (int c = 1; c <= columnCount; c++) {
            final int colIndex = c - 1;
            TableColumn<List<String>, String> column = new TableColumn<>(meta.getColumnName(c));
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(colIndex)));
            resultsTable.getColumns().add(column);
        }

        while (rs.next()) {
            List<String> row = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getString(i));
            }
            resultsTable.getItems().add(row);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
