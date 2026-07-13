package app.recipevault.platform.u202610123.consumptiontracking.application.internal.eventhandlers;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class ConsumptionRecordRegisteredEventHandler {
    private final IngredientRepository ingredientRepository;
    private final IngredientCommandService ingredientCommandService;

    private static final Logger LOGGER
      = LoggerFactory.getLogger(ConsumptionRecordRegisteredEventHandler.class);

    public ConsumptionRecordRegisteredEventHandler(IngredientRepository ingredientRepository,
                                                   IngredientCommandService ingredientCommandService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandService = ingredientCommandService;
    }

    @EventListener
    public void on(ConsumptionRecordRegisteredEvent event) {
        if (ingredientRepository.existsByCode(event.ingredientCode())) {
            var ingredient = ingredientRepository.findByCode(event.ingredientCode());
            var ing = ingredient.get();
            if (event.portionGrams() < ing.getDefaultPortionGrams()) {
                var updateIngredientCommand = new UpdateIngredientCommand(ing.getId(),
                        ing.getCode(), ing.getName(), event.portionGrams(), ing.getBaseCaloriesPer100g());
                this.ingredientCommandService.handle(updateIngredientCommand);
                LOGGER.info("Process of Updating Portion Grams per serving completed in {}",
                        currentTimestamp());
            }
            else {
                LOGGER.info("The New Portion Grams value is invalid");
            }
        } else {
            LOGGER.info("The Ingredient does not exist");
        }
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
