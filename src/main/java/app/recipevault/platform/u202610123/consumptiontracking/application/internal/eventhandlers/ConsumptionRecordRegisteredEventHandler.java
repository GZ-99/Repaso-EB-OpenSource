package app.recipevault.platform.u202610123.consumptiontracking.application.internal.eventhandlers;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.UpdateIngredientCommand;
import app.recipevault.platform.u202610123.consumptiontracking.application.acl.CatalogACL;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.events.ConsumptionRecordRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumptionRecordRegisteredEventHandler {
    private final IngredientCommandService ingredientCommandService;
    private final CatalogACL catalogACL;

    private static final Logger LOGGER
      = LoggerFactory.getLogger(ConsumptionRecordRegisteredEventHandler.class);

    public ConsumptionRecordRegisteredEventHandler(IngredientCommandService ingredientCommandService,
                                                   CatalogACL catalogACL) {
        this.ingredientCommandService = ingredientCommandService;
        this.catalogACL = catalogACL;
    }

    @EventListener
    public void on(ConsumptionRecordRegisteredEvent event) {
        var ingredient = catalogACL.getIngredientData(event.ingredientCode());

        if (ingredient.isEmpty()) {
            log.warn(
                    "Ingredient with code {} was not found",
                    event.ingredientCode()
            );
            return;
        }

        var ing = ingredient.get();

        if (event.portionGrams() >= ing.defaultPortionGrams()) {
            log.info(
                    "The New Portion Grams value is invalid"
            );
            return;
        }

        ingredientCommandService.handle(
                new UpdateIngredientCommand(
                        ing.id(),
                        ing.code(),
                        ing.name(),
                        event.portionGrams(),
                        ing.baseCaloriesPer100g()
                )
        );
        log.info(
                "Ingredient {} default portion updated to {} grams",
                ing.code(),
                event.portionGrams()
        );
    }
}
