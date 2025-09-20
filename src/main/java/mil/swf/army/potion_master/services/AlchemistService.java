package mil.swf.army.potion_master.services;

import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.entities.Inventory;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistDto;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistMapper;
import mil.swf.army.potion_master.entities.dtos.core.InventoryDto;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
import mil.swf.army.potion_master.entities.dtos.http.UpdateAlchemistRequest;
import mil.swf.army.potion_master.repos.AlchemistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlchemistService {

    private final AlchemistRepository alchemistRepository;

    public AlchemistService (AlchemistRepository alchemistRepository) {
        this.alchemistRepository = alchemistRepository;
    }

    public AlchemistDto createAlchemist(CreateAlchemistRequest request) {
       Alchemist alchemist = Alchemist.builder()
               .name(request.name())
               .gold(request.gold())
               .build();

       // initialize inventory and wire up both sides of the relationship
        Inventory inventory = new Inventory();
        inventory.setAlchemist(alchemist);
        alchemist.setInventory(inventory);

       Alchemist saved = alchemistRepository.save(alchemist);

       return AlchemistMapper.toDto(saved);
    }

    public List<AlchemistDto> getAll() {

        return alchemistRepository
                .findAll()
                .stream().map(AlchemistMapper::toDto).toList();
    }

    public AlchemistDto getById(Long id) {

        Alchemist target = alchemistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alchemist with id: " + id + " not found."));

        return AlchemistMapper.toDto(target);
    }

    public AlchemistDto updateAlchemist(Long id, UpdateAlchemistRequest request) {
        Alchemist alchemist = alchemistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alchemist with id: " + id + " not found."));

        if (request.name() != null) {
            alchemist.setName(request.name());
        }

        if (request.gold() != null) {
            alchemist.setGold(request.gold());
        }

        Alchemist saved = alchemistRepository.save(alchemist);
        return AlchemistMapper.toDto(saved);
    }

    public InventoryDto getInventory(Long alchemistId) {
        Alchemist alchemist = alchemistRepository.findById(alchemistId)
                .orElseThrow(() -> new RuntimeException("Alchemist with id: " + alchemistId + " not found."));

        if (alchemist.getInventory() == null) {
            throw new IllegalStateException("Alchemist inventory does not exist..");
        }


        return AlchemistMapper.toDto(alchemist.getInventory());
    }
}
