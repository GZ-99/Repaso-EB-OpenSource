package app.recipevault.platform.u202610123.consumptiontracking.application.acl;

import app.recipevault.platform.u202610123.catalogmanagement.application.queryservices.IngredientQueryService;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.ExistsIngredientByCodeQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.model.queries.GetIngredientByCodeQuery;
import app.recipevault.platform.u202610123.catalogmanagement.domain.repositories.IngredientRepository;
import app.recipevault.platform.u202610123.shared.domain.model.valueobjects.IngredientCode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CatalogACLImpl implements CatalogACL {
    private final IngredientQueryService ingredientQueryService;
    public CatalogACLImpl(IngredientQueryService ingredientQueryService) {
        this.ingredientQueryService = ingredientQueryService;
    }

    @Override
    public boolean ingredientExists(IngredientCode code) {
        return ingredientQueryService.handle(
                new ExistsIngredientByCodeQuery(code)
        );
    }

    @Override
    public Optional<IngredientData> getIngredientData(IngredientCode code) {
        return ingredientQueryService.handle(
                new GetIngredientByCodeQuery(code)
        ).map(ingredient -> new IngredientData(
                ingredient.getId(),
                ingredient.getCode(),
                ingredient.getName(),
                ingredient.getDefaultPortionGrams(),
                ingredient.getBaseCaloriesPer100g()
        ));
    }
}
