package app.recipevault.platform.u202610123.catalogmanagement.application.internal.commandservices;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.application.queryservices.IngredientQueryService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.SeedIngredientsCommand;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientCommandServiceImpl implements IngredientCommandService {

  private final IngredientRepository ingredientRepository;
  public IngredientCommandServiceImpl(IngredientRepository ingredientRepository) {
    this.ingredientRepository = ingredientRepository;
  }


  @Override
  public void handle(SeedIngredientsCommand command) {
    if (this.ingredientRepository.count() == 0) {
      List<Ingredient> ingredients = List.of(
          new Ingredient(new IngredientCode("AV-OAT-001"), "Rolled Oats", 40.0, 389.0 ),
          new Ingredient(new IngredientCode("AV-BER-002"), "Blueberries", 100.0, 57.0 ),
          new Ingredient(new IngredientCode("AV-OAT-001"), "Whey Protein Powder", 30.0, 400.0 ),
          new Ingredient(new IngredientCode("AV-NUT-004"), "Almond Milk", 240.0, 15.0 )
      );
      ingredients.forEach(this.ingredientRepository::save);
    }
  }

  @Override
  public Optional<Ingredient> handle(UpdateIngredientCommand command) {
    return Optional.empty();
  }
}
