package app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.adapters;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.assemblers.ConsumptionRecordPersistenceAssembler;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.repositories.ConsumptionRecordPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConsumptionRecordRepositoryImpl implements ConsumptionRecordRepository {

  private final ConsumptionRecordPersistenceRepository consumptionRecordPersistenceRepository;
  public ConsumptionRecordRepositoryImpl(ConsumptionRecordPersistenceRepository repository) {
    this.consumptionRecordPersistenceRepository = repository;
  }

  @Override
  public ConsumptionRecord save(ConsumptionRecord consumptionRecord) {
    var persistenceSaved = this.consumptionRecordPersistenceRepository
        .save(ConsumptionRecordPersistenceAssembler.toPersistenceFromDomain(consumptionRecord));
    return ConsumptionRecordPersistenceAssembler.toDomainFromPersistence(persistenceSaved);
  }

  @Override
  public Optional<ConsumptionRecord> findById(Long id) {
    return this.consumptionRecordPersistenceRepository.findById(id)
        .map(ConsumptionRecordPersistenceAssembler::toDomainFromPersistence);
  }
}
