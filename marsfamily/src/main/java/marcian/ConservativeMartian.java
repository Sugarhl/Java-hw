package marcian;

import java.util.ArrayList;

public class ConservativeMartian<T> extends AbstractMartian<T> {

    protected final T geneticСode;
    protected final AbstractMartian<T> parent;
    protected final ArrayList<AbstractMartian<T>> children;

    /**
     * Конструктор по предку и коду
     * Приватный т.к. такие марсиане появляются только из новаторов
     * значит доступный котруктор у них должен быть один.
     *
     * @param geneticСode Ключ
     * @param parent Отец
     */
    private ConservativeMartian(T geneticСode, AbstractMartian<T> parent) {
        this.geneticСode = geneticСode;
        this.parent = parent;
        children = new ArrayList<>();
    }

    /**
     * Открытый конструктор от новатора
     * Копирует все поддерево от коря который передаем
     * Не стал копировать все дерево т.к. думаю что каждый марсианин должен сохранять в вечности
     * только то, чего добился он сам.
     * @param martian Новатор который решил, что пора все залить на сервак
     */
    public ConservativeMartian(InnovatorMartian<T> martian) {
        geneticСode = martian.geneticСode;
        parent = null;
        children = new ArrayList<>();
        addChildren(martian.children);
    }

    /**
     * Приватный метод добавления ребенка
     * т.к. без него логическая ловушка того что надо создать узел, у которого все соседи уже есть.
     *
     * @param invchildren Новаторские дети которых надо сделать консерваторами.
     */
    private void addChildren(ArrayList<AbstractMartian<T>> invchildren) {
        for (AbstractMartian<T> child : invchildren) {
            var current = new ConservativeMartian<T>(child.geneticСode, this);
            children.add(current);
            current.addChildren(child.children);
        }
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
     * Гетер детей
     * @return Коллекция детей.
     */
    @Override
    public ArrayList<AbstractMartian<T>> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Гетер потомков
     * @return Коллекция потомков.
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
     * Метод получения строки информации о марсианине.
     * @param tabs Количество отступов
     * @return Строчка инфы
     */
    @Override
    public String toString(int tabs) {
        return "    ".repeat(Math.max(0, tabs)) + ConservativeMartian.class.getSimpleName() + '(' +
                geneticСode.getClass().getSimpleName() +":"+ geneticСode +
                ')';
    }

}
