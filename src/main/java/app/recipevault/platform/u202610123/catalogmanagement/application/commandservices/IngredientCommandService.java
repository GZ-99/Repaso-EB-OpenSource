package app.recipevault.platform.u202610123.catalogmanagement.application.commandservices;

import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.SeedIngredientsCommand;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;

import java.util.Optional;

public interface IngredientCommandService {

  void handle(SeedIngredientsCommand command);
  Optional<Ingredient> handle(UpdateIngredientCommand command);

}
