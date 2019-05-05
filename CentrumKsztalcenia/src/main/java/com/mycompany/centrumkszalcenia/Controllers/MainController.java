package com.mycompany.centrumkszalcenia.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TopMenuButtonsController topMenuButtonsController;

    @FXML
    private void initialize(){
        topMenuButtonsController.setMainController(this);
    }

    @FXML
    public void setCenter(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlPath));

        ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainBorderPane.setCenter(parent);
    }

    public void closeApplication() {
        HibernateUtil.getSessionFactory().close();
        Platform.exit();
        System.exit(0);
    }
}
