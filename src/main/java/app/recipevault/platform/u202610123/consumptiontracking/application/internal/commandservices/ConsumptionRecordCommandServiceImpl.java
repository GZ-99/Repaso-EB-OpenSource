package app.recipevault.platform.u202610123.consumptiontracking.application.internal.commandservices;

import app.recipevault.platform.u202610123.consumptiontracking.application.acl.CatalogACL;
import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionRecordCommandServiceImpl implements ConsumptionRecordCommandService {
  private final ConsumptionRecordRepository consumptionRecordRepository;
  private final CatalogACL catalogACL;
  private final ApplicationEventPublisher eventPublisher;
  public ConsumptionRecordCommandServiceImpl(ConsumptionRecordRepository consumptionRecordRepository,
                                             CatalogACL catalogACL,
                                             ApplicationEventPublisher eventPublisher) {
    this.consumptionRecordRepository = consumptionRecordRepository;
    this.catalogACL = catalogACL;
    this.eventPublisher = eventPublisher;
  }

  @Override
  public Result<Long, ApplicationError> handle(CreateConsumptionRecordCommand command) {
    var ingredientCode = command.ingredientCode();
    var ingredient = catalogACL.getIngredientData(command.ingredientCode());

    if (ingredient.isEmpty()) {
      return Result.failure(
              ApplicationError.notFound("ingredient", ingredientCode.code()));
    }

    var consumptionRecord = new ConsumptionRecord(command);

    try {
      consumptionRecord = this.consumptionRecordRepository.save(consumptionRecord);
      eventPublisher.publishEvent(
              new ConsumptionRecordRegisteredEvent(
                      command.ingredientCode(),
                      command.portionGrams()
              )
      );
      return Result.success(consumptionRecord.getId());
    } catch (Exception e) {
      return Result.failure(
              ApplicationError.unexpected("consumption-record", e.getMessage()));
    }
  }
}
