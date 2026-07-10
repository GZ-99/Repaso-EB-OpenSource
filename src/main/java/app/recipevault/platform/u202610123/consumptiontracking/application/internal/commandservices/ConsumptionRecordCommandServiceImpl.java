package app.recipevault.platform.u202610123.consumptiontracking.application.internal.commandservices;

import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.aggregates.ConsumptionRecord;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.consumptiontracking.domain.repositories.ConsumptionRecordRepository;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class ConsumptionRecordCommandServiceImpl implements ConsumptionRecordCommandService {

  private final ConsumptionRecordRepository consumptionRecordRepository;
  public ConsumptionRecordCommandServiceImpl(ConsumptionRecordRepository consumptionRecordRepository) {
    this.consumptionRecordRepository = consumptionRecordRepository;
  }

  @Override
  public Result<Long, ApplicationError> handle(CreateConsumptionRecordCommand command) {
    var ingredientCode = command.ingredientCode();
    //- falta ACL
    var consumptionRecord = new ConsumptionRecord(command);
    try {
      consumptionRecord = this.consumptionRecordRepository.save(consumptionRecord);
    } catch (Exception e) {
      return Result.failure(ApplicationError.unexpected("consumption-record", e.getMessage()));
    }
    return Result.success(consumptionRecord.getId());
  }
}
