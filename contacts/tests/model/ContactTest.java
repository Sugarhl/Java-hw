package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactTest {

    ContactBuilder contactBuilder = new ContactBuilder();

    private List<Contact> Incorrect() {
        ArrayList<Contact> contacts = new ArrayList<>();
        contactBuilder.nameProperty().set("123");
        contactBuilder.surnameProperty().set("113assdfD");
        contactBuilder.patronymicProperty().set("113assdfD");
        contactBuilder.mobileNumberProperty().set("12312312312");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Ybrb sdf");
        contactBuilder.surnameProperty().set("sdfsdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contactBuilder.mobileNumberProperty().set("12312312312");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Ybrbsdf");
        contactBuilder.surnameProperty().set("sdfsdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Ybrbsdf");
        contactBuilder.surnameProperty().set("sdfsdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contactBuilder.homeNumberProperty().set("dsfsdfsdf");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Ybrbsdf");
        contactBuilder.surnameProperty().set("sdfsdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contactBuilder.homeNumberProperty().set("dsfsdfsdf");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("sdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contactBuilder.mobileNumberProperty().set("12312312312");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.surnameProperty().set("sdfsdf");
        contactBuilder.patronymicProperty().set("dsfsdfsdf");
        contactBuilder.mobileNumberProperty().set("12312312312");
        contacts.add(new Contact(contactBuilder));

        return contacts;
    }


    private List<Contact> Correct() {
        ArrayList<Contact> contacts = new ArrayList<>();

        contactBuilder.nameProperty().set("Никита");
        contactBuilder.surnameProperty().set("Сахаров");
        contactBuilder.patronymicProperty().set("Денисович");
        contactBuilder.homeNumberProperty().set("89284602946");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Nikita");
        contactBuilder.surnameProperty().set("Sakharov");
        contactBuilder.patronymicProperty().set("Denisovich");
        contactBuilder.homeNumberProperty().set("89284602946");
        contacts.add(new Contact(contactBuilder));

        contactBuilder.nameProperty().set("Никита");
        contactBuilder.surnameProperty().set("Сахаров");
        contactBuilder.patronymicProperty().set("Денисович");
        contactBuilder.homeNumberProperty().set("89284602946");
        contactBuilder.mobileNumberProperty().set("89284602946");
        contactBuilder.addressProperty().set("Майкоп");
        contactBuilder.commentProperty().set("это коммент");
        contactBuilder.dateProperty().set(LocalDate.of(1001, 2, 3));
        contacts.add(new Contact(contactBuilder));

        return contacts;
    }

    @Test
    void isCorrectFilled() {
        var incorrect = Incorrect();
        for (Contact contact : incorrect) {
            assertFalse(contact.isCorrectFilled());
        }
        var correct = Correct();
        for (Contact contact : correct) {
            assertTrue(contact.isCorrectFilled());
        }
    }
}