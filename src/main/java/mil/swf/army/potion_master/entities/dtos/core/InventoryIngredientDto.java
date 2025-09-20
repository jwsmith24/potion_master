package mil.swf.army.potion_master.entities.dtos.core;

public record InventoryIngredientDto(
        Long id,
        String ingredientName,
        int quantity
) {
}
