package sugar.annotationHandlers;

import sugar.anotations.NotEmpty;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotEmptyHandler extends AbstractHandler {
    private static final String FORMAT_MESSAGE = "Container is empty. Please add some elements";

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
        if (object instanceof List<?>) {
            return ((List<?>) object).isEmpty();
        }
        if (object instanceof Set<?>) {
            return ((Set<?>) object).isEmpty();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).isEmpty();
        }
        if (object instanceof String) {
            return ((String) object).isEmpty();
        }

        return false;
    }

    @Override
    public String createMessage(Annotation annotation) {
        return FORMAT_MESSAGE;
    }
}
