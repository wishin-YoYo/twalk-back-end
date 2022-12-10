package server.twalk.Walking;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import server.twalk.Walking.controller.WalkingController;
import server.twalk.Walking.dto.request.WalkingReq;
import server.twalk.Walking.service.WalkingService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class WalkingControllerTest {
    @InjectMocks
    WalkingController walkingController;
    @Mock
    WalkingService walkingService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(walkingController).build();
    }

    @Test
    void Walking_생성() throws Exception {
        // given
        WalkingReq req = createWalkingReq();


        // when, then
        mockMvc.perform(
                        post("/walking")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());


    }

    @Test
    void Walking_GET() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        get("/walking/{id}", id))
                .andExpect(status().isOk());
    }

    WalkingReq createWalkingReq(){
        return new WalkingReq();
    }
}
