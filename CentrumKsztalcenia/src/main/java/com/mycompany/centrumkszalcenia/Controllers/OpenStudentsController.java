package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.models.StudentFx;
import com.mycompany.centrumkszalcenia.models.StudentModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OpenStudentsController {
    private StudentModel studentModel;

    @FXML
    private TableView<StudentFx> studentsTableView;
    @FXML
    private TableColumn<StudentFx, String> nameColumn;
    @FXML
    private TableColumn<StudentFx, String> surnameColumn;
    @FXML
    private TableColumn<StudentFx, Long> peselColumn;
    @FXML
    private TableColumn<StudentFx, String> placeColumn;
    @FXML
    private TableColumn<StudentFx, String> streetColumn;
    @FXML
    private TableColumn<StudentFx, Integer> numberOfHouseColumn;
    @FXML
    private TableColumn<StudentFx, Integer> zipcodeColumn;
    @FXML
    private TableColumn<StudentFx, Integer> phoneNumberColumn;
    @FXML
    private TableColumn<StudentFx, String> mailColumn;
    @FXML
    private TableColumn<StudentFx, String> courseColumn;

    public void initialize(){
        this.studentModel = new StudentModel();
        this.studentModel.initStudentList();

        this.studentsTableView.setItems(this.studentModel.getStudentFxObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().imieProperty());
        this.surnameColumn.setCellValueFactory(cellData-> cellData.getValue().nazwiskoProperty());
        this.peselColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Long>("pesel"));
        this.placeColumn.setCellValueFactory(cellData-> cellData.getValue().miejscowoscProperty());
        this.streetColumn.setCellValueFactory(cellData-> cellData.getValue().ulicaProperty());
        this.numberOfHouseColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("nrDomu"));
        this.zipcodeColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("kodPocztowy"));
        this.phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("nrTelefonu"));
        this.mailColumn.setCellValueFactory(cellData-> cellData.getValue().mailProperty());
        this.courseColumn.setCellValueFactory(cellData-> cellData.getValue().kursProperty());

    }

}
