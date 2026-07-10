package app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.resources;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import io.swagger.v3.oas.annotations.media.Schema;

public record IngredientResource(
    @Schema(description = "The unique identifier of the ingredient", example = "1")
    Long id,

    @Schema(description = "The unique code of the ingredient", example = "AV-OAT-001")
    String code,

    @Schema(description = "The name of the ingredient", example = "Rolled Oats")
    String name,

    @Schema(description = "The default portion size of the ingredient in grams", example = "40.0")
    Double defaultPortionGrams,

    @Schema(description = "The base calories of the ingredient per 100 grams", example = "389.0")
    Double baseCaloriesPer100g
) {

}
