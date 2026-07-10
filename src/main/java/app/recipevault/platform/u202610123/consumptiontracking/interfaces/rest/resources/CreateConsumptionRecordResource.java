package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.DietaryTag;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.MealType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Objects;

public record CreateConsumptionRecordResource(
    @Schema(
        description = "Code of the ingredient consumed",
        example = "AV-OAT-001",
        type = "string"
    )
    String ingredientCode,

    @Schema(
        description = "Type of meal (e.g., BREAKFAST, LUNCH, DINNER, SNACK)",
        example = "LUNCH",
        type = "string"
    )
    String mealType,

    @Schema(
        description = "Portion size in grams",
        example = "150.0",
        type = "number",
        format = "double"
    )
    Double portionGrams,

    @Schema(
        description = "Calories consumed",
        example = "250.0",
        type = "number",
        format = "double"
    )
    Double calories,

    @Schema(
        description = "Dietary tag associated with the consumption (e.g., VEGAN, VEGETARIAN, GLUTEN_FREE, STANDARD)",
        example = "VEGAN",
        type = "string"
    )
    String dietaryTag,

    @Schema(
        description = "Date and time when the consumption record was generated",
        example = "2025-12-16 20:13:32",
        type = "string",
        format = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime recordedAt) {

  private static final Double MIN_DEFAULT_PORTION_GRAMS = 0.0;
  private static final Double MAX_DEFAULT_PORTION_GRAMS = 500.0;
  private static final Double MIN_PORTION_GRAMS = 0.0;
  private static final Double MAX_PORTION_GRAMS = 1000.0;
  private static final Double MIN_CALORIES = 0.0;

  public CreateConsumptionRecordResource {
    Objects.requireNonNull(ingredientCode, "Ingredient code cannot be null");
    Objects.requireNonNull(mealType, "Meal type cannot be null");
    Objects.requireNonNull(portionGrams, "Portion grams cannot be null");
    Objects.requireNonNull(calories, "Calories cannot be null");
    Objects.requireNonNull(dietaryTag, "Dietary tag cannot be null");
    Objects.requireNonNull(recordedAt, "Recorded at cannot be null");

    if (portionGrams < MIN_DEFAULT_PORTION_GRAMS || portionGrams > MAX_DEFAULT_PORTION_GRAMS) {
      throw new IllegalArgumentException("Portion grams must be between "
          + MIN_DEFAULT_PORTION_GRAMS + " and " + MAX_DEFAULT_PORTION_GRAMS
      );
    }

    if (MealType.valueOf(mealType) == null) {
      throw new IllegalArgumentException("Invalid meal type: " + mealType);
    }

    if (portionGrams < MIN_PORTION_GRAMS || portionGrams > MAX_PORTION_GRAMS) {
      throw new IllegalArgumentException("Portion grams must be between "
          + MIN_PORTION_GRAMS + " and " + MAX_PORTION_GRAMS
      );
    }

    if (calories < MIN_CALORIES) {
      throw new IllegalArgumentException("Calories cannot be below " + MIN_CALORIES);
    }

    if (DietaryTag.valueOf(dietaryTag) == null) {
      throw new IllegalArgumentException("Invalid dietary tag: " + dietaryTag);
    }

    if (recordedAt.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Recorded at cannot be in the future"
      );
    }
  }
}
