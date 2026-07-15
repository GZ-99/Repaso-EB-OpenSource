package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.aggregates.Ingredient;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;
import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.resources.IngredientResource;
import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.transform.IngredientResourceFromEntityAssembler;
import app.recipevault.platform.u202610123.consumptiontracking.application.acl.CatalogACL;
import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import app.recipevault.platform.u202610123.consumptiontracking.application.queryservices.ConsumptionRecordQueryService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.queries.GetConsumptionRecordByIdQuery;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.ConsumptionRecordResource;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.CreateConsumptionRecordResource;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform.ConsumptionRecordResourceFromEntityAssembler;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform.CreateConsumptionRecordCommandFromResourceAssembler;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import app.recipevault.platform.u202610123.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/consumption-records", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Consumption Records", description = "Consumption Record Management Endpoints")
public class ConsumptionRecordController {

  private final ConsumptionRecordCommandService consumptionRecordCommandService;
  private final ConsumptionRecordQueryService consumptionRecordQueryService;
  private final CatalogACL catalogACL;
  private final IngredientCommandService ingredientCommandService;

  public ConsumptionRecordController(ConsumptionRecordCommandService consumptionRecordCommandService,
                                     ConsumptionRecordQueryService consumptionRecordQueryService,
                                     CatalogACL catalogACL,
                                     IngredientCommandService ingredientCommandService) {
    this.consumptionRecordCommandService = consumptionRecordCommandService;
    this.consumptionRecordQueryService = consumptionRecordQueryService;
    this.catalogACL = catalogACL;
    this.ingredientCommandService = ingredientCommandService;
  }

  @PostMapping
  @Operation(
      summary = "Create a new consumption record",
      description = "Creates a new consumption record based on the provided details"
  )
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "Consumption record created successfully",
          content = @Content(schema = @Schema(implementation = ConsumptionRecordResource.class))
      ),
      @ApiResponse(responseCode = "400", description = "Invalid input data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
      @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
  })
  public ResponseEntity<?> createConsumptionRecord(@RequestBody CreateConsumptionRecordResource resource) {
    var command = CreateConsumptionRecordCommandFromResourceAssembler.toCommandFromResource(resource);
    var result = this.consumptionRecordCommandService.handle(command)
        .flatMap(consumptionId
            -> this.consumptionRecordQueryService.handle(new GetConsumptionRecordByIdQuery(consumptionId))
            .<Result<ConsumptionRecord, ApplicationError>>map(Result::success)
            .orElseGet(()
                -> Result.failure(ApplicationError.notFound("consumption-record", consumptionId.toString()))));

    return ResponseEntityAssembler.toResponseEntityFromResult(result,
        ConsumptionRecordResourceFromEntityAssembler::toResourceFromEntity, HttpStatus.CREATED);
  }

  @PutMapping("/{ingredientCode}")
    @Operation(summary = "Update defaultPortionGrams", description = "Updates the defaultPortionGrams of an existing ingredient's record. Requires code and the new defaultPortionGrams.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "DefaultPortionGrams updated successfully",
                content = @Content(schema = @Schema(implementation = IngredientResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token required"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    public ResponseEntity<?> updateDefaultPortionGrams(
            @PathVariable
            @Parameter(description = "Unique ingredient code", example = "AV-OAT-001 ", required = true)
            String ingredientCode,
            @Parameter(description = "Default Portion Grams", example = "40.0", required = true)
            Double defaultPortionGrams,
            @RequestBody ConsumptionRecordRegisteredEvent event
    ) {
    try {
      var code = new IngredientCode(ingredientCode);

      var ingredientData = this.catalogACL.getIngredientData(code);

      if (ingredientData.isEmpty()) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                Result.failure(ApplicationError.notFound("ingredient", ingredientCode)),
                IngredientResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.NOT_FOUND
        );
      }
      var ing = ingredientData.get();

      var updateIngredientCommand = new UpdateIngredientCommand(
              ing.id(),
              ing.code(),
              ing.name(),
              defaultPortionGrams,
              ing.baseCaloriesPer100g()
      );

      var result = this.ingredientCommandService.handle(updateIngredientCommand)
              .<Result<Ingredient, ApplicationError>>map(Result::success)
              .orElseGet(() -> Result.failure(ApplicationError.notFound("ingredient", ingredientCode)));

      return ResponseEntityAssembler.toResponseEntityFromResult(
              result,
              IngredientResourceFromEntityAssembler::toResourceFromEntity,
              HttpStatus.OK
      );
    } catch (Exception e) {
      return ResponseEntityAssembler.toResponseEntityFromResult(
              Result.failure(ApplicationError.unexpected("ingredient", e.getMessage())),
              IngredientResourceFromEntityAssembler::toResourceFromEntity,
              HttpStatus.BAD_REQUEST
      );
    }
  }
}
