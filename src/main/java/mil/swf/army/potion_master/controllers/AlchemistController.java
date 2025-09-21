package mil.swf.army.potion_master.controllers;

import mil.swf.army.potion_master.entities.dtos.core.AlchemistDto;
import mil.swf.army.potion_master.entities.dtos.core.InventoryDto;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
import mil.swf.army.potion_master.entities.dtos.http.UpdateAlchemistRequest;
import mil.swf.army.potion_master.entities.dtos.http.UpdateIngredientQuantityRequest;
import mil.swf.army.potion_master.services.AlchemistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alchemist")
public class AlchemistController {

    private final AlchemistService alchemistService;

    public AlchemistController(AlchemistService alchemistService) {
        this.alchemistService = alchemistService;
    }


    @GetMapping()
    public ResponseEntity<List<AlchemistDto>> getAllAlchemists() {
        return ResponseEntity.ok(alchemistService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlchemistDto> getAlchemistById(@PathVariable Long id) {
        return ResponseEntity.ok(alchemistService.getById(id));
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<InventoryDto> getInventory(@PathVariable Long id) {
        return ResponseEntity.ok(alchemistService.getInventory(id));

    }

    @PostMapping()
    public ResponseEntity<AlchemistDto> addNewAlchemist(@RequestBody CreateAlchemistRequest request) {

       AlchemistDto created = alchemistService.createAlchemist(request);

       return ResponseEntity
               .created(URI.create("/api/v1/alchemist/" + created.id()))
               .body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlchemistDto> updateAlchemist(@PathVariable Long id, @RequestBody UpdateAlchemistRequest request) {
        AlchemistDto updated = alchemistService.updateAlchemist(id, request);

        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{alchemistId}/inventory/ingredients")
    public ResponseEntity<InventoryDto> updateIngredientQuantity(@PathVariable Long alchemistId, @RequestBody UpdateIngredientQuantityRequest request) {
        return ResponseEntity.ok(alchemistService
                .updateIngredientQuantity(alchemistId, request.ingredientId(), request.quantity()));
    }



}
