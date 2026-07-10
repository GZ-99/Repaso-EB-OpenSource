package app.recipevault.platform.u202610123.consumptiontracking.application.internal.eventhandlers;

import app.recipevault.platform.u202610123.consumptiontracking.application.commandservices.ConsumptionRecordCommandService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class ConsumptionRecordRegisteredEventHandler {
    private final ConsumptionRecordCommandService consumptionCommandService;
    private static final Logger LOGGER
      = LoggerFactory.getLogger(ConsumptionRecordRegisteredEventHandler.class);

    public ConsumptionRecordRegisteredEventHandler(ConsumptionRecordCommandService consumptionCommandService) {
        this.consumptionCommandService = consumptionCommandService;
    }

    @EventListener
    public void on() {

    }
}
