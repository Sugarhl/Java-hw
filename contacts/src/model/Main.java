package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.EditView;
import view.MainView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../windows/mainMenu.fxml"));
        Parent root = loader.load();
        MainView view = loader.getController();
        FXMLLoader subLoader = new FXMLLoader(getClass().getResource("../windows/editWindow.fxml"));
        Parent subRoot = subLoader.load();
        EditView subview = subLoader.getController();

        Stage editStage = new Stage();
        editStage.initOwner(primaryStage);
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.showingProperty().addListener(subview.changeListener);
        editStage.setWidth(390);
        editStage.setHeight(400);
        editStage.setResizable(false);
        editStage.setScene(new Scene(subRoot));

        view.getViewModel().setStage(primaryStage);
        view.getViewModel().setEditStage(editStage);
        view.getViewModel().setEditViewModel(subview.getViewModel());
        subview.getViewModel().setStage(editStage);

        primaryStage.setOnCloseRequest(windowEvent -> {
            view.exit(null);
        });
        primaryStage.setTitle("Contacts");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);

        primaryStage.show();

    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
