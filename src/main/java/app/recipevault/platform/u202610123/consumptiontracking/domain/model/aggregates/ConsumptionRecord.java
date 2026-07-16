package app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.DietaryTag;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.MealType;
import app.recipevault.platform.u202610123.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ConsumptionRecord extends AbstractDomainAggregateRoot<ConsumptionRecord>{

  private Long id;
  private IngredientCode ingredientCode;
  private MealType mealType;
  private Double portionGrams;
  private Double calories;
  private DietaryTag dietaryTag;
  private LocalDateTime recordedAt;

  /**
   * Default constructor for JPA.
   */
  public ConsumptionRecord() {
  }

  /**
   * Constructor for ConsumptionRecord using CreateConsumptionRecordCommand.
   *
   * @param command the command to create a consumption record
   */
  public ConsumptionRecord(CreateConsumptionRecordCommand command) {
    this.ingredientCode = command.ingredientCode();
    this.mealType = command.mealType();
    this.portionGrams = command.portionGrams();
    this.calories = command.calories();
    this.dietaryTag = command.dietaryTag();
    this.recordedAt = command.recordedAt();
    this.registerEvent(new ConsumptionRecordRegisteredEvent(
        command.ingredientCode(),
        command.portionGrams()));
  }
}
