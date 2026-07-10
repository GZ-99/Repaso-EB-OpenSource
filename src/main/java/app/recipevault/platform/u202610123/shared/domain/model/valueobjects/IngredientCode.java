package app.recipevault.platform.u202610123.shared.domain.model.valueobjects;

import java.util.Objects;

public record IngredientCode(String code) {
    private static final String INGREDIENT_CODE_PATTERN = "^[A-Z]{2}-[A-Z]{3}-[0-9]{3}$";

    public IngredientCode {
        if (Objects.isNull(code) || code.isBlank()) {
            throw new IllegalArgumentException("[IngredientCode] Ingredient code cannot be null or blank");
        }
        if (!code.matches(INGREDIENT_CODE_PATTERN)) {
            throw new IllegalArgumentException("[IngredientCode] Ingredient code format is invalid");
        }
    }
}
