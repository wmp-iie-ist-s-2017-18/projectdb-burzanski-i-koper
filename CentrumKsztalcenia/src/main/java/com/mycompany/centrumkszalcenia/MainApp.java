package com.mycompany.centrumkszalcenia;

import com.mycompany.centrumkszalcenia.database.HibernateUtil;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.ResourceBundle;


public class MainApp extends Application {

    public static final String MAIN_SCENE_FXML = "/fxml/MainScene.fxml";

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MAIN_SCENE_FXML));

        ResourceBundle bundle = ResourceBundle.getBundle("bundles.messages");
        loader.setResources(bundle);

        BorderPane mainBorderPane = loader.load();
        Scene scene = new Scene(mainBorderPane);
        
        stage.setTitle(bundle.getString("title.application"));
        stage.setScene(scene);
        stage.show();

        Session session = HibernateUtil.getSessionFactory().openSession();

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
