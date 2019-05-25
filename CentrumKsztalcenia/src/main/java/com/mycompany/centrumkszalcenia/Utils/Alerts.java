package com.mycompany.centrumkszalcenia.Utils;

import javafx.scene.control.Alert;
import java.util.ResourceBundle;

public class Alerts {

    static ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");

    public static void DatabaseConnectionAlert(String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("Alerts.databaseConnectionAlertTitle"));
        errorAlert.setHeaderText(bundle.getString("Alerts.databaseConnectionAlertHeaderText"));
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void AddRecordErrorAlert(String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("Alerts.addRecordErrorAlertTitle"));
        errorAlert.setHeaderText(bundle.getString("Alerts.addRecordErrorAlertHeaderText"));
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void UpdateRecordErrorAlert(String message){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("Alerts.updateRecordErrorAlertTitle"));
        errorAlert.setHeaderText(bundle.getString("Alerts.updateRecordErrorAlertHeaderText"));
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void DeleteRecordErrorAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("Alerts.deleteRecordErrorAlertTitle"));
        errorAlert.setHeaderText(bundle.getString("Alerts.deleteRecordErrorAlertHeaderText"));
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    public static void DataTypeErrorAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("Alerts.dataTypeErrorAlertTitle"));
        errorAlert.setHeaderText(bundle.getString("Alerts.dataTypeErrorAlertHeaderText"));
        errorAlert.setContentText(bundle.getString("Alerts.dataTypeErrorAlertContentText"));
        errorAlert.showAndWait();
    }

    public static void DoublePeselInformationAlert() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("Alerts.doublePeselInformationAlertTitle"));
        informationAlert.setHeaderText(bundle.getString("Alerts.doublePeselInformationAlertHeaderText"));
        informationAlert.setContentText(bundle.getString("Alerts.doublePeselInformationAlertContentText"));
        informationAlert.showAndWait();
    }

    public static void WrongPeselInformationAlert() {
        Alert inforamtionAlert = new Alert(Alert.AlertType.INFORMATION);
        inforamtionAlert.setTitle(bundle.getString("Alerts.wrongPeselInformationAlertTitle"));
        inforamtionAlert.setHeaderText(bundle.getString("Alerts.wrongPeselInformationAlertHeaderText"));
        inforamtionAlert.setContentText(bundle.getString("Alerts.wrongPeselInformationAlertContentText"));
        inforamtionAlert.showAndWait();
    }

    public static void WrongMailInformationAlert() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("Alerts.wrongMailInformationAlertTitle"));
        informationAlert.setHeaderText(bundle.getString("Alerts.wrongMailInformationAlertHeaderText"));
        informationAlert.setContentText(bundle.getString("Alerts.wrongMailInformationAlertContentText"));
        informationAlert.showAndWait();
    }
}
