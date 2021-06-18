package model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;

public class ContactBuilder implements Serializable {
    private StringProperty name;
    private StringProperty surname;
    private StringProperty patronymic;
    private StringProperty homeNumber;
    private StringProperty mobileNumber;
    private StringProperty address;
    private ObjectProperty<LocalDate> date;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public StringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public String getHomeNumber() {
        return homeNumber.get();
    }

    public StringProperty homeNumberProperty() {
        return homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber.set(homeNumber);
    }

    public String getMobileNumber() {
        return mobileNumber.get();
    }

    public StringProperty mobileNumberProperty() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.set(mobileNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    private StringProperty comment;

    public ContactBuilder() {
        name = new SimpleStringProperty();
        surname = new SimpleStringProperty();
        patronymic = new SimpleStringProperty();
        mobileNumber = new SimpleStringProperty();
        homeNumber = new SimpleStringProperty();
        address = new SimpleStringProperty();
        date = new SimpleObjectProperty<>();
        comment = new SimpleStringProperty();
    }


}
