package acmi.l2.clientmod.view;

import acmi.l2.clientmod.io.UnrealPackage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class TestApp extends Application implements Initializable {
    @FXML
    private ListView<UnrealPackage.ExportEntry> list;
    @FXML
    private Label format;
    @FXML
    private Label width;
    @FXML
    private Label height;
    @FXML
    private ImageView image;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testApp.fxml"));
        loader.setControllerFactory(param -> TestApp.this);

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
