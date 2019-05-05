package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.models.CourseFx;
import com.mycompany.centrumkszalcenia.models.CourseModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class OpenCoursesController {

    private CourseModel courseModel;

    @FXML
    private TableView<CourseFx> coursesTableView;
    @FXML
    private TableColumn<CourseFx, String> nameColumn;
    @FXML
    private TableColumn<CourseFx, Integer> hoursColumn;
    @FXML
    private TableColumn<CourseFx, String> leaderColumn;

    public void initialize(){
        this.courseModel = new CourseModel();
        this.courseModel.initCourseList();

        this.coursesTableView.setItems(this.courseModel.getCourseFxObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().nazwaProperty());
        this.hoursColumn.setCellValueFactory(new PropertyValueFactory<CourseFx, Integer>("liczbaGodzin"));
        this.leaderColumn.setCellValueFactory(cellData-> cellData.getValue().prowadzacyProperty());

    }
}
