package app.recipevault.platform.u202610123.catalogmanagement.application.internal.queryservices;

import app.recipevault.platform.u202610123.catalogmanagement.application.queryservices.IngredientQueryService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.ExistsIngredientByCodeQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetAllIngredientsQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetIngredientByCodeQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientQueryServiceImpl implements IngredientQueryService {

  private final IngredientRepository ingredientRepository;
  public IngredientQueryServiceImpl(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }

  @Override
  public List<Ingredient> handle(GetAllIngredientsQuery query) {
    return this.ingredientRepository.findAll();
  }

  @Override
  public Optional<Ingredient> handle(GetIngredientByCodeQuery query) {
    return this.ingredientRepository.findByCode(query.code());
  }

  @Override
  public boolean handle(ExistsIngredientByCodeQuery query) {
    return this.ingredientRepository.existsByCode(query.code());
  }
}
