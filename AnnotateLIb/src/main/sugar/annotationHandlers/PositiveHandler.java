package sugar.annotationHandlers;

import java.lang.annotation.Annotation;

public class PositiveHandler extends AbstractHandler {
    private static final String FORMAT_MESSAGE = "Value must be positive";

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
        return AbstractHandler.getNumber(object) <= 0;
    }

    @Override
    public String createMessage(Annotation annotation) {
        return FORMAT_MESSAGE;
    }
}
