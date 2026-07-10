package app.recipevault.platform.u202610123.catalogmanagement.infrastructure.persistence.jpa.entities;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import app.recipevault.platform.u202610123.shared.infrastructure.persistence.jpa.converters.IngredientCodePersistenceConverter;
import app.recipevault.platform.u202610123.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class IngredientPersistenceEntity extends AuditableAbstractPersistenceEntity {

  @Convert(converter = IngredientCodePersistenceConverter.class)
  @Column(name = "code", length = 10, nullable = false)
  private IngredientCode code;

  @Column(nullable = false, name = "name")
  private String name;

  @Column(name = "default_portion_grams", nullable = false)
  private Double defaultPortionGrams;

  @Column(name = "base_calories_per_100g", nullable = false)
  private Double baseCaloriesPer100g;


}
