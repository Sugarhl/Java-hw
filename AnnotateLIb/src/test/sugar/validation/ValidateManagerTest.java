package sugar.validation;

import org.junit.jupiter.api.Test;
import sugar.samples.*;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidateManagerTest {
    Validator validator = new ValidateManager();

    @Test
    void validate() {
        List<GuestForm> guests = List.of(
                new GuestForm(/*firstName*/ null, /*lastName*/ "Def", /*age*/ 21),
                new GuestForm(/*firstName*/ "", /*lastName*/ "Ijk", /*age*/ -3)
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                /*amenities*/ List.of("TV", "Piano"),
                /*propertyType*/ "Apartment",
                unrelated
        );
        Set<ValidationError> errors = validator.validate(bookingForm);

        StringBuilder firstSample = new StringBuilder();
        var stream = errors.stream().sorted((validationError, t1) -> {
            if (validationError.getPath().compareTo(t1.getPath()) == 0) {
                return validationError.getMessage().compareTo(t1.getMessage());
            }
            return validationError.getPath().compareTo(t1.getPath());
        });
        for (var er : stream.toArray()) {
            firstSample.append(er.toString()).append("\n");
        }

        List<List<Integer>> list = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 3, null, 23)),
                new ArrayList<>(Arrays.asList(1, 3, null, null)),
                null,
                new ArrayList<>(),
                null,
                new ArrayList<>(Arrays.asList(1, 3, null))
        ));
        var sample = new Sample(2, null, list, new AnnotationCheckClass());

        errors = validator.validate(sample);

        StringBuilder secondSample = new StringBuilder();
        stream = errors.stream().sorted((validationError, t1) -> {
            if (validationError.getPath().compareTo(t1.getPath()) == 0) {
                return validationError.getMessage().compareTo(t1.getMessage());
            }
            return validationError.getPath().compareTo(t1.getPath());
        });
        for (var er : stream.toArray()) {
            secondSample.append(er.toString()).append("\n");
        }
        assertEquals(firstSample.toString(), firstAnswer);
        assertEquals(secondSample.toString(), secondAnswer);
    }

    @Test
    void checkAnnotation() {
        AnnotationCheckClass sample = new AnnotationCheckClass();
        Method method = null;
        try {
            method = validator.getClass().getDeclaredMethod("checkAnnotation", AnnotatedType.class,
                    Object.class, String.class);

            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Arrays.stream(sample.getClass().getDeclaredFields()).forEach(field -> field.setAccessible(true));

        var annotation = Arrays.stream(sample.getClass().getDeclaredFields()).
                map(Field::getAnnotatedType).collect(Collectors.toList());

        var names = Arrays.stream(sample.getClass().getDeclaredFields()).
                map(Field::getName).collect(Collectors.toList());

        var objects = Arrays.stream(sample.getClass().getDeclaredFields()).
                map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(sample);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
        try {
            for (int i = 0; i < 8; i++) {
                var set = method.invoke(validator, annotation.get(i), objects.get(i), names.get(i));
                assertEquals(((Set<?>) set).size(), (i + 1) % 2);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static String firstAnswer = "Message: The value must be one of the list [TV, Kitchen]\n" +
            "Value: Piano\n" +
            "Path: amenities[1]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: guests[0].firstName\n" +
            "Message: Value must be in range [0,200]\n" +
            "Value: -3\n" +
            "Path: guests[1].age\n" +
            "Message: String is blank, please fill it\n" +
            "Value: \n" +
            "Path: guests[1].firstName\n" +
            "Message: The value must be one of the list [House, Hostel]\n" +
            "Value: Apartment\n" +
            "Path: propertyType\n";

    static String secondAnswer = "Message: The value must be one of the list [Danya, Andrey, Dima]\n" +
            "Value: Sugar\n" +
            "Path: annotationCheckClass.anyof\n" +
            "Message: Value must be in range [1,1000]\n" +
            "Value: 100034\n" +
            "Path: annotationCheckClass.inRange\n" +
            "Message: Value must be negative\n" +
            "Value: 1\n" +
            "Path: annotationCheckClass.negative\n" +
            "Message: String is blank, please fill it\n" +
            "Value: \n" +
            "Path: annotationCheckClass.notBlank\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: annotationCheckClass.notEmpty\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: annotationCheckClass.notNull\n" +
            "Message: Value must be positive\n" +
            "Value: -1\n" +
            "Path: annotationCheckClass.positive\n" +
            "Message: Size of this object must be in range [1, 4]\n" +
            "Value: [1, 2, 3, 4, 5]\n" +
            "Path: annotationCheckClass.size\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: emptyList\n" +
            "Message: Size of this object must be in range [2, 3]\n" +
            "Value: [1, 3, null, 23]\n" +
            "Path: friends[0]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[0][2]\n" +
            "Message: Size of this object must be in range [2, 3]\n" +
            "Value: [1, 3, null, null]\n" +
            "Path: friends[1]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[1][2]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[1][3]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[2]\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: friends[3]\n" +
            "Message: Size of this object must be in range [2, 3]\n" +
            "Value: []\n" +
            "Path: friends[3]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[4]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: friends[5][2]\n" +
            "Message: Value must be in range [-31,1]\n" +
            "Value: 2\n" +
            "Path: id\n" +
            "Message: String is blank, please fill it\n" +
            "Value: \n" +
            "Path: kek\n" +
            "Message: Value must be positive\n" +
            "Value: -1\n" +
            "Path: lists.list[1]\n" +
            "Message: Value must be positive\n" +
            "Value: -22\n" +
            "Path: lists.list[2]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, -2, 2]\n" +
            "Path: lists.subList[0]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subList[0][1]\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: lists.subList[1]\n" +
            "Message: Value must be positive\n" +
            "Value: []\n" +
            "Path: lists.subList[1]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4]\n" +
            "Path: lists.subList[2]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4, -2]\n" +
            "Path: lists.subList[3]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subList[3][3]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 123, -2, 2]\n" +
            "Path: lists.subList[4]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subList[4][2]\n" +
            "Message: Value must be positive\n" +
            "Value: [[1, -2, 2], [], [1, 2, 4], [1, 2, 4, -2], [1, 123, -2, 2]]\n" +
            "Path: lists.subSubList[0]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, -2, 2]\n" +
            "Path: lists.subSubList[0][0]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[0][0][1]\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: lists.subSubList[0][1]\n" +
            "Message: Value must be positive\n" +
            "Value: []\n" +
            "Path: lists.subSubList[0][1]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4]\n" +
            "Path: lists.subSubList[0][2]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4, -2]\n" +
            "Path: lists.subSubList[0][3]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[0][3][3]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 123, -2, 2]\n" +
            "Path: lists.subSubList[0][4]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[0][4][2]\n" +
            "Message: Size of this object must be in range [2, 5]\n" +
            "Value: [[1, 2, 4], [1, -2, 2], [1, 2, 4, -2], [], [1, 123, -2, -2, 2], []]\n" +
            "Path: lists.subSubList[1]\n" +
            "Message: Value must be positive\n" +
            "Value: [[1, 2, 4], [1, -2, 2], [1, 2, 4, -2], [], [1, 123, -2, -2, 2], []]\n" +
            "Path: lists.subSubList[1]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4]\n" +
            "Path: lists.subSubList[1][0]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, -2, 2]\n" +
            "Path: lists.subSubList[1][1]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[1][1][1]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 2, 4, -2]\n" +
            "Path: lists.subSubList[1][2]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[1][2][3]\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: lists.subSubList[1][3]\n" +
            "Message: Value must be positive\n" +
            "Value: []\n" +
            "Path: lists.subSubList[1][3]\n" +
            "Message: Value must be positive\n" +
            "Value: [1, 123, -2, -2, 2]\n" +
            "Path: lists.subSubList[1][4]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[1][4][2]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: lists.subSubList[1][4][3]\n" +
            "Message: Container is empty. Please add some elements\n" +
            "Value: []\n" +
            "Path: lists.subSubList[1][5]\n" +
            "Message: Value must be positive\n" +
            "Value: []\n" +
            "Path: lists.subSubList[1][5]\n" +
            "Message: Size of this object must be in range [2, 5]\n" +
            "Value: []\n" +
            "Path: lists.subSubList[2]\n" +
            "Message: Value must be positive\n" +
            "Value: []\n" +
            "Path: lists.subSubList[2]\n" +
            "Message: Value is null. Please put some object this place\n" +
            "Value: null\n" +
            "Path: name\n" +
            "Message: Value must be positive\n" +
            "Value: -1\n" +
            "Path: positive[0]\n" +
            "Message: Value must be positive\n" +
            "Value: -2\n" +
            "Path: positive[1]\n" +
            "Message: Value must be positive\n" +
            "Value: -3\n" +
            "Path: positive[2]\n" +
            "Message: The value must be one of the list [Dima, Sahsa, Danya]\n" +
            "Value: Nikita\n" +
            "Path: sername\n";
}