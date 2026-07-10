package app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.DietaryTag;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.MealType;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateConsumptionRecordCommand(IngredientCode ingredientCode, MealType mealType,
                                             Double portionGrams, Double calories,
                                             DietaryTag dietaryTag, LocalDateTime recordedAt) {

  private static final Double MIN_DEFAULT_PORTION_GRAMS = 0.0;
  private static final Double MAX_DEFAULT_PORTION_GRAMS = 500.0;
  private static final Double MIN_PORTION_GRAMS = 0.0;
  private static final Double MAX_PORTION_GRAMS = 1000.0;
  private static final Double MIN_CALORIES = 0.0;

  public CreateConsumptionRecordCommand {
    Objects.requireNonNull(ingredientCode, "[CreateConsumptionRecordCommand] Ingredient code cannot be null");
    Objects.requireNonNull(mealType, "[CreateConsumptionRecordCommand] Meal type cannot be null");
    Objects.requireNonNull(portionGrams, "[CreateConsumptionRecordCommand] Portion grams cannot be null");
    Objects.requireNonNull(calories, "[CreateConsumptionRecordCommand] Calories cannot be null");
    Objects.requireNonNull(dietaryTag, "[CreateConsumptionRecordCommand] Dietary tag cannot be null");
    Objects.requireNonNull(recordedAt, "[CreateConsumptionRecordCommand] Recorded at cannot be null");

    if (portionGrams < MIN_DEFAULT_PORTION_GRAMS ||
        portionGrams > MAX_DEFAULT_PORTION_GRAMS) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Portion grams must be between " +
              MIN_DEFAULT_PORTION_GRAMS + " and " + MAX_DEFAULT_PORTION_GRAMS
      );
    }

    if (mealType != MealType.BREAKFAST &&
        mealType != MealType.LUNCH &&
        mealType != MealType.DINNER &&
        mealType != MealType.SNACK) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Invalid meal type: " + mealType
      );
    }

    if (portionGrams < MIN_PORTION_GRAMS ||
        portionGrams > MAX_PORTION_GRAMS) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Portion grams must be between " +
              MIN_PORTION_GRAMS + " and " + MAX_PORTION_GRAMS
      );
    }

    if (calories < MIN_CALORIES) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Calories cannot be below " + MIN_CALORIES
      );
    }

    if (dietaryTag != DietaryTag.VEGAN &&
        dietaryTag != DietaryTag.VEGETARIAN &&
        dietaryTag != DietaryTag.GLUTEN_FREE &&
        dietaryTag != DietaryTag.STANDARD) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Invalid dietary tag: " + dietaryTag
      );
    }

    if (recordedAt.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException(
          "[CreateConsumptionRecordCommand] Recorded at cannot be in the future"
      );
    }
  }
}
