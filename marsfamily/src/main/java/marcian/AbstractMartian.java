package marcian;

import java.util.ArrayList;

public abstract class AbstractMartian<T> {
    protected T geneticСode;
    protected AbstractMartian<T> parent;
    protected ArrayList<AbstractMartian<T>> children;

    /**
     * Гетер для предка.
     * @return Предок.
     */
    public abstract AbstractMartian<T> getParent();

    /**
     * Гетер для коллекции детей.
     * @return Коллекиця детей.
     */
    public abstract ArrayList<AbstractMartian<T>> getChildren();

    /**
     * Гетер получения всех потомков.
     * @return Коллекция потомков.
     */
    public abstract ArrayList<AbstractMartian<T>> getDescendants();

    /**
     * Поиск ребенка по ключу.
     * @param value Ключ.
     * @return Наличие ребенка.
     */
    public boolean hasChildWithValue(T value) {
        for (AbstractMartian<T> child : children) {
            if (child.geneticСode == value)
                return true;
        }
        return false;
    }

    /**
     * Поиск потомка по ключу
     * @param value Ключ.
     * @return Наличие потомка
     */
    public boolean hasDescadantWithValue(T value) {
        var descadants = getDescendants();
        for (AbstractMartian<T> descadant : descadants) {
            if (descadant.geneticСode == value)
                return true;
        }
        return false;
    }

    /**
     * Провекра на то является ли потомком марсианин
     * @param descendant Марсианнин
     * @return Потомок или нет
     */
    protected boolean isDescandant(AbstractMartian<T> descendant) {
        return getDescendants().contains(descendant);
    }

    /**
     * Строковое представлении информации о марсианине
     * @param tabs Количество отступов
     * @return Строчка информации
     */
    public String toString(int tabs) {
        return "    ".repeat(Math.max(0, tabs)) + AbstractMartian.class.getName() + '(' +
                geneticСode.getClass().getName() + geneticСode +
                ')';
    }

    /**
     * Метод преобразования строки в объект
     * @param parent Предок
     * @param description Описание свойств марсианина
     * @return Марсианин по описанию
     */
    public static InnovatorMartian Parse(AbstractMartian parent, String description) {
        String[] parts = description.split(":");
        switch (parts[0]) {
            case "Integer":
                return new InnovatorMartian<>(Integer.parseInt(parts[1]), (InnovatorMartian<Integer>) parent);
            case "Double":
                return new InnovatorMartian<>(Double.parseDouble(parts[1]), (InnovatorMartian<Double>) parent);
            case "String":
                return new InnovatorMartian<>(parts[1], (InnovatorMartian<String>) parent);
            default:
                return null;
        }
    }
}