package app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.adapters;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.assemblers.ConsumptionRecordPersistenceAssembler;
import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.repositories.ConsumptionRecordPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConsumptionRecordRepositoryImpl implements ConsumptionRecordRepository {

  private final ConsumptionRecordPersistenceRepository consumptionRecordPersistenceRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  public ConsumptionRecordRepositoryImpl(ConsumptionRecordPersistenceRepository repository,
                                         ApplicationEventPublisher applicationEventPublisher) {
    this.consumptionRecordPersistenceRepository = repository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public ConsumptionRecord save(ConsumptionRecord consumptionRecord) {
    var persistenceSaved = this.consumptionRecordPersistenceRepository
        .save(ConsumptionRecordPersistenceAssembler.toPersistenceFromDomain(consumptionRecord));
    var saved = ConsumptionRecordPersistenceAssembler.toDomainFromPersistence(persistenceSaved);
    consumptionRecord.domainEvents().forEach(applicationEventPublisher::publishEvent);
    consumptionRecord.clearDomainEvents();
    return saved;
  }

  @Override
  public Optional<ConsumptionRecord> findById(Long id) {
    return this.consumptionRecordPersistenceRepository.findById(id)
        .map(ConsumptionRecordPersistenceAssembler::toDomainFromPersistence);
  }
}
