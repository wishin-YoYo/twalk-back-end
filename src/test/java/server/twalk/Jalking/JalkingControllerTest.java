package server.twalk.Jalking;


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
import server.twalk.Walking.controller.JalkingController;
import server.twalk.Walking.dto.request.JalkingReq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class JalkingControllerTest {
    @InjectMocks
    JalkingController jalkingController;
    @Mock
    server.twalk.Walking.service.JalkingService jalkingService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(jalkingController).build();
    }

    @Test
    void Jalking_생성() throws Exception {
        // given
        JalkingReq req = createJalkingReq();


        // when, then
        mockMvc.perform(
                        post("/jalking")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());


    }

    @Test
    void Jalking_조회() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        get("/jalking/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void Jalking_거절() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        put("/jalking-reject/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void Jalking_취소() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        put("/jalking-cancel/{id}", id))
                .andExpect(status().isOk());
    }


    @Test
    void Jalking_승인() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        put("/jalking-approve/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void Jalking_종료() throws Exception {
        // given
        Long id = 1L;

        // when, then
        mockMvc.perform(
                        put("/jalking/end/{id}", id))
                .andExpect(status().isOk());
    }

    JalkingReq createJalkingReq(){
        return new JalkingReq();
    }
}
