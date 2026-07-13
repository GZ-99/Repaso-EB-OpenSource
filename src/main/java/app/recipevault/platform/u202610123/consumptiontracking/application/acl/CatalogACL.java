package app.recipevault.platform.u202610123.consumptiontracking.application.acl;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

import java.util.Optional;

public interface CatalogACL {
    boolean ingredientExists(IngredientCode code);
    Optional<IngredientData> getIngredientData(IngredientCode code);
}
