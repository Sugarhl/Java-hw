package sugar.annotationHandlers;

import sugar.anotations.NotEmpty;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NotBlankHandler extends AbstractHandler {
    static final String FORMAT_MESSAGE = "String is blank, please fill it";

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
        if (object instanceof String) {
            return ((String) object).isBlank();
        }

        return false;
    }

    @Override
    public String createMessage(Annotation annotation) {
        return FORMAT_MESSAGE;
    }
}
