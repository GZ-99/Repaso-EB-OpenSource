package app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.assemblers;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.entities.IngredientPersistenceEntity;

public class IngredientPersistenceAssembler {
  private IngredientPersistenceAssembler() {}

  public static IngredientPersistenceEntity toPersistenceFromDomain(Ingredient ingredient) {
    IngredientPersistenceEntity persistenceEntity = new IngredientPersistenceEntity();
    persistenceEntity.setId(ingredient.getId());
    persistenceEntity.setCode(ingredient.getCode());
    persistenceEntity.setName(ingredient.getName());
    persistenceEntity.setDefaultPortionGrams(ingredient.getDefaultPortionGrams());
    persistenceEntity.setBaseCaloriesPer100g(ingredient.getBaseCaloriesPer100g());
    return persistenceEntity;
  }

  public static Ingredient toDomainFromPersistence(IngredientPersistenceEntity ingredientPersistenceEntity) {
    Ingredient domain = new Ingredient();
    domain.setId(ingredientPersistenceEntity.getId());
    domain.setCode(ingredientPersistenceEntity.getCode());
    domain.setName(ingredientPersistenceEntity.getName());
    domain.setDefaultPortionGrams(ingredientPersistenceEntity.getDefaultPortionGrams());
    domain.setBaseCaloriesPer100g(ingredientPersistenceEntity.getBaseCaloriesPer100g());
    return domain;
  }
}
