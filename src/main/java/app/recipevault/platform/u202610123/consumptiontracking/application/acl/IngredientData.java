package app.recipevault.platform.u202610123.consumptiontracking.application.acl;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

public record IngredientData(
        Long id,
        IngredientCode code,
        String name,
        Double defaultPortionGrams,
        Double baseCaloriesPer100g
) {
}
