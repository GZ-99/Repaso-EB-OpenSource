package app.recipevault.platform.u202610123.consumptiontracking.application.internal.queryservices;

import app.recipevault.platform.u202610123.consumptiontracking.application.queryservices.ConsumptionRecordQueryService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.queries.GetConsumptionRecordByIdQuery;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.assemblers.ConsumptionRecordPersistenceAssembler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsumptionRecordQueryServiceImpl implements ConsumptionRecordQueryService {

  private final ConsumptionRecordRepository consumptionRecordRepository;
  public ConsumptionRecordQueryServiceImpl(ConsumptionRecordRepository consumptionRecordRepository) {
    this.consumptionRecordRepository = consumptionRecordRepository;
  }

  @Override
  public Optional<ConsumptionRecord> handle(GetConsumptionRecordByIdQuery query) {
    return this.consumptionRecordRepository.findById(query.consumptionRecordId());
  }
}
