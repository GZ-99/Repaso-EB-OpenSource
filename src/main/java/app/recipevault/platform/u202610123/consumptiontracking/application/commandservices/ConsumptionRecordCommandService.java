package app.recipevault.platform.u202610123.consumptiontracking.application.commandservices;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.commands.CreateConsumptionRecordCommand;
import app.recipevault.platform.u202610123.shared.application.result.ApplicationError;
import app.recipevault.platform.u202610123.shared.application.result.Result;

public interface ConsumptionRecordCommandService {

  Result<Long, ApplicationError> handle(CreateConsumptionRecordCommand command);
}
