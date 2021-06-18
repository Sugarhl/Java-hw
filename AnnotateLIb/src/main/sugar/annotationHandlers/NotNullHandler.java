package sugar.annotationHandlers;

import sugar.annotationHandlers.AbstractHandler;

import java.lang.annotation.Annotation;

public class NotNullHandler extends AbstractHandler {
    static final String FORMAT_MESSAGE = "Value is null. Please put some object this place";

    /**
     * Метод проверки объекта по аннотации
     * @param annotation Аннотация
     * @param object     Объект
     * @return Валидность
     */
    public boolean isNotCorrect(Annotation annotation, Object object) {
        return object == null;
    }

    @Override
    public String createMessage(Annotation annotation) {
        return FORMAT_MESSAGE;
    }

}
