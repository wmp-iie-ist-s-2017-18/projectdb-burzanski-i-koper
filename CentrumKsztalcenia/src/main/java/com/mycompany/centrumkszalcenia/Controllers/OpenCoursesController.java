package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.Utils.Alerts;
import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.models.CourseFx;
import com.mycompany.centrumkszalcenia.models.CourseModel;
import com.mycompany.centrumkszalcenia.models.LeaderFx;
import com.mycompany.centrumkszalcenia.models.LeaderModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class OpenCoursesController {

    private CourseModel courseModel;
    private LeaderModel leaderModel;
    private LeaderFx leaderFx;

    @FXML
    private TableView<CourseFx> coursesTableView;
    @FXML
    private TableColumn<CourseFx, String> nameColumn;
    @FXML
    private TableColumn<CourseFx, Integer> hoursColumn;
    @FXML
    private TableColumn<CourseFx, String> leaderColumn;
    @FXML
    private Button addButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField hoursTextField;
    @FXML
    private ComboBox<LeaderFx> addLeaderComboBox;
    @FXML
    private VBox editSectionVBox;
    @FXML
    private ComboBox<LeaderFx> editLeaderComboBox;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelEditButton;
    @FXML
    private MenuItem deleteMenuItem;

    public void initialize(){
        this.leaderModel = new LeaderModel();
        this.leaderModel.initLeaderList();
        this.addLeaderComboBox.setItems(this.leaderModel.getLeaderFxObservableList());
        this.editLeaderComboBox.setItems(this.leaderModel.getLeaderFxObservableList());

        this.courseModel = new CourseModel();
        this.courseModel.initCourseList();

        this.coursesTableView.setItems(this.courseModel.getCourseFxObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().nazwaProperty());
        this.hoursColumn.setCellValueFactory(new PropertyValueFactory<CourseFx, Integer>("liczbaGodzin"));
        this.leaderColumn.setCellValueFactory(cellData-> cellData.getValue().prowadzacyProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.hoursColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch(NumberFormatException e) {
                    Alerts.DataTypeErrorAlert();
                    return courseModel.getCourseFxObjectPropertyEdit().getLiczbaGodzin();
                }
            }
        }));
        this.leaderColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.coursesTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            this.courseModel.setCourseFxObjectPropertyEdit(newValue);
        }));

        limitedTextField();
        activeButtons();
    }

    public void limitedTextField() {

        this.nameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (nameTextField.getText().length() > 50) {
                    String s = nameTextField.getText().substring(0, 50);
                    nameTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żó*")) {
                    nameTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żó]", ""));
                }
            }
        });

        this.hoursTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    hoursTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (hoursTextField.getText().length() > 3) {
                    String s = hoursTextField.getText().substring(0, 3);
                    hoursTextField.setText(s);
                }
            }
        });
    }

    public void activeButtons(){
        this.addButton.disableProperty().bind(
                this.nameTextField.textProperty().isEmpty()
                    .or(this.hoursTextField.textProperty().isEmpty()
                        .or(this.addLeaderComboBox.valueProperty().isNull()
                        ))
        );

        this.editSectionVBox.setVisible(false);

        this.editButton.disableProperty().bind(this.editLeaderComboBox.valueProperty().isNull());
    }

    public void onActionAddLeaderComboBox() {
        this.leaderFx = this.addLeaderComboBox.getSelectionModel().getSelectedItem();
    }

    public void onActionAddButton() {
        String name = this.nameTextField.getText();
        int hours = Integer.parseInt(this.hoursTextField.getText());
        try {
            this.courseModel.saveCourse(name, hours, this.leaderFx);
        } catch (ApplicationException e) {
            Alerts.AddRecordErrorAlert(e.getMessage());
        }

        this.nameTextField.clear();
        this.hoursTextField.clear();
        this.addLeaderComboBox.getSelectionModel().clearSelection();
    }

    public void onEditCommitName(TableColumn.CellEditEvent<CourseFx, String> courseFxStringCellEditEvent) {
        this.courseModel.getCourseFxObjectPropertyEdit().setNazwa(courseFxStringCellEditEvent.getNewValue());
        try {
            this.courseModel.updateCourse();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.courseModel.initCourseList();
        }
    }

    public void onEditCommitHours(TableColumn.CellEditEvent<CourseFx, Integer> courseFxIntegerCellEditEvent) {
        this.courseModel.getCourseFxObjectPropertyEdit().setLiczbaGodzin(courseFxIntegerCellEditEvent.getNewValue());
        try {
            this.courseModel.updateCourse();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.courseModel.initCourseList();
        }
    }

    public void onEditStartLeader(TableColumn.CellEditEvent<CourseFx, String> courseFxStringCellEditEvent) {
        this.courseModel.getCourseFxObjectPropertyEdit().setProwadzacy(courseFxStringCellEditEvent.getOldValue());
        this.editSectionVBox.setVisible(true);
    }

    public void onEditCommitLeader(TableColumn.CellEditEvent<CourseFx, String> courseFxStringCellEditEvent) {
        this.courseModel.initCourseList();
        this.editSectionVBox.setVisible(false);
    }

    public void onActionEditComboBox() {
        this.leaderFx = this.editLeaderComboBox.getSelectionModel().getSelectedItem();
    }

    public void onActionEditButton() {
        try {
            this.courseModel.updateLeaderCourse(this.leaderFx);
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
        }
        this.editLeaderComboBox.getSelectionModel().clearSelection();
        this.editSectionVBox.setVisible(false);
    }

    public void onActionCancelEditButton() {
        this.editSectionVBox.setVisible(false);
        this.courseModel.initCourseList();
    }

    public void onActionDeleteMenuItem() {
        try {
            this.courseModel.deleteCourse();
        } catch (ApplicationException e) {
            Alerts.DeleteRecordErrorAlert(e.getMessage());
        }
    }
}
