package app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects;

public enum DietaryTag {
  VEGAN(0),
  VEGETARIAN(1),
  GLUTEN_FREE(2),
  STANDARD(3);

  private final int value;

  /**
   * Constructor for DietaryTag enum.
   *
   * @param value the integer identifier for the dietary tag
   */
  DietaryTag(int value) {
    this.value = value;
  }

  /**
   * Gets the integer identifier of the dietary tag.
   *
   * @param value the string representation of the dietary tag
   * @return the corresponding DietaryTag enum value
   */
  public static DietaryTag fromString(String value) {
    try {
      return DietaryTag.valueOf(value.toUpperCase());
    } catch (Exception ex) {
      throw new IllegalArgumentException(
          "[DietaryTag] Invalid dietaryTag value: " + value
      );
    }
  }
}