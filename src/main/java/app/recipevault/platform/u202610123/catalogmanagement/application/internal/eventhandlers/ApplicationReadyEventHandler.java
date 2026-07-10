package app.recipevault.platform.u202610123.catalogmanagement.application.internal.eventhandlers;

import app.recipevault.platform.u202610123.catalogmanagement.application.commandservices.IngredientCommandService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.commands.SeedIngredientsCommand;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
public class ApplicationReadyEventHandler {

  private final IngredientCommandService ingredientCommandService;
  private static final Logger LOGGER
      = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

  public ApplicationReadyEventHandler(IngredientCommandService ingredientCommandService) {
    this.ingredientCommandService = ingredientCommandService;
  }

  @EventListener
  public void on(ApplicationReadyEvent event) {
    var applicationName = event.getApplicationContext().getId();
    LOGGER.info("Starting to verify if ingredients seeding is needed for {} at {}",
        applicationName, currentTimestamp());

    var seedIngredientsCommand = new SeedIngredientsCommand();
    this.ingredientCommandService.handle(seedIngredientsCommand);

    LOGGER.info("Ingredients seeding verification finished for {} at {}",
        applicationName, currentTimestamp());

  }

  private Timestamp currentTimestamp() {
    return new Timestamp(System.currentTimeMillis());
  }
}
