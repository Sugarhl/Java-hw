package view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Contact;
import viewModel.MainViewModel;

import java.util.Date;

public class MainView {

    public MainViewModel getViewModel() {
        return viewModel;
    }

    private final MainViewModel viewModel;

    public MainView() {
        viewModel = MainViewModel.getInstance();
    }

    @FXML
    public TableView<Contact> tableView;
    @FXML
    public TextField search;
    @FXML
    public TableColumn<Contact, Date> date;
    @FXML
    public MenuItem edit;
    @FXML
    private TableColumn<Contact, String> nameColumn;

    @FXML
    private TableColumn<Contact, String> surnameColumn;
    @FXML
    private TableColumn<Contact, String> patronymicColumn;
    @FXML
    private TableColumn<Contact, String> numberColumn;
    @FXML
    private TableColumn<Contact, String> addressColumn;
    @FXML
    public TableColumn<Contact, Date> dateColumn;
    @FXML
    private TableColumn<Contact, String> commentColumn;


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        patronymicColumn.setCellValueFactory(new PropertyValueFactory<>("patronymic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));


        search.textProperty().addListener((observableValue, oldValue, newValue) -> {
                    if (newValue.equals(""))
                        viewModel.resetFilter();
                }
        );

        tableView.getSelectionModel().selectedIndexProperty()
                .addListener(viewModel.numberChangeListener);

        tableView.getSelectionModel().selectedIndexProperty()
                .addListener((observableValue, oldSelection, newSelection) -> {
                    edit.setDisable(newSelection == null);
                });
        tableView.setItems(viewModel.getViewContactsList());
        viewModel.showLoadSucsess();
    }


    public void addContact(ActionEvent actionEvent) {
        viewModel.addContact();
    }

    public void deleteContact(ActionEvent actionEvent) {
        viewModel.deleteContact();
    }

    public void editContact(ActionEvent actionEvent) {
        viewModel.editContact();
    }

    public void search(ActionEvent actionEvent) {
        viewModel.search(search.getText());
    }


    public void importContacts(ActionEvent actionEvent) {
        viewModel.importContacts();
    }

    public void exportContacts(ActionEvent actionEvent) {
        viewModel.exportContacts();
    }

    public void exit(ActionEvent actionEvent) {
        viewModel.saveContacts();
        viewModel.exit();
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER){
            viewModel.search(search.getText());
        }
    }

    public void infoAbout(Event actionEvent) {
        viewModel.showInfo();
    }
}
