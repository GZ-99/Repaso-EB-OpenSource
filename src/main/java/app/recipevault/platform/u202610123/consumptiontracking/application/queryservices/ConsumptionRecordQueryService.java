package app.recipevault.platform.u202610123.consumptiontracking.application.queryservices;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.queries.GetConsumptionRecordByIdQuery;

import java.util.Optional;

public interface ConsumptionRecordQueryService {

  Optional<ConsumptionRecord> handle(GetConsumptionRecordByIdQuery query);
}
