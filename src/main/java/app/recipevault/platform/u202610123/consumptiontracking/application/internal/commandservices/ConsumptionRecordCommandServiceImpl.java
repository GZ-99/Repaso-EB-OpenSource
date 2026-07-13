package app.recipevault.platform.u202610123.consumptiontracking.application.internal.commandservices;

import app.recipevault.platform.u202610123.consumptiontracking.application.acl.CatalogACL;
import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionRecordCommandServiceImpl implements ConsumptionRecordCommandService {
  private final ConsumptionRecordRepository consumptionRecordRepository;
  private final CatalogACL catalogACL;
  public ConsumptionRecordCommandServiceImpl(ConsumptionRecordRepository consumptionRecordRepository,
                                             CatalogACL catalogACL) {
    this.consumptionRecordRepository = consumptionRecordRepository;
    this.catalogACL = catalogACL;
  }

  @Override
  public Result<Long, ApplicationError> handle(CreateConsumptionRecordCommand command) {
    var ingredientCode = command.ingredientCode();
    var ingredient = catalogACL.getIngredientData(command.ingredientCode());
    var consumptionRecord = new ConsumptionRecord(command);
    if (ingredient.isPresent()) {
      try {
        consumptionRecord = this.consumptionRecordRepository.save(consumptionRecord);
      } catch (Exception e) {
        return Result.failure(ApplicationError.unexpected("consumption-record", e.getMessage()));
      }
      return Result.success(consumptionRecord.getId());
    }
    else {
      return Result.failure(ApplicationError.notFound("ingredient", ingredientCode.code()));
    }
  }
}
