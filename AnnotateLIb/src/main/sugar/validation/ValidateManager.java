package sugar.validation;

import sugar.annotationHandlers.*;
import sugar.anotations.*;
import sugar.error.Error;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("ALL")
public class ValidateManager implements Validator {

    String currentPath = "";

    /**
     * Метод валидации объекта со всеми рекурсивным спусками в поля.
     *
     * @param object Объект
     * @return Сет ошибко допущенных в объекте
     */
    @Override
    public Set<ValidationError> validate(Object object) {

        Set<ValidationError> errors = new HashSet<>();
        if (object.getClass().getAnnotation(Constrained.class) == null) {
            return errors;
        }
        var fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            var annotationType = field.getAnnotatedType();

            Object subObject = null;
            try {
                subObject = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (subObject != null && subObject.getClass().getAnnotation(Constrained.class) != null) {
                var tempPath = currentPath;
                currentPath += field.getName() + ".";
                errors.addAll(validate(subObject));
                currentPath = tempPath;
            }

            errors.addAll(checkAnnotation(annotationType, subObject,
                    currentPath + field.getName()));

            if (subObject instanceof List<?>) {
                AnnotatedType type = ((AnnotatedParameterizedType) field.getAnnotatedType())
                        .getAnnotatedActualTypeArguments()[0];

                errors.addAll(checkList(type, (List<?>) subObject, currentPath + field.getName()));
            }
        }
        return errors;
    }

    /**
     * Метод возрвщает сет ошибок объекта по всем аннотациям
     *
     * @param annotationType Аннотации
     * @param object         Объект
     * @param path           Путь до объекта
     * @return Сет ошибок
     */
    Set<ValidationError> checkAnnotation(AnnotatedType annotationType,
                                         Object object, String path) {
        Set<ValidationError> errors = new HashSet<>();

        var annotations = annotationType.getAnnotations();
        for (var annotation : annotations) {
            var checker = checkers.get(annotation.annotationType());
            if (checker != null && checker.isNotCorrect(annotation, object)) {
                errors.add((ValidationError) new Error(object, checker.createMessage(annotation), path));
            }
        }
        return errors;
    }

    /**
     * Рекурсивный метод обработки листов
     *
     * @param ItemAnnotatedType Аннотации листа
     * @param list              Лист для обработки
     * @param path              Путь до листа
     * @return Сет ошибок в листе
     */
    Set<ValidationError> checkList(AnnotatedType ItemAnnotatedType, List<?> list,
                                   String path) {
        Set<ValidationError> errors = new HashSet<>();
        for (int i = 0; i < list.size(); ++i) {
            var item = list.get(i);

            if (list.get(i) != null && list.get(i).getClass().getAnnotation(Constrained.class) != null) {
                var tempPath = currentPath;
                currentPath = path + "[" + i + "].";
                errors.addAll(validate(list.get(i)));
                currentPath = tempPath;
            }

            errors.addAll(checkAnnotation(ItemAnnotatedType, item, path + "[" + i + "]"));
            if (item instanceof List<?>) {
                AnnotatedType nextItemAnnortatedType = ((AnnotatedParameterizedType)
                        ItemAnnotatedType).getAnnotatedActualTypeArguments()[0];
                errors.addAll(checkList(nextItemAnnortatedType, (List<?>) item, path + "[" + i + "]"));
            }
        }
        return errors;
    }

    /**
     * Мапа для приянятия решения по обработаке объекта
     */
    static final Map<Class<? extends Annotation>, ? extends AbstractHandler> checkers = Map.of(
            NotNull.class, new NotNullHandler(),
            Size.class, new SizeHandler(),
            AnyOf.class, new AnyofHandler(),
            NotEmpty.class, new NotEmptyHandler(),
            NotBlank.class, new NotBlankHandler(),
            Positive.class, new PositiveHandler(),
            Negative.class, new NegativeHandler(),
            InRange.class, new InRangeHandler(),
            Constrained.class, new ConstrainedHandler()
    );
}
