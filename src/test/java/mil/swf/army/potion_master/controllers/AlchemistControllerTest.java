package mil.swf.army.potion_master.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import mil.swf.army.potion_master.entities.Alchemist;
import mil.swf.army.potion_master.entities.dtos.http.CreateAlchemistRequest;
import mil.swf.army.potion_master.services.AlchemistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Alchemist mockAlchemist;

    @BeforeEach
    void setup() {
        mockAlchemist = new Alchemist(99L, "Test", 300.0, null);
    }

    @Test
    void shouldCreateAlchemist() throws Exception {
        CreateAlchemistRequest request = new CreateAlchemistRequest("Test", 300.0);

        when(alchemistService.createAlchemist(any(Alchemist.class)))
                .thenReturn(mockAlchemist);

        mockMvc.perform(post("/api/v1/alchemist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test"))
                        .andExpect(jsonPath("$.gold").value(300.0));


        verify(alchemistService).createAlchemist(any(Alchemist.class));
    }

}