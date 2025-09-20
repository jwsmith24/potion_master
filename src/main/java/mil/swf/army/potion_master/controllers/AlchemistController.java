package mil.swf.army.potion_master.controllers;

import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistDto;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistMapper;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
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

        List<Alchemist> alchemists = alchemistService.getAll();

        List<AlchemistDto> dtoList = alchemists
                .stream()
                .map(AlchemistMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping()
    public ResponseEntity<AlchemistDto> addNewAlchemist(@RequestBody CreateAlchemistRequest request) {
        Alchemist entity = new Alchemist();
        entity.setName(request.name());
        entity.setGold(request.gold());

       Alchemist created = alchemistService.createAlchemist(entity);

       return ResponseEntity
               .created(URI.create("/api/v1/alchemist/" + created.getId()))
               .body(AlchemistMapper.toDto(created));
    }



}
