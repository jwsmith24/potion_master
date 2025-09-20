package mil.swf.army.potion_master.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // owning side
    @OneToOne
    @JoinColumn(name = "alchemist_id", nullable = false, unique = true)
    private Alchemist alchemist;

    // non-owning side
    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryIngredient> ingredientList = new ArrayList<>();


    // helpers
    public void addIngredient(Ingredient ingredient, int quantity) {
        InventoryIngredient entry = new InventoryIngredient(this, ingredient, quantity);
        ingredientList.add(entry);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredientList.removeIf(inventoryIngredient -> inventoryIngredient.getIngredient().equals(ingredient));
    }

}
