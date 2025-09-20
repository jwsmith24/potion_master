package mil.swf.army.potion_master.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mil.swf.army.potion_master.entities.dtos.core.AlchemistDto;
import mil.swf.army.potion_master.entities.dtos.core.InventoryDto;
import mil.swf.army.potion_master.entities.dtos.core.InventoryIngredientDto;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
import mil.swf.army.potion_master.entities.dtos.http.UpdateAlchemistRequest;
import mil.swf.army.potion_master.services.AlchemistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlchemistController.class)
class AlchemistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AlchemistService alchemistService;

    private final List<InventoryIngredientDto> mockIngredientsList = List.of(
            new InventoryIngredientDto(1L, "Mandrake root", 3),
            new InventoryIngredientDto(2L, "Phoenix feather", 1)
    );

    private final InventoryDto mockEmptyInventoryDto = new InventoryDto(1L, new ArrayList<>());
    private final InventoryDto mockFullInventoryDto = new InventoryDto(1L, mockIngredientsList);

    private final AlchemistDto mockAlchemistDtoWithEmptyInventory = new AlchemistDto(99L, "Test", 300.0);
    private final AlchemistDto mockAlchemistDtoWithPopulatedInventory = new AlchemistDto(99L, "Test", 300.0);

    private final List<AlchemistDto> alchemistDtoList = List.of(
            new AlchemistDto(99L, "Test1", 300.0),
            new AlchemistDto(100L, "Test2", 30.0),
            new AlchemistDto(101L, "Test3", 3000.0)
    );



    @Test
    void shouldCreateAlchemist() throws Exception {
        CreateAlchemistRequest mockCreateRequest = new CreateAlchemistRequest("Test", 300.0);

        when(alchemistService.createAlchemist(any(CreateAlchemistRequest.class)))
                .thenReturn(mockAlchemistDtoWithEmptyInventory);

        mockMvc.perform(post("/api/v1/alchemist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(99L))
                .andExpect(jsonPath("$.name").value("Test"))
                .andExpect(jsonPath("$.gold").value(300.0));

        verify(alchemistService).createAlchemist(any(CreateAlchemistRequest.class));
    }


    @Test
    void shouldGetAllAlchemists() throws Exception {

        when(alchemistService.getAll()).thenReturn(alchemistDtoList);

        mockMvc.perform(get("/api/v1/alchemist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));

        verify(alchemistService).getAll();
    }

    @Test
    void shouldGetAlchemistById() throws Exception {
        Long targetId = mockAlchemistDtoWithEmptyInventory.id();
        when(alchemistService.getById(targetId))
                .thenReturn(mockAlchemistDtoWithEmptyInventory);

        mockMvc.perform(get("/api/v1/alchemist/" + targetId))
                .andExpect(status().isOk());

        verify(alchemistService).getById(eq(targetId));


    }

    @Test
    void shouldUpdateAlchemistName() throws Exception {
        // initial Test, new Updated
        UpdateAlchemistRequest request = new UpdateAlchemistRequest("Craig the Wise", 300.0);
        AlchemistDto updatedMock = new AlchemistDto(99L, "Craig the Wise", 300.0);

        when(alchemistService.updateAlchemist(eq(99L), any(UpdateAlchemistRequest.class)))
                .thenReturn(updatedMock);

        mockMvc.perform(patch("/api/v1/alchemist/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Craig the Wise"))
                .andExpect(jsonPath("$.gold").value(300.0));

        verify(alchemistService).updateAlchemist(eq(99L), any(UpdateAlchemistRequest.class));
    }

    @Test
    void shouldUpdateAlchemistGold() throws Exception {
        UpdateAlchemistRequest request = new UpdateAlchemistRequest("Craig the Wise", 3500.0);
        AlchemistDto updatedMock = new AlchemistDto(99L, "Craig the Wise", 3500.0);

        when(alchemistService.updateAlchemist(eq(99L), any(UpdateAlchemistRequest.class)))
                .thenReturn(updatedMock);

        mockMvc.perform(patch("/api/v1/alchemist/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Craig the Wise"))
                .andExpect(jsonPath("$.gold").value(3500.0));

        verify(alchemistService).updateAlchemist(eq(99L), any(UpdateAlchemistRequest.class));


    }

    @Test
    void shouldGetInventory() throws Exception {
        when(alchemistService.getInventory(any(Long.class)))
                .thenReturn(mockFullInventoryDto);

        mockMvc.perform(get("/api/v1/alchemist/99/inventory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

}