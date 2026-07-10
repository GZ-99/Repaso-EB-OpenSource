package app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.repositories;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.entities.IngredientPersistenceEntity;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientPersistenceRepository extends JpaRepository<IngredientPersistenceEntity, Long> {

  Optional<IngredientPersistenceEntity> findByCode(IngredientCode code);
  boolean existsByCode(IngredientCode code);
  boolean existsByCodeAndIdIsNot(IngredientCode code, Long id);
}
