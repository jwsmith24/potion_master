package mil.swf.army.potion_master.entities.dtos.core;

import java.util.List;

public record InventoryDto (
        Long id,
        List<InventoryIngredientDto> ingredients
){
}
