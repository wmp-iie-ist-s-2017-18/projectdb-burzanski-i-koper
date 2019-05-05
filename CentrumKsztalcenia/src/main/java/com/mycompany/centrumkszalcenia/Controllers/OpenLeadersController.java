package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.models.LeaderFx;
import com.mycompany.centrumkszalcenia.models.LeaderModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;


public class OpenLeadersController {

    private LeaderModel leaderModel;

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
    }

    public void onActionAddButton() {
        String name = nameTextField.getText();
        String surname = surnameTextField.getText();
        String mail = mailTextField.getText();
        this.leaderModel.saveLeader(name, surname, mail);

        nameTextField.clear();
        surnameTextField.clear();
        mailTextField.clear();
    }

    public void onEditCommitName(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.leaderModel.getLeaderFxObjectPropertyEdit().setImie(leaderFxStringCellEditEvent.getNewValue());
        this.leaderModel.updateLeader();
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.leaderModel.getLeaderFxObjectPropertyEdit().setNazwisko(leaderFxStringCellEditEvent.getNewValue());
        this.leaderModel.updateLeader();
    }

    public void onEditCommitMail(TableColumn.CellEditEvent<LeaderFx, String> leaderFxStringCellEditEvent) {
        this.leaderModel.getLeaderFxObjectPropertyEdit().setMail(leaderFxStringCellEditEvent.getNewValue());
        this.leaderModel.updateLeader();
    }

    public void onActionDeleteLeader() {
        this.leaderModel.deleteLeader();
    }
}
