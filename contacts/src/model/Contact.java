package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Класс представления контакта
 */
public final class Contact implements Serializable {
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String number;
    private final String mobileNumber;
    private final String homeNumber;
    private final String address;
    private final LocalDate date;
    private final String comment;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }


    public String getID() {
        return name + surname + patronymic;
    }

    public String getID2() {
        return surname + patronymic;
    }

    public String getID3() {
        return surname + patronymic;
    }

    /**
     * Метод проверки корректности контакта
     *
     * @return корректность заполнения
     */

    public boolean isCorrectFilled() {
        return nameIsCorrect() && surnameIsCorrect() && patronymicIsCorrect() &&
                numberIsCorrect();
    }

    /**
     * Валидация имени
     *
     * @return Корректность имени
     */
    private boolean nameIsCorrect() {
        return name != null && !name.isEmpty() &&
                !name.contains(" ") &&
                name.matches("[а-яА-ЯA-Za-z]+");
    }

    /**
     * Валидация фамилии
     *
     * @return Корректность фамилии
     */
    private boolean surnameIsCorrect() {
        return surname != null && !surname.isEmpty() &&
                !surname.contains(" ") &&
                surname.matches("[а-яА-ЯA-Za-z]+");
    }

    /**
     * Валидация отчества
     *
     * @return Корректность отчества
     */
    private boolean patronymicIsCorrect() {
        return patronymic != null && patronymic.isEmpty() ||
                !patronymic.contains(" ") &&
                        patronymic.matches("[а-яА-ЯA-Za-z]+");
    }

    /**
     * Валидация домашнего телефона
     *
     * @return Корректность телефона
     */
    private boolean homeNumberIsCorrect() {
        return homeNumber != null && homeNumber.matches("[0-9]+") &&
                homeNumber.length() == 11;
    }

    /**
     * Валидация мобиьного телефона
     *
     * @return Корректность телефона
     */
    private boolean mobileNumberIsCorrect() {
        return mobileNumber != null && mobileNumber.matches("[0-9]+") &&
                mobileNumber.length() == 11;
    }

    /**
     * Валидация предствления номеров
     *
     * @return Корректность телефона
     */
    private boolean numberIsCorrect() {
        if (mobileNumberIsCorrect()) {
            return homeNumberIsCorrect() || (homeNumber != null && homeNumber.isEmpty());
        } else {
            return (mobileNumber == null || mobileNumber.isEmpty()) && homeNumberIsCorrect();
        }
    }

    /**
     * Констркутор от билдера контакта
     *
     * @param contactBuilder билдер контакта
     */
    public Contact(ContactBuilder contactBuilder) {
        this.name = contactBuilder.getName();
        this.surname = contactBuilder.getSurname();
        this.patronymic = contactBuilder.getPatronymic();
        this.address = contactBuilder.getAddress();
        this.date = contactBuilder.getDate();
        this.comment = contactBuilder.getComment();
        homeNumber = contactBuilder.getHomeNumber();
        mobileNumber = contactBuilder.getMobileNumber();
        if (mobileNumber == null || mobileNumber.isEmpty()) {
            number = homeNumber;
        } else {
            if (homeNumber == null || homeNumber.isEmpty()) {
                number = mobileNumber;
            } else {
                number = mobileNumber + " / " + homeNumber;
            }
        }
    }
}
