package app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest;

import app.recipevault.platform.u202610123.catalogmanagement.application.queryservices.IngredientQueryService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetAllIngredientsQuery;
import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.resources.IngredientResource;
import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.transform.IngredientResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Ingredient Management", description = "APIs for managing ingredients in the recipe vault")
public class IngredientController {

  private final IngredientQueryService ingredientQueryService;

  public IngredientController(IngredientQueryService ingredientQueryService) {
    this.ingredientQueryService = ingredientQueryService;
  }

  @GetMapping
  @Operation(
      summary = "Get all ingredients",
      description = "Retrieves a list of all ingredients available in the recipe vault"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "Ingredients found",
          content = @Content(schema = @Schema(implementation = IngredientResource.class))
      )
  })
  public ResponseEntity<List<IngredientResource>> getAllIngredients() {
    var ingredients = this.ingredientQueryService.handle(new GetAllIngredientsQuery());
    if (ingredients.isEmpty()) {
      return ResponseEntity.ok(Collections.emptyList());
    }
    var ingredientResources = ingredients.stream()
        .map(IngredientResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(ingredientResources);

  }
}
