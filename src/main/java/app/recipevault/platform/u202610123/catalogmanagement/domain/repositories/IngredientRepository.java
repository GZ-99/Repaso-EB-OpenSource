package app.recipevault.platform.u202610123.catalogmanagement.domain.repositories;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository {
  //Este es de GET
  List<Ingredient> findAll();

  //Estos no son de GET
  long count();
  Ingredient save(Ingredient ingredient);
  Optional<Ingredient> findByCode(IngredientCode code);
  boolean existsByCode(IngredientCode code);
  boolean existsByCodeAndIdIsNot(IngredientCode code, Long id);
}
