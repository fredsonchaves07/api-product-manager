package github.fredsonchaves07.productmanager.error;

public class ProductError extends RuntimeException {

    private TypeProductError typeError;

    private String message;

    private String description;

    private ProductError(TypeProductError typeError, String message, String description) {
        super(message);
        this.typeError = typeError;
        this.message = message;
        this.description = description;
    }

    public static ProductError throwsError(TypeProductError typeError) {
        return new ProductError(typeError, typeError.message(), typeError.description());
    }

    public TypeProductError typeError() {
        return typeError;
    }

    public String message() {
        return message;
    }

    public String description() {
        return description;
    }
}
