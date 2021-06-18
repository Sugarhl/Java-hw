package model;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    Model model = Model.getInstance();
    ContactBuilder contactBuilder = new ContactBuilder();

    @org.junit.jupiter.api.Test
    void getFilter() {
        assertNotNull(model.getFilter());
    }

    @org.junit.jupiter.api.Test
    void getContact() {
        model.resetFilter();
        var contact = model.getContact(0);
        assertEquals(contact.getName(), "Ali");
        assertEquals(contact.getSurname(), "Panesh");
        assertEquals(contact.getPatronymic(), "");
        assertEquals(contact.getNumber(), "89182275572");
        assertEquals(contact.getAddress(), "");
        assertNull(contact.getDate());
        assertEquals(contact.getComment(), "");
    }

    @org.junit.jupiter.api.Test
    void saveContacts() {
        assertTrue(model.saveContacts());
    }

    @org.junit.jupiter.api.Test
    void serializeContacts() {
        assertTrue(model.serializeContacts("tests/serialize.cont"));
    }

    @org.junit.jupiter.api.Test
    void deserializeContacts() {
        var test = model.deserializeContacts("tests/test.cont");
        var test1 = model.deserializeContacts("tests/test1.cont");
        var test2 = model.deserializeContacts("tests/test2.cont");
        assertEquals(test.size(), 3);
        assertEquals(test1.size(), 4);
        assertEquals(test2.size(), 6);
    }

    @org.junit.jupiter.api.Test
    void editContact() {
        contactBuilder.nameProperty().set("Никита");
        contactBuilder.surnameProperty().set("Сахаров");
        contactBuilder.patronymicProperty().set("Денисович");
        contactBuilder.homeNumberProperty().set("89284602946");
        var contact = new Contact(contactBuilder);
        model.editContact(model.getContactsSize() - 1, contact);
        assertEquals(model.getContact(model.getContactsSize() - 1), contact);

    }

    @org.junit.jupiter.api.Test
    void addContact() {
        contactBuilder.nameProperty().set("ssssикита");
        contactBuilder.surnameProperty().set("SASDSAахаров");
        contactBuilder.patronymicProperty().set("ASDASДенисович");
        contactBuilder.homeNumberProperty().set("89284602946");
        assertTrue(model.addContact(new Contact(contactBuilder)));

        contactBuilder.nameProperty().set("sssNikita");
        contactBuilder.surnameProperty().set("sssssSakharov");
        contactBuilder.patronymicProperty().set("Denisovich");
        contactBuilder.homeNumberProperty().set("89284602946");
        assertTrue(model.addContact(new Contact(contactBuilder)));

        contactBuilder.nameProperty().set("sssssНикита");
        contactBuilder.surnameProperty().set("Сахаров");
        contactBuilder.patronymicProperty().set("Денисович");
        contactBuilder.homeNumberProperty().set("89284602946");
        contactBuilder.mobileNumberProperty().set("89284602946");
        contactBuilder.addressProperty().set("Майкоп");
        contactBuilder.commentProperty().set("это коммент");
        contactBuilder.dateProperty().set(LocalDate.of(1001, 2, 3));
        assertTrue(model.addContact(new Contact(contactBuilder)));
        model.deleteContact(model.getContactsSize() - 1);
        model.deleteContact(model.getContactsSize() - 1);
        model.deleteContact(model.getContactsSize() - 1);
    }

    @org.junit.jupiter.api.Test
    void deleteContact() {
        int size = model.getContactsSize();
        model.deleteContact(0);
        assertEquals(model.getContactsSize(), size - 1);
        model.importContacts(new File("copy.contacts"));
    }

    @org.junit.jupiter.api.Test
    void importContacts() {
        model.importContacts(new File("tests/importTest.cont"));
        assertEquals(model.getContactsSize(), 10);
    }

    @org.junit.jupiter.api.Test
    void exportContacts() {
        model.exportContacts(new File("tests/exportTest.cont"));
        assertEquals(model.deserializeContacts("tests/exportTest.cont").size(), 5);
    }

    @org.junit.jupiter.api.Test
    void search() {
        model.search("Ali");
        assertEquals(model.getFilter().size(), 2);
        model.search("Nikita");
        assertEquals(model.getFilter().size(), 1);
    }

    @org.junit.jupiter.api.Test
    void resetFilter() {
        model.search("Ali");
        model.resetFilter();
        assertEquals(model.getFilter().size(), 5);
    }
}