package sugar.annotationHandlers;

import sugar.anotations.AnyOf;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class AnyofHandler extends AbstractHandler {
    private static final String FORMAt_MESSAGE = "The value must be one of the list %s";

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
        var list = Arrays.asList(((AnyOf) annotation).value());
        return !list.contains(object);
    }

    @Override
    public String createMessage(Annotation annotation) {
        var list = Arrays.asList(((AnyOf) annotation).value());
        return String.format(FORMAt_MESSAGE, list);
    }
}
