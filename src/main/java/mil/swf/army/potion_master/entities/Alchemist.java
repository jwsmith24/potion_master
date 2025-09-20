package mil.swf.army.potion_master.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Alchemist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double gold;

    // non-owning side
    @OneToOne(mappedBy = "alchemist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Inventory inventory;
}
