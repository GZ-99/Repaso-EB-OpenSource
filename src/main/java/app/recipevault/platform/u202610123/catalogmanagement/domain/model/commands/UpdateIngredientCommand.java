package app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

import java.util.Objects;

public record UpdateIngredientCommand(Long ingredientId, IngredientCode code,
                                      String name, Double defaultPortionGrams,
                                      Double baseCaloriesPer100g) {

  private static final Double MIN_DEFAULT_PORTION_GRAMS = 0.0;
  private static final Double MAX_DEFAULT_PORTION_GRAMS = 500.0;
  private static final Double MIN_BASE_CALORIES_PER100G = 0.0;
  private static final Double MAX_BASE_CALORIES_PER100G = 900.0;

  public UpdateIngredientCommand {
    Objects.requireNonNull(ingredientId, "[UpdateIngredientCommand] Ingredient ID cannot be null");
    Objects.requireNonNull(code, "[UpdateIngredientCommand] Ingredient code cannot be null");
    Objects.requireNonNull(name, "[UpdateIngredientCommand] Ingredient name cannot be null");
    Objects.requireNonNull(defaultPortionGrams, "[UpdateIngredientCommand] Default portion grams cannot be null");
    Objects.requireNonNull(baseCaloriesPer100g, "[UpdateIngredientCommand] Base calories per 100g cannot be null");

    if (ingredientId <= 0) {
      throw new IllegalArgumentException("[UpdateIngredientCommand] Ingredient ID must be greater than zero");
    }

    if (defaultPortionGrams < MIN_DEFAULT_PORTION_GRAMS ||
        defaultPortionGrams > MAX_DEFAULT_PORTION_GRAMS) {
      throw new IllegalArgumentException("[UpdateIngredientCommand] Default portion grams must be between " +
          MIN_DEFAULT_PORTION_GRAMS + " and " + MAX_DEFAULT_PORTION_GRAMS);
    }

    if (baseCaloriesPer100g < MIN_BASE_CALORIES_PER100G ||
        baseCaloriesPer100g > MAX_BASE_CALORIES_PER100G) {
      throw new IllegalArgumentException("[UpdateIngredientCommand] Base calories per 100g must be between " +
          MIN_BASE_CALORIES_PER100G + " and " + MAX_BASE_CALORIES_PER100G);
    }
  }
}