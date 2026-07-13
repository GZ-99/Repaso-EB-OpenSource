package app.recipevault.platform.u202610123.consumptiontracking.application.internal.eventhandlers;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.consumptiontracking.application.acl.CatalogACL;
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
    private final CatalogACL catalogACL;

    private static final Logger LOGGER
      = LoggerFactory.getLogger(ConsumptionRecordRegisteredEventHandler.class);

    public ConsumptionRecordRegisteredEventHandler(IngredientRepository ingredientRepository,
                                                   IngredientCommandService ingredientCommandService,
                                                   CatalogACL catalogACL) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCommandService = ingredientCommandService;
        this.catalogACL = catalogACL;
    }

    @EventListener
    public void on(ConsumptionRecordRegisteredEvent event) {
        //var ingredient = ingredientRepository.findByCode(event.ingredientCode());
        var ingredient = catalogACL.getIngredientData(event.ingredientCode());
        //if (ingredientRepository.existsByCode(event.ingredientCode()))
        if (ingredient.isPresent()) {
            var ing = ingredient.get();
            if (event.portionGrams() < ing.defaultPortionGrams()) {
                var updateIngredientCommand = new UpdateIngredientCommand(ing.id(),
                        ing.code(), ing.name(), event.portionGrams(), ing.baseCaloriesPer100g());
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
