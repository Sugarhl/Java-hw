package sugar.annotationHandlers;

import java.lang.annotation.Annotation;

public abstract class AbstractHandler {
    public String message = "";

    /**
     * Метод проверки валидности объекта по аннотации
     *
     * @param annotation Аннотация
     * @param object     Объект
     * @return Валидность
     */
    public abstract boolean isNotCorrect(Annotation annotation, Object object);

    /**
     * Получение сообщения по аннотации
     *
     * @param annotation Аннотция
     * @return Сообщение для пользователя
     */
    public abstract String createMessage(Annotation annotation);

    /**
     * Метод каста  объекта к числового значению для аннотаций над числами
     *
     * @param object Обект-число
     * @return Значение
     */
    public static long getNumber(Object object) {
        long value = 0;
        if (object instanceof Byte) {
            value = (Byte) object;
        }
        if (object instanceof Short) {
            value = (Short) object;
        }
        if (object instanceof Integer) {
            value = (Integer) object;
        }
        if (object instanceof Long) {
            value = (Long) object;
        }
        return value;
    }
}
