package app.recipevault.platform.u202610123.consumptiontracking.infrastructure.persistence.jpa.entities;

import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.DietaryTag;
import app.recipevault.platform.u202610123.consumptiontracking.domain.model.valueobjects.MealType;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import app.recipevault.platform.u202610123.shared.infrastructure.persistence.jpa.converters.IngredientCodePersistenceConverter;
import app.recipevault.platform.u202610123.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consumption_records")
@Getter
@Setter
public class ConsumptionRecordPersistenceEntity extends AuditableAbstractPersistenceEntity {

  @Convert(converter = IngredientCodePersistenceConverter.class)
  @Column(name = "ingredient_code", length = 10, nullable = false)
  private IngredientCode ingredientCode;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "meal_type", nullable = false)
  private MealType mealType;

  @Column(name = "portion_grams", nullable = false)
  private Double portionGrams;

  @Column(name = "calories", nullable = false)
  private Double calories;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "dietary_tag", nullable = false)
  private DietaryTag dietaryTag;

  @Temporal(TemporalType.TIMESTAMP)
  @Past
  @Column(name = "recorded_at", nullable = false)
  private LocalDateTime recordedAt;
}
