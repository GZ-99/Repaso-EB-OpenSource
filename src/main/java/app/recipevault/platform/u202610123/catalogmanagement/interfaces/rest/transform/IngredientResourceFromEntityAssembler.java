package app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.transform;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.resources.IngredientResource;

public class IngredientResourceFromEntityAssembler {

  public static IngredientResource toResourceFromEntity(Ingredient entity) {
    return new IngredientResource(
        entity.getId(),
        entity.getCode().code(),
        entity.getName(),
        entity.getDefaultPortionGrams(),
        entity.getBaseCaloriesPer100g()
    );
  }
}
