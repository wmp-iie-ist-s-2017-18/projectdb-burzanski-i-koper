package com.mycompany.centrumkszalcenia.Controllers;

import com.mycompany.centrumkszalcenia.Utils.Alerts;
import com.mycompany.centrumkszalcenia.Utils.ApplicationException;
import com.mycompany.centrumkszalcenia.Utils.MailValidator;
import com.mycompany.centrumkszalcenia.Utils.PeselValidator;
import com.mycompany.centrumkszalcenia.models.CourseFx;
import com.mycompany.centrumkszalcenia.models.CourseModel;
import com.mycompany.centrumkszalcenia.models.StudentFx;
import com.mycompany.centrumkszalcenia.models.StudentModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class OpenStudentsController {

    private StudentModel studentModel;
    private CourseModel courseModel;
    private CourseFx courseFx;
    private PeselValidator peselValidator;
    private MailValidator mailValidator;

    @FXML
    private TableView<StudentFx> studentsTableView;
    @FXML
    private TableColumn<StudentFx, String> nameColumn;
    @FXML
    private TableColumn<StudentFx, String> surnameColumn;
    @FXML
    private TableColumn<StudentFx, String> peselColumn;
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
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField peselTextField;
    @FXML
    private TextField placeTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField numberOfHouseTextField;
    @FXML
    private TextField zipcodeTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField mailTextField;
    @FXML
    private ComboBox<CourseFx> courseComboBox;
    @FXML
    private Button addButton;
    @FXML
    private VBox editSectionVBox;
    @FXML
    private ComboBox<CourseFx> editCourseComboBox;
    @FXML
    private Button editButton;
    @FXML
    private Button cancelEditButton;
    @FXML
    private MenuItem deleteMenuItem;

    public void initialize(){
        this.courseModel = new CourseModel();
        this.courseModel.initCourseList();
        this.courseComboBox.setItems(this.courseModel.getCourseFxObservableList());
        this.editCourseComboBox.setItems(this.courseModel.getCourseFxObservableList());

        this.studentModel = new StudentModel();
        this.studentModel.initStudentList();

        this.studentsTableView.setItems(this.studentModel.getStudentFxObservableList());
        this.nameColumn.setCellValueFactory(cellData-> cellData.getValue().imieProperty());
        this.surnameColumn.setCellValueFactory(cellData-> cellData.getValue().nazwiskoProperty());
        this.peselColumn.setCellValueFactory(cellData-> cellData.getValue().peselProperty());
        this.placeColumn.setCellValueFactory(cellData-> cellData.getValue().miejscowoscProperty());
        this.streetColumn.setCellValueFactory(cellData-> cellData.getValue().ulicaProperty());
        this.numberOfHouseColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("nrDomu"));
        this.zipcodeColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("kodPocztowy"));
        this.phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<StudentFx, Integer>("nrTelefonu"));
        this.mailColumn.setCellValueFactory(cellData-> cellData.getValue().mailProperty());
        this.courseColumn.setCellValueFactory(cellData-> cellData.getValue().kursProperty());

        this.nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.surnameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.peselColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.placeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.streetColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.numberOfHouseColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch(NumberFormatException e) {
                    Alerts.DataTypeErrorAlert();
                    return studentModel.getStudentFxObjectPropertyEdit().getNrDomu();
                }
            }
        }));
        this.zipcodeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch(NumberFormatException e) {
                    Alerts.DataTypeErrorAlert();
                    return studentModel.getStudentFxObjectPropertyEdit().getKodPocztowy();
                }
            }
        }));
        this.phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter() {
            @Override
            public Integer fromString(String value) {
                try {
                    return super.fromString(value);
                } catch(NumberFormatException e) {
                    Alerts.DataTypeErrorAlert();
                    return studentModel.getStudentFxObjectPropertyEdit().getNrTelefonu();
                }
            }
        }));
        this.mailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.courseColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.studentsTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->  {
            this.studentModel.setStudentFxObjectPropertyEdit(newValue);
        }));

        limitedTextField();
        activeButtons();
    }

    public void limitedTextField() {
        this.nameTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (nameTextField.getText().length() > 30) {
                    String s = nameTextField.getText().substring(0, 30);
                    nameTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żĄ-ŻóÓ*")) {
                    nameTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żĄ-ŻóÓ]", ""));
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
                if (!newValue.matches("\\sa-zA-Zą-żĄ-ŻóÓ*")) {
                    surnameTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żĄ-ŻóÓ]", ""));
                }
            }
        });

        this.peselTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    peselTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (peselTextField.getText().length() > 11) {
                    String s = peselTextField.getText().substring(0, 11);
                    peselTextField.setText(s);
                }
            }
        });

        this.placeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (placeTextField.getText().length() > 30) {
                    String s = placeTextField.getText().substring(0, 30);
                    placeTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żĄ-ŻóÓ*")) {
                    placeTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żĄ-ŻóÓ]", ""));
                }
            }
        });

        this.streetTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (streetTextField.getText().length() > 30) {
                    String s = streetTextField.getText().substring(0, 30);
                    streetTextField.setText(s);
                }
                if (!newValue.matches("\\sa-zA-Zą-żĄ-ŻóÓ*")) {
                    streetTextField.setText(newValue.replaceAll("[^\\sa-zA-Zą-żĄ-ŻóÓ]", ""));
                }
            }
        });

        this.numberOfHouseTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberOfHouseTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (numberOfHouseTextField.getText().length() > 4) {
                    String s = numberOfHouseTextField.getText().substring(0, 4);
                    numberOfHouseTextField.setText(s);
                }
            }
        });

        this.zipcodeTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    zipcodeTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (zipcodeTextField.getText().length() > 5) {
                    String s = zipcodeTextField.getText().substring(0, 5);
                    zipcodeTextField.setText(s);
                }
            }
        });

        this.phoneNumberTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (phoneNumberTextField.getText().length() > 9) {
                    String s = phoneNumberTextField.getText().substring(0, 9);
                    phoneNumberTextField.setText(s);
                }
            }
        });

        this.mailTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (mailTextField.getText().length() > 50) {
                    String s = mailTextField.getText().substring(0, 50);
                    mailTextField.setText(s);
                }
            }
        });
    }

    public void activeButtons(){
        this.addButton.disableProperty().bind(
                this.nameTextField.textProperty().isEmpty()
                .or(this.surnameTextField.textProperty().isEmpty()
                .or(this.peselTextField.textProperty().isEmpty()
                .or(this.placeTextField.textProperty().isEmpty()
                .or(this.streetTextField.textProperty().isEmpty()
                .or(this.numberOfHouseTextField.textProperty().isEmpty()
                .or(this.zipcodeTextField.textProperty().isEmpty()
                .or(this.phoneNumberTextField.textProperty().isEmpty()
                .or(this.mailTextField.textProperty().isEmpty()
                .or(this.courseComboBox.valueProperty().isNull()
                 )))))))))
        );

        this.editSectionVBox.setVisible(false);

        this.editButton.disableProperty().bind(this.editCourseComboBox.valueProperty().isNull());
}

    public void onActionCourseComboBox() {
        this.courseFx = this.courseComboBox.getSelectionModel().getSelectedItem();
    }

    public void onActionAddButton() {
        this.peselValidator = new PeselValidator(this.peselTextField.getText());
        boolean peselIsValid = this.peselValidator.isValid();
        if (peselIsValid == true) {
            this.mailValidator = new MailValidator();
            boolean mailIsValid = this.mailValidator.validateEmailAddress(this.mailTextField.getText());

            if (mailIsValid == true) {
                this.studentModel.getStudentFxObjectProperty().setPesel(this.peselTextField.getText());
                this.studentModel.getStudentFxObjectProperty().setImie(this.nameTextField.getText());
                this.studentModel.getStudentFxObjectProperty().setNazwisko(this.surnameTextField.getText());
                this.studentModel.getStudentFxObjectProperty().setMiejscowosc(this.placeTextField.getText());
                this.studentModel.getStudentFxObjectProperty().setUlica(this.streetTextField.getText());
                this.studentModel.getStudentFxObjectProperty().setNrDomu(Integer.parseInt(this.numberOfHouseTextField.getText()));
                this.studentModel.getStudentFxObjectProperty().setKodPocztowy(Integer.parseInt(this.zipcodeTextField.getText()));
                this.studentModel.getStudentFxObjectProperty().setNrTelefonu(Integer.parseInt(this.phoneNumberTextField.getText()));
                this.studentModel.getStudentFxObjectProperty().setMail(this.mailTextField.getText());

                int answer = -1;
                try {
                    answer = this.studentModel.saveStudent(courseFx);
                } catch (ApplicationException e) {
                    Alerts.AddRecordErrorAlert(e.getMessage());
                }

                if (answer == 1) {
                    Alerts.DoublePeselInformationAlert();
                } if (answer == -1) {

                }   else {
                    this.nameTextField.clear();
                    this.surnameTextField.clear();
                    this.peselTextField.clear();
                    this.placeTextField.clear();
                    this.streetTextField.clear();
                    this.numberOfHouseTextField.clear();
                    this.zipcodeTextField.clear();
                    this.phoneNumberTextField.clear();
                    this.mailTextField.clear();
                    this.courseComboBox.getSelectionModel().clearSelection();
                    this.studentModel.initStudentList();
                }
            } else {
                Alerts.WrongMailInformationAlert();
            }
        } else {
            Alerts.WrongPeselInformationAlert();
        }
    }

    public void onEditCommitName(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setImie(studentFxStringCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitSurname(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setNazwisko(studentFxStringCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitPesel(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.peselValidator = new PeselValidator(studentFxStringCellEditEvent.getNewValue());
        boolean valid = this.peselValidator.isValid();
        if (valid == true) {
            this.studentModel.getStudentFxObjectPropertyEdit().setPesel(studentFxStringCellEditEvent.getNewValue());
            try {
                this.studentModel.updateStudent();
            } catch (ApplicationException e) {
                Alerts.UpdateRecordErrorAlert(e.getMessage());
                this.studentModel.initStudentList();
            }
        } else {
            Alerts.WrongPeselInformationAlert();
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitPlace(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setMiejscowosc(studentFxStringCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitStreet(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setUlica(studentFxStringCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitNumberOfHouse(TableColumn.CellEditEvent<StudentFx, Integer> studentFxIntegerCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setNrDomu(studentFxIntegerCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitZipcode(TableColumn.CellEditEvent<StudentFx, Integer> studentFxIntegerCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setKodPocztowy(studentFxIntegerCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitPhoneNumber(TableColumn.CellEditEvent<StudentFx, Integer> studentFxIntegerCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setNrTelefonu(studentFxIntegerCellEditEvent.getNewValue());
        try {
            this.studentModel.updateStudent();
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
            this.studentModel.initStudentList();
        }
    }

    public void onEditCommitMail(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.mailValidator = new MailValidator();
        boolean mailIsValid = this.mailValidator.validateEmailAddress(studentFxStringCellEditEvent.getNewValue());

        if (mailIsValid == true) {
            this.studentModel.getStudentFxObjectPropertyEdit().setMail(studentFxStringCellEditEvent.getNewValue());
            try {
                this.studentModel.updateStudent();
            } catch (ApplicationException e) {
                Alerts.UpdateRecordErrorAlert(e.getMessage());
                this.studentModel.initStudentList();
            }
        } else {
            Alerts.WrongMailInformationAlert();
            this.studentModel.initStudentList();
        }
    }

    public void onEditStartCourse(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.getStudentFxObjectPropertyEdit().setKurs(studentFxStringCellEditEvent.getOldValue());
        this.editSectionVBox.setVisible(true);
    }

    public void onEditCommitCourse(TableColumn.CellEditEvent<StudentFx, String> studentFxStringCellEditEvent) {
        this.studentModel.initStudentList();
        this.editSectionVBox.setVisible(false);
    }

    public void onActionEditCourseComboBox() {
        this.courseFx = this.editCourseComboBox.getSelectionModel().getSelectedItem();
    }

    public void onActionEditButton() {
        try {
            this.studentModel.updateCourseStudent(courseFx);
        } catch (ApplicationException e) {
            Alerts.UpdateRecordErrorAlert(e.getMessage());
        }
        this.editCourseComboBox.getSelectionModel().clearSelection();
        this.editSectionVBox.setVisible(false);
    }

    public void onActionCancelEditButton() {
        this.editSectionVBox.setVisible(false);
        this.studentModel.initStudentList();
    }

    public void onActionDeleteMenuItem() {
        try {
            this.studentModel.deleteStudent();
        } catch (ApplicationException e) {
            Alerts.DeleteRecordErrorAlert(e.getMessage());
        }
    }
}
