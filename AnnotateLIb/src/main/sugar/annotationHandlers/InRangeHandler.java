package sugar.annotationHandlers;

import sugar.anotations.InRange;

import java.lang.annotation.Annotation;

public class InRangeHandler extends AbstractHandler {
    private static final String FORMAT_MESSAGE = "Value must be in range [%d,%d]";

    /**
     * Метод проверки объекта по аннотации
     * @param annotation Аннотация
     * @param object     Объект
     * @return Валидность
     */
    @Override
    public boolean isNotCorrect(Annotation annotation, Object object) {
        if (object == null) {
            return false;
        }
        var range = (InRange) annotation;
        long value = AbstractHandler.getNumber(object);
        return value < range.min() || value > range.max();
    }


    @Override
    public String createMessage(Annotation annotation) {
        var range = (InRange) annotation;
        return String.format(FORMAT_MESSAGE, range.min(), range.max());
    }
}
