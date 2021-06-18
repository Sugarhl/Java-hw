package sugar.validation;

public interface ValidationError {
    String getMessage();

    String getPath();

    Object getFailedValue();

}