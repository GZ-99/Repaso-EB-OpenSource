package app.recipevault.platform.u202610123.catalogmanagement.application.queryservices;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.ExistsIngredientByCodeQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetAllIngredientsQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetIngredientByCodeQuery;

import java.util.List;
import java.util.Optional;

public interface IngredientQueryService {

  List<Ingredient> handle(GetAllIngredientsQuery query);
  Optional<Ingredient> handle(GetIngredientByCodeQuery query);
  boolean handle(ExistsIngredientByCodeQuery query);
}
