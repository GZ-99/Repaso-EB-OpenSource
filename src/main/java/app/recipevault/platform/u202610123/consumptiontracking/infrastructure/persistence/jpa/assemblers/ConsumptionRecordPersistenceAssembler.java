package app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.assemblers;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.entities.ConsumptionRecordPersistenceEntity;

public class ConsumptionRecordPersistenceAssembler {

  private ConsumptionRecordPersistenceAssembler() {
  }

  public static ConsumptionRecordPersistenceEntity toPersistenceFromDomain(ConsumptionRecord consumptionRecord) {
    ConsumptionRecordPersistenceEntity persistenceEntity = new ConsumptionRecordPersistenceEntity();
    persistenceEntity.setId(consumptionRecord.getId());
    persistenceEntity.setIngredientCode(consumptionRecord.getIngredientCode());
    persistenceEntity.setMealType(consumptionRecord.getMealType());
    persistenceEntity.setPortionGrams(consumptionRecord.getPortionGrams());
    persistenceEntity.setCalories(consumptionRecord.getCalories());
    persistenceEntity.setDietaryTag(consumptionRecord.getDietaryTag());
    persistenceEntity.setRecordedAt(consumptionRecord.getRecordedAt());
    return persistenceEntity;
  }

  public static ConsumptionRecord toDomainFromPersistence(ConsumptionRecordPersistenceEntity persistenceEntity) {
    ConsumptionRecord domain = new ConsumptionRecord();
    domain.setId(persistenceEntity.getId());
    domain.setIngredientCode(persistenceEntity.getIngredientCode());
    domain.setMealType(persistenceEntity.getMealType());
    domain.setPortionGrams(persistenceEntity.getPortionGrams());
    domain.setCalories(persistenceEntity.getCalories());
    domain.setDietaryTag(persistenceEntity.getDietaryTag());
    domain.setRecordedAt(persistenceEntity.getRecordedAt());
    return domain;
  }
}