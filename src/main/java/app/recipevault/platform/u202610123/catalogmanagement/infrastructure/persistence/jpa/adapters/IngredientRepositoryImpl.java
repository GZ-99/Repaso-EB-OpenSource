package app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.adapters;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.assemblers.IngredientPersistenceAssembler;
import app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.repositories.IngredientPersistenceRepository;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {

  private final IngredientPersistenceRepository ingredientPersistenceRepository;
  public IngredientRepositoryImpl(IngredientPersistenceRepository ingredientPersistenceRepository) {
    this.ingredientPersistenceRepository = ingredientPersistenceRepository;
  }

  @Override
  public List<Ingredient> findAll() {
    return this.ingredientPersistenceRepository.findAll()
        .stream()
        .map(IngredientPersistenceAssembler::toDomainFromPersistence)
        .toList();
  }

  @Override
  public long count() {
    return this.ingredientPersistenceRepository.count();
  }

  @Override
  public Ingredient save(Ingredient ingredient) {
    var ingredientSaved = this.ingredientPersistenceRepository
        .save(IngredientPersistenceAssembler.toPersistenceFromDomain(ingredient));
    return IngredientPersistenceAssembler.toDomainFromPersistence(ingredientSaved);
  }

  @Override
  public Optional<Ingredient> findByCode(IngredientCode code) {
    return this.ingredientPersistenceRepository.findByCode(code)
        .map(IngredientPersistenceAssembler::toDomainFromPersistence);
  }

  @Override
  public boolean existsByCode(IngredientCode code) {
    return this.ingredientPersistenceRepository.existsByCode(code);
  }

  @Override
  public boolean existsByCodeAndIdIsNot(IngredientCode code, Long id) {
    return this.ingredientPersistenceRepository.existsByCodeAndIdIsNot(code, id);
  }
}
