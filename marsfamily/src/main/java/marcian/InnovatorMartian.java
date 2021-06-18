package marcian;


import java.util.ArrayList;

public class InnovatorMartian<T> extends AbstractMartian<T> {

    /**
     * Конструктор от всех параметров
     * @param code Ключ
     * @param parent Отец
     * @throws IllegalArgumentException кидает такое исключение потому,
     * что не хочу чтобы можно было создавать марсиан прочих типов.
     */
    public InnovatorMartian(T code, InnovatorMartian<T> parent) throws IllegalArgumentException {
        if (code.getClass() != Integer.class &
                code.getClass() != Double.class &
                code.getClass() != String.class)
            throw new IllegalArgumentException("Неверное значение генетического кода.");
        this.parent = parent;
        geneticСode = code;
        children = new ArrayList<>();
    }

    /**
     * Сетер ключа
     * @param code Ключ
     * @return Успех операции
     */
    public boolean setGeneticCode(T code) {
        if (code == null)
            return false;
        geneticСode = code;
        return false;
    }

    /**
     * Гетер отца
     * @return Отец
     */
    @Override
    public AbstractMartian<T> getParent() {
        return parent;
    }

    /**
     * Сеттер отца
     * @param newparent Новый отец
     * @return Успех оперпации
     */
    public boolean setParent(InnovatorMartian<T> newparent) {
        if (newparent == null) {
            parent = null;
            return true;
        }
        if (isDescandant(newparent)) {
            return false;
        }
        parent = newparent;
        return true;
    }

    /**
     * Геттер детей
     * @return Коллекция детей.
     */
    @Override
    public ArrayList<AbstractMartian<T>> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Сеттер детей
     * поддерживает проверку корректности добаления
     * @param newChildren Коллекция новых детей.
     * @return Успех операции
     */
    public boolean setChildren(ArrayList<AbstractMartian<T>> newChildren) {
        if (newChildren == null) {
            return false;
        }
        for (AbstractMartian<T> newchild : newChildren) {
            if (newchild.isDescandant(this)) {
                return false;
            }
        }
        for (AbstractMartian<T> child : children) {
            ((InnovatorMartian<T>) child).setParent(null);
        }
        children = newChildren;
        return true;
    }

    /**
     * Геттер потомков
     * @return Коллекция потомков
     */
    @Override
    public ArrayList<AbstractMartian<T>> getDescendants() {
        ArrayList<AbstractMartian<T>> descendantsInfo = new ArrayList<>(getChildren());
        for (AbstractMartian<T> child : children) {
            descendantsInfo.addAll((child.getDescendants()));
        }
        return descendantsInfo;
    }

    /**
     * Добавление ребенка
     * @param newChild Новый ребенок
     * @return Успех операции
     */
    public boolean addChild(InnovatorMartian<T> newChild) {
        if (!(newChild == null || this == newChild)) {
            if (!this.isDescandant(newChild) && newChild.setParent(this)) {
                children.add(newChild);
                return true;
            }
        }
        return false;
    }

    /**
     * Удаление ребенка
     * @param child Ребенок
     * @return Успех операции
     */
    public boolean removeChild(InnovatorMartian<T> child) {
        if (child == null || !children.contains(child)) {
            return false;
        }
        children.remove(child);
        return true;
    }

    /**
     * Получение строчки информации о
     * @param tabs Количество отступов
     * @return Строка информации
     */
    @Override
    public String toString(int tabs) {
        return "    ".repeat(Math.max(0, tabs)) + InnovatorMartian.class.getSimpleName() + '(' +
                geneticСode.getClass().getSimpleName() + ':' + geneticСode +
                ')';
    }
}
