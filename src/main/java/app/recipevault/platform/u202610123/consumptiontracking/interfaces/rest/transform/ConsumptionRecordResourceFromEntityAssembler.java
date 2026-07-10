package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.ConsumptionRecordResource;

public class ConsumptionRecordResourceFromEntityAssembler {

  public static ConsumptionRecordResource toResourceFromEntity(ConsumptionRecord entity) {
    return new ConsumptionRecordResource(
        entity.getId(),
        entity.getIngredientCode().code(),
        entity.getMealType().toString(),
        entity.getPortionGrams(),
        entity.getCalories(),
        entity.getDietaryTag().toString(),
        entity.getRecordedAt()
    );
  }
}
