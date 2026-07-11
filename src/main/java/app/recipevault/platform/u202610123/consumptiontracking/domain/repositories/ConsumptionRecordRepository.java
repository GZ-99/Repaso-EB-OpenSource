package app.recipevault.platform.u202610123.consumptiontracking.domain.repositories;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;

import java.util.Optional;

public interface ConsumptionRecordRepository {
  ConsumptionRecord save(ConsumptionRecord consumptionRecord);
  Optional<ConsumptionRecord> findById(Long id);
}
