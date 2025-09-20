package mil.swf.army.potion_master.entities.dtos.core;

import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.entities.Inventory;

import java.util.List;

public class AlchemistMapper {

    public static AlchemistDto toDto(Alchemist alchemist) {
        return new AlchemistDto(
                alchemist.getId(),
                alchemist.getName(),
                alchemist.getGold(),
                toDto(alchemist.getInventory())

        );
    }

    private static InventoryDto toDto(Inventory inventory) {
        if (inventory == null) return null;

        List<InventoryIngredientDto> ingredients = inventory.getIngredientList()
                .stream()
                .map(inventoryIngredient -> new InventoryIngredientDto(
                        inventoryIngredient.getId(),
                        inventoryIngredient.getIngredient().getName(),
                        inventoryIngredient.getQuantity()
                )).toList();

        return new InventoryDto(inventory.getId(), ingredients);
    }
}
