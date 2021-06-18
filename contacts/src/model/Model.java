package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Model {
    private final ObservableList<Contact> contacts;

    private final FilteredList<Contact> filter;
    // путь до файла с сохраненными контактами
    private final String load = ".contacts";

    private boolean loadSuccess = true;

    public int getContactsSize() {
        return contacts.size();
    }

    public boolean getLoadSuccess() {
        return loadSuccess;
    }

    private Model() {
        contacts = FXCollections.observableArrayList(deserializeContacts(load));
        filter = new FilteredList<>(contacts);
    }

    /**
     * Получение фильтра
     *
     * @return фильтр
     */
    public FilteredList<Contact> getFilter() {
        return filter;
    }

    /**
     * Получение контакта по индексу
     *
     * @param index индекс
     * @return Выбранный контакт
     */
    public Contact getContact(int index) {
        int bySelected = getContactIndexBySelected(index);
        if (bySelected >= 0 && bySelected < contacts.size())
            return contacts.get(bySelected);
        return null;
    }

    /**
     * Сохранение контактов
     *
     * @return Успех операции
     */
    public boolean saveContacts() {
        return serializeContacts(load);
    }

    /**
     * Сериалиация контактов
     *
     * @param path путь до файла
     * @return Успех операции
     */
    public boolean serializeContacts(String path) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ArrayList<Contact> list = new ArrayList<>(contacts);
            objectOutputStream.writeObject(list);
            return true;
        } catch (IOException e) {
            loadSuccess = false;
        }
        return false;
    }

    /**
     * Десериализация контактов
     *
     * @param file путь до файла
     * @return список контактов, пуст если сериализация не удалась
     */
    public ArrayList<Contact> deserializeContacts(String file) {
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            ObjectInputStream objectOutputStream = new ObjectInputStream(inputStream);
            return (ArrayList<Contact>) objectOutputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            loadSuccess = false;
        }
        return new ArrayList<>();
    }

    /**
     * Изменение контакта
     *
     * @param selectedIndex выбранный индекс
     * @param person        Измененный контакт
     * @return Успех операции
     */
    public boolean editContact(int selectedIndex, Contact person) {
        if (!person.isCorrectFilled())
            return false;
        var pos = getContactIndexBySelected(selectedIndex);
        if (pos < 0 || pos >= contacts.size()) {
            return false;
        }
        contacts.set(pos, person);
        return true;
    }

    /**
     * Добавление контакта
     *
     * @param person Контакт
     * @return Успех операции
     */
    public boolean addContact(Contact person) {
        var isExist = contacts.stream().map(Contact::getID).anyMatch(x -> x.equals(person.getID()));
        if (!person.isCorrectFilled() || isExist)
            return false;
        contacts.add(person);
        return true;
    }


    /**
     * Удаления контакта
     *
     * @param selectedIndex индекс выбора
     */
    public void deleteContact(int selectedIndex) {
        int pos = getContactIndexBySelected(selectedIndex);
        if (pos >= 0 && pos < contacts.size())
            contacts.remove(pos);
    }

    /**
     * Экспорт контактов в файл
     *
     * @param file Файл для экспорта
     * @return Успех операции
     */
    public boolean exportContacts(File file) {
        if (file == null)
            return true;
        return serializeContacts(file.getAbsolutePath());
    }

    /**
     * Импорт контактов из файла
     *
     * @param file Файл для импорта
     * @return Успех операции
     */
    public boolean importContacts(File file) {
        if (file == null)
            return true;
        var addContacts = deserializeContacts(file.getAbsolutePath());
        if (addContacts.isEmpty()) {
            return false;
        }
        var ids = contacts.stream().map(Contact::getID).collect(Collectors.toList());
        for (var contact : addContacts) {
            if (!ids.contains(contact.getID())) {
                contacts.add(contact);
            }
        }
        return true;
    }

    /**
     * Фультр контактов по паттерну
     *
     * @param text паттерн
     */
    public void search(String text) {
        var parameters = text.split(" ");
        int bound = Math.min(parameters.length, 3);
        StringBuilder patternBuilder = new StringBuilder();
        for (int i = 0; i < bound; i++) {
            patternBuilder.append(parameters[i]);
        }
        Predicate<Contact> first = (Contact x) -> x.getID().startsWith(new String(patternBuilder));
        Predicate<Contact> second = (Contact x) -> x.getID2().startsWith(new String(patternBuilder));
        if (contacts.stream().noneMatch(first)) {
            if (contacts.stream().noneMatch(second)) {
                filter.setPredicate(x -> x.getID3().startsWith(new String(patternBuilder)));
            } else {
                filter.setPredicate(second);
            }
        } else {
            filter.setPredicate(first);
        }
    }

    /**
     * Сброса фильтра
     */
    public void resetFilter() {
        filter.setPredicate(x -> true);
    }

    /**
     * Преобразование выбранного индекса в индекс контакта
     *
     * @param selectedIndex выбранны индекс
     * @return индекс котакта или -1, если его нет
     */
    private int getContactIndexBySelected(int selectedIndex) {
        if (selectedIndex >= 0 && selectedIndex < filter.size()) {
            var contact = filter.get(selectedIndex);
            return contacts.indexOf(contact);
        }
        return -1;
    }

    private static Model model;

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }
}
