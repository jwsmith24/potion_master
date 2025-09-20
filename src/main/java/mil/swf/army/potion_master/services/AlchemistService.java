package mil.swf.army.potion_master.services;

import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.repos.AlchemistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlchemistService {

    private final AlchemistRepository alchemistRepository;

    public AlchemistService (AlchemistRepository alchemistRepository) {
        this.alchemistRepository = alchemistRepository;
    }

    public Alchemist createAlchemist(Alchemist newAlchemist) {
        return alchemistRepository.save(newAlchemist);
    }

    public List<Alchemist> getAll() {
        return alchemistRepository.findAll();
    }
}
