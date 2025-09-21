package mil.swf.army.potion_master.services;

import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistDto;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
import mil.swf.army.potion_master.repos.AlchemistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlchemistServiceTest {

    @Mock
    private AlchemistRepository alchemistRepository;

    @InjectMocks AlchemistService alchemistService;


    @Test
    void shouldCreateAlchemist() {
        CreateAlchemistRequest mockRequest = new CreateAlchemistRequest("Craig the Wise", 500.0);

        when(alchemistRepository.save(any(Alchemist.class)))
                .thenAnswer(invocation -> {
                    Alchemist alchemist = invocation.getArgument(0);
                    alchemist.setId(99L);
                    return alchemist;
                });
        AlchemistDto response = alchemistService.createAlchemist(mockRequest);

        assertEquals("Craig the Wise", response.name());
        assertEquals(500.0, response.gold());
        assertEquals(99L, response.id());

        verify(alchemistRepository).save(any(Alchemist.class));
    }



}