package mil.swf.army.potion_master.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InventoryIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // owning side
    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    // owning side
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    private int quantity;

    // convenience constructor
    public InventoryIngredient(Inventory inventory, Ingredient ingredient, int quantity) {
        this.inventory = inventory;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
}
