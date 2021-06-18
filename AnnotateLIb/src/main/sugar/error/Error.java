package sugar.error;

import sugar.validation.ValidationError;

public class Error implements ValidationError {

    final String message;
    private Object object;
    private String path;


    public Error(Object object, String message, String path) {
        this.object = object;
        this.message = message;
        this.path = path;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public Object getFailedValue() {
        return object;
    }

    @Override
    public String toString() {
        return "Message: " + getMessage() + "\nValue: " + getFailedValue() + "\nPath: " + getPath();
    }
}
