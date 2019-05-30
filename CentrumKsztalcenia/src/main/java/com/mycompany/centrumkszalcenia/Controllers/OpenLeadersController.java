package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.Utils.Alerts;
import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.Utils.MailValidator;
import com.mycompany.centrumkszalcenia.models.LeaderFx;
import com.mycompany.centrumkszalcenia.models.LeaderModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;


public class OpenLeadersController {

    private LeaderModel leaderModel;
    private MailValidator mailValidator;

    @FXML
    private TableView<LeaderFx> leaderTableView;
    @FXML
    private TableColumn<LeaderFx, String> nameColumn;
    @FXML
    private TableColumn<LeaderFx, String> surnameColumn;
    @FXML
    private TableColumn<LeaderFx, String> mailColumn;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private MenuItem deleteMenuItem;

    public void initialize(){
        this.leaderModel = new LeaderModel();
        this.leaderModel.initLeaderList();

        this.leaderTableView.setItems(this.leaderModel.getLeaderFxObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().imieProperty());
        this.surnameColumn.setCellValueFactory(cellData-> cellData.getValue().nazwiskoProperty());
        this.mailColumn.setCellValueFactory(cellData-> cellData.getValue().mailProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.mailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.leaderTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.leaderModel.setLeaderFxObjectPropertyEdit(newValue);
        });

        addButton.disableProperty().bind(nameTextField.textProperty().isEmpty()
        .or(surnameTextField.textProperty().isEmpty()
        .or(mailTextField.textProperty().isEmpty())));

        limitedTextField();
    }

    public void limitedTextField () {
        this.nameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (nameTextField.getText().length() > 30) {
                    String s = nameTextField.getText().substring(0, 30);
                    nameTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żó*")) {
                    nameTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żó]", ""));
                }
            }
        });

        this.surnameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (surnameTextField.getText().length() > 30) {
                    String s = surnameTextField.getText().substring(0, 30);
                    surnameTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żó*")) {
                    surnameTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żó]", ""));
                }
            }
        });

        this.mailTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (mailTextField.getText().length() > 30) {
                    String s = mailTextField.getText().substring(0, 30);
                    mailTextField.setText(s);
                }
            }
        });
    }

    public void onActionAddButton() {
        this.mailValidator = new MailValidator();
        boolean isValid = this.mailValidator.validateEmailAddress(this.mailTextField.getText());

        if (isValid == true) {
            String name = this.nameTextField.getText();
            String surname = this.surnameTextField.getText();
            String mail = this.mailTextField.getText();

            try {
                this.leaderModel.saveLeader(name, surname, mail);

                this.nameTextField.clear();
                this.surnameTextField.clear();
                this.mailTextField.clear();
            } catch (ApplicationException e) {
                Alerts.AddRecordErrorAlert(e.getMessage());
            }
        } else {
            Alerts.WrongMailInformationAlert();
        }
    }

    public void onEditCommitName(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.leaderModel.getLeaderFxObjectPropertyEdit().setImie(leaderFxStringCellEditEvent.getNewValue());
        try {
            this.leaderModel.updateLeader();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.leaderModel.initLeaderList();
        }
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.leaderModel.getLeaderFxObjectPropertyEdit().setNazwisko(leaderFxStringCellEditEvent.getNewValue());
        try {
            this.leaderModel.updateLeader();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.leaderModel.initLeaderList();
        }
    }

    public void onEditCommitMail(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.mailValidator = new MailValidator();
        boolean isValid = this.mailValidator.validateEmailAddress(leaderFxStringCellEditEvent.getNewValue());

        if (isValid == true) {
            this.leaderModel.getLeaderFxObjectPropertyEdit().setMail(leaderFxStringCellEditEvent.getNewValue());
            try {
                this.leaderModel.updateLeader();
            } catch (ApplicationException e) {
                Alerts.UpdateRecordErrorAlert(e.getMessage());
                this.leaderModel.initLeaderList();
            }
        } else {
            Alerts.WrongMailInformationAlert();
            this.leaderModel.initLeaderList();
        }
    }

    public void onActionDeleteLeader() {
        try {
            this.leaderModel.deleteLeader();
        } catch (ApplicationException e) {
            Alerts.DeleteRecordErrorAlert(e.getMessage());
        }
    }
}
