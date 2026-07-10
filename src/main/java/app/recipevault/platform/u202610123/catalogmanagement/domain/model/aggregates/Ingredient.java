package app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates;

import app.recipevault.platform.u202610123.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Ingredient extends AbstractDomainAggregateRoot<Ingredient>{

  private Long id;
  private IngredientCode code;
  private String name;
  private Double defaultPortionGrams;
  private Double baseCaloriesPer100g;

  public Ingredient() {
  }

  public Ingredient(IngredientCode code, String name, Double defaultPortionGrams,
                    Double baseCaloriesPer100g) {
    this.code = code;
    this.name = name;
    this.defaultPortionGrams = defaultPortionGrams;
    this.baseCaloriesPer100g = baseCaloriesPer100g;
  }

}
