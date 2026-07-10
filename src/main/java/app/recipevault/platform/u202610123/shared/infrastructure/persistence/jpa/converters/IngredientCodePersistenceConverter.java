package app.recipevault.platform.u202610123.shared.infrastructure.persistence.jpa.converters;

import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class IngredientCodePersistenceConverter implements AttributeConverter<IngredientCode, String> {
  @Override
  public String convertToDatabaseColumn(IngredientCode attribute) {
    return attribute == null ? null : attribute.code();
  }

  @Override
  public IngredientCode convertToEntityAttribute(String dbData) {
    return dbData == null ? null : new IngredientCode(dbData);
  }
}
