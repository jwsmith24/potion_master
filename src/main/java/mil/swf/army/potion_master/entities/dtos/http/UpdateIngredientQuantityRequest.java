package mil.swf.army.potion_master.entities.dtos.http;

public record UpdateIngredientQuantityRequest(
        Long ingredientId,
        int quantity
) {
}
