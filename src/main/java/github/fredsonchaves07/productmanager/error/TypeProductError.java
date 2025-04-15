package github.fredsonchaves07.productmanager.error;

public enum TypeProductError {

    NAME_REQUIRED(
            "NAME_REQUIRED", "Nome inválido", "O nome do produto é obrigatório."),

    INVALID_PRICE("INVALID_PRICE", "Preço inválido", "O preço do produto deve ser maior ou igual a zero."),

    INVALID_STOCK("INVALID_STOCK", "Quantidade de estoque inválida", "A quantidade em estoque não pode ser negativa."),

    NOT_FOUND("NOT_FOUND", "Produto não encontrado", "Produto não encontrado com o ID informado.");

    private final String message;

    private final String description;

    TypeProductError(String type, String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String message() {
        return message;
    }

    public String description() {
        return description;
    }
}
