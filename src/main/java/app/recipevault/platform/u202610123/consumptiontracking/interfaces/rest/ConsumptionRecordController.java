package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest;

import app.recipevault.platform.u202610123.catalogmanagement.interfaces.rest.resources.IngredientResource;
import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import app.recipevault.platform.u202610123.consumptiontracking.application.queryservices.ConsumptionRecordQueryService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.queries.GetConsumptionRecordByIdQuery;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.ConsumptionRecordResource;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources.CreateConsumptionRecordResource;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform.ConsumptionRecordResourceFromEntityAssembler;
import app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.transform.CreateConsumptionRecordCommandFromResourceAssembler;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;
import app.recipevault.platform.u202610123.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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

  public ConsumptionRecordController(ConsumptionRecordCommandService consumptionRecordCommandService,
                                     ConsumptionRecordQueryService consumptionRecordQueryService) {
    this.consumptionRecordCommandService = consumptionRecordCommandService;
    this.consumptionRecordQueryService = consumptionRecordQueryService;
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
}
