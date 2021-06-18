package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Contact;
import viewModel.EditViewModel;

public class EditView {
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField patronymic;
    @FXML
    public TextField mobileNumber;
    @FXML
    public TextField homeNumber;
    @FXML
    public TextField address;
    @FXML
    public DatePicker date;
    @FXML
    public TextArea comment;

    public EditViewModel getViewModel() {
        return viewModel;
    }

    private final EditViewModel viewModel;


    @FXML
    public void initialize() {
        viewModel.getContactBuilder().nameProperty().bind(name.textProperty());
        viewModel.getContactBuilder().surnameProperty().bind(surname.textProperty());
        viewModel.getContactBuilder().patronymicProperty().bind(patronymic.textProperty());
        viewModel.getContactBuilder().addressProperty().bind(address.textProperty());
        viewModel.getContactBuilder().homeNumberProperty().bind(homeNumber.textProperty());
        viewModel.getContactBuilder().mobileNumberProperty().bind(mobileNumber.textProperty());
        viewModel.getContactBuilder().dateProperty().bind(date.valueProperty());
        viewModel.getContactBuilder().commentProperty().bind(comment.textProperty());
    }

    public EditView() {
        viewModel = EditViewModel.getInstance();
    }


    public ChangeListener<Boolean> changeListener = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
            Contact contact = viewModel.getEditableContact();
            if (contact != null) {
                name.setText(contact.getName());
                surname.setText(contact.getSurname());
                patronymic.setText(contact.getPatronymic());
                mobileNumber.setText(contact.getMobileNumber());
                homeNumber.setText(contact.getHomeNumber());
                address.setText(contact.getAddress());
                date.setValue(contact.getDate());
                comment.setText(contact.getComment());
            } else {
                name.setText("");
                surname.setText("");
                patronymic.setText("");
                mobileNumber.setText("");
                homeNumber.setText("");
                address.setText("");
                date.setValue(null);
                comment.setText("");
            }
        }
    };


    public void saveContact(ActionEvent actionEvent) {
        viewModel.saveContact();
    }

    public void cancel(ActionEvent actionEvent) {
        viewModel.cancel();
    }
}
