package app.recipevault.platform.u202610123.consumptiontracking.domain.model.events;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

public record ConsumptionRecordRegisteredEvent(
        ConsumptionRecord command,
        IngredientCode ingredientCode,
        Double portionGrams) {
}
