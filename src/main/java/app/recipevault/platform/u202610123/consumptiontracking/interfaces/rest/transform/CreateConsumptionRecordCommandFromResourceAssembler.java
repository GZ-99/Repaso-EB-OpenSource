package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.DietaryTag;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.MealType;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.CreateConsumptionRecordResource;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

public class CreateConsumptionRecordCommandFromResourceAssembler {

  public static CreateConsumptionRecordCommand toCommandFromResource(CreateConsumptionRecordResource resource) {
    return new CreateConsumptionRecordCommand(
        new IngredientCode(resource.ingredientCode()),
        MealType.fromString(resource.mealType()),
        resource.portionGrams(),
        resource.calories(),
        DietaryTag.fromString(resource.dietaryTag()),
        resource.recordedAt()
    );

  }
}
