package viewModel;


import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Contact;
import model.ContactBuilder;
import model.Model;

public class EditViewModel {
    private final Model model;
    public static final String ERROR = "Вы заполнили не все поля.\n" +
            "Обязательные поля: имя фамилия и одиз из номеров." +
            "\nПри заполении первых трех полай не используйте пробелы.";

    public ContactBuilder getContactBuilder() {
        return contactBuilder;
    }

    private final ContactBuilder contactBuilder = new ContactBuilder();


    public void setEditIndex(int editAction) {
        editIndex = editAction;
    }

    private int editIndex = -1;


    public Contact getEditableContact() {
        return model.getContact(editIndex);
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Stage stage;

    private EditViewModel() {
        model = Model.getInstance();
    }

    public void cancel() {
        stage.close();
    }

    public void saveContact() {
        Contact contact = new Contact(contactBuilder);
        if (editIndex >= 0) {
            if (!model.editContact(editIndex, contact)) {
                showAlert();
            } else {
                stage.close();
            }
        } else {
            if (!model.addContact(contact)) {
                showAlert();
            } else {
                stage.close();
            }
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setHeaderText("Сохранение контакта.");
        alert.setContentText(ERROR);
        alert.showAndWait();
    }

    private static EditViewModel viewModel;

    public static EditViewModel getInstance() {
        if (viewModel == null) {
            viewModel = new EditViewModel();
        }
        return viewModel;
    }
}
