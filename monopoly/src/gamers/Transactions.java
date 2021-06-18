package gamers;

public interface Transactions {
    /**
     * @param sender Запросивший снятие объект
     * @param sum    сумма
     * @return успех операции
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Снимает со счета деньги
     */
    boolean pay(Object sender, int sum);

    /**
     * @param sum сумма
     * @author <a href="mailto:ndsakharov@edu.hse.ru"> Nikita Sakharov</a>* Увеличивает счет
     */
    void raiseCash(int sum);

}
