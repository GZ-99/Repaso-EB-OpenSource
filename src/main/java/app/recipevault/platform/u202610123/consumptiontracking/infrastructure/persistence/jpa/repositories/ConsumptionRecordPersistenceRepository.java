package app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.repositories;

import app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.entities.ConsumptionRecordPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRecordPersistenceRepository extends JpaRepository<ConsumptionRecordPersistenceEntity, Long> {

}
