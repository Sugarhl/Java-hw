package viewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Contact;
import model.Model;

import java.io.File;

public class MainViewModel {
    private final Model model;

    private final FileChooser fileChooser;

    public void setEditViewModel(EditViewModel editViewModel) {
        this.editViewModel = editViewModel;
    }

    private EditViewModel editViewModel;
    private Stage editStage;

    private Stage stage;

    private final Stage stageFile = new Stage();
    private int selectedIndex = -1;

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setEditStage(Stage stage) {
        this.editStage = stage;
    }

    private MainViewModel() {
        model = Model.getInstance();
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Contact files (*.cont)", "*.cont"));
        stageFile.initOwner(stage);
        stageFile.initModality(Modality.WINDOW_MODAL);
    }

    public ObservableList<Contact> getViewContactsList() {
        return model.getFilter();
    }

    public void importContacts() {
        if (!model.importContacts(fileChooser.showOpenDialog(stageFile))) {
            showAlert("Импорт контактов", "Файл с контактами пуст или заполнен некорректно.");
        }
    }

    public void exportContacts() {
        if (!model.exportContacts(fileChooser.showSaveDialog(stageFile))) {
            showAlert("Сохранение контактов", "");
        }
    }

    public void editContact() {
        if (selectedIndex < 0) {
            showAlert("Редактирование контакта.", "Вы не выбрали контакт.");
            return;
        }
        editStage.setTitle("Редактирование");
        editStage.showAndWait();
    }

    public void addContact() {
        editViewModel.setEditIndex(-1);
        editStage.setTitle("Добавлеие");
        editStage.showAndWait();
        editViewModel.setEditIndex(selectedIndex);
    }

    public void deleteContact() {
        model.deleteContact(selectedIndex);
    }

    public void saveContacts() {
        if (!model.saveContacts()) {
            showAlert("Сохранение контактов.", "Ошибка сохранения контактов.");
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public final ChangeListener<Number> numberChangeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldSelection, Number newSelection) {
            if (newSelection != null) {
                selectedIndex = (int) newSelection;
                editViewModel.setEditIndex((Integer) newSelection);
            } else {
                selectedIndex = -1;
                editViewModel.setEditIndex(-1);
            }
        }
    };

    public void showInfo() {
        showAlert("Домашку мучил: Никита Сахаров БПИ196", "Привет!Я Сахар и дизайна мне " +
                "не завезли...\nПриятной (или не очень) проверки :D\n" +
                "Советую трек. Вот вам callback\n" + "https://music.yandex.ru/album/7102882/track/51098025");
    }

    public void showLoadSucsess() {
        if (!model.getLoadSuccess()) {
            showAlert("Загрузка контактов", "Не удалось загрузить список контактов.");
        }
    }

    public void search(String text) {
        model.search(text);
    }

    public void resetFilter() {
        model.resetFilter();
    }

    public void exit() {
        stage.close();
    }

    private static MainViewModel viewModel;

    public static MainViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new MainViewModel();
        }
        return viewModel;
    }
}


