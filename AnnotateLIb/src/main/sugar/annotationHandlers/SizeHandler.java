package sugar.annotationHandlers;

import sugar.anotations.Size;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SizeHandler extends AbstractHandler {
    private static final String FORMAT_MESSAGE = "Size of this object must be in range [%d, %d]";

    /**
     * Метод проверки объекта по аннотации
     * @param annotation Аннотация
     * @param object     Объект
     * @return Валидность
     */
    @Override
    public boolean isNotCorrect(Annotation annotation, Object object) {
        var range = (Size) annotation;
        if (object == null) {
            return false;
        }

        if (object instanceof List<?>) {
            return (((List<?>) object).size() > range.max() || ((List<?>) object).size() < range.min());
        }
        if (object instanceof Set<?>) {
            return ((Set<?>) object).size() > range.max() || ((Set<?>) object).size() < range.min();
        }
        if (object instanceof Map<?, ?>) {
            return ((Map<?, ?>) object).size() > range.max() || ((Map<?, ?>) object).size() < range.min();
        }
        if (object instanceof String) {
            return ((String) object).length() > range.max() || ((String) object).length() < range.min();
        }

        return false;
    }

    @Override
    public String createMessage(Annotation annotation) {
        var range = (Size) annotation;
        return String.format(FORMAT_MESSAGE, range.min(), range.max());
    }
}
