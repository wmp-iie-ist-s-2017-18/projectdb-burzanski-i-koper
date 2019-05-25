package com.mycompany.centrumkszalcenia.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class TopMenuButtonsController {

    public static final String OPEN_LEADERS_FXML = "/fxml/OpenLeaders.fxml";
    public static final String OPEN_COURSES_FXML = "/fxml/OpenCourses.fxml";
    public static final String OPEN_STUDENTS_FXML = "/fxml/OpenStudents.fxml";
    private MainController mainController;

    @FXML
    private Button leaderButton;
    @FXML
    private Button courseButton;
    @FXML
    private Button studentsButton;

    @FXML
    public void openLeaders() {
        mainController.setCenter(OPEN_LEADERS_FXML);
    }

    @FXML
    public void openCourses() {
        mainController.setCenter(OPEN_COURSES_FXML);
    }

    @FXML
    public void openStudents() {
        mainController.setCenter(OPEN_STUDENTS_FXML);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
