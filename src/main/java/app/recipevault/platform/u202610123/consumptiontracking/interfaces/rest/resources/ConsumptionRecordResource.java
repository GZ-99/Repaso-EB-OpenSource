package app.recipevault.platform.u202610123.consumptiontracking.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ConsumptionRecordResource(

    Long id,
    String ingredientCode,
    String mealType,
    Double portionGrams,
    Double calories,
    String dietaryTag,
    LocalDateTime recordedAt) {
}