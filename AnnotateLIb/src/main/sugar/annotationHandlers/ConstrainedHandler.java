package sugar.annotationHandlers;

import sugar.annotationHandlers.AbstractHandler;

import java.lang.annotation.Annotation;

public class ConstrainedHandler extends AbstractHandler {
    @Override
    public boolean isNotCorrect(Annotation annotation, Object object) {
        return false;
    }

    @Override
    public String createMessage(Annotation annotation) {
        return "";
    }
}
