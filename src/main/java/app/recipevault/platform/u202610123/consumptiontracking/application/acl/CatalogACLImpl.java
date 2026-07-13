package app.recipevault.platform.u202610123.consumptiontracking.application.acl;

import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CatalogACLImpl implements CatalogACL {
    private final IngredientRepository ingredientRepository;
    public CatalogACLImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public boolean ingredientExists(IngredientCode code) {
        return ingredientRepository.existsByCode(code);
    }

    @Override
    public Optional<IngredientData> getIngredientData(IngredientCode code) {
        return ingredientRepository.findByCode(code)
                .map(ingredient -> new IngredientData(
                        ingredient.getId(),
                        ingredient.getCode(),
                        ingredient.getName(),
                        ingredient.getDefaultPortionGrams(),
                        ingredient.getBaseCaloriesPer100g()
                ));
    }
}
