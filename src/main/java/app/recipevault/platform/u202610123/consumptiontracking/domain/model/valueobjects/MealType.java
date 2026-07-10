package app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects;

public enum MealType {
  BREAKFAST(0),
  LUNCH(1),
  DINNER(2),
  SNACK(3);

  private final int value;

  /**
   * Constructor for MealType enum.
   *
   * @param value the integer identifier for the meal type
   */
  MealType(int value) {
    this.value = value;
  }

  /**
   * Gets the integer identifier of the meal type.
   *
   * @param value the string representation of the meal type
   * @return the corresponding MealType enum value
   */
  public static MealType fromString(String value) {
    try {
      return MealType.valueOf(value.toUpperCase());
    } catch (Exception ex) {
      throw new IllegalArgumentException(
          "[MealType] Invalid mealType value: " + value
      );
    }
  }
}
