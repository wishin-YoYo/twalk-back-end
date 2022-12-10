package server.twalk.PvP;

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
import server.twalk.PvP.controller.PvpController;
import server.twalk.PvP.dto.PvpReq;
import server.twalk.PvP.service.PvPService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PvpControllerTest {
    @InjectMocks
    PvpController pvpController;
    @Mock
    PvPService pvPService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(pvpController).build();
    }

    @Test
    void PVP_생성() throws Exception {
        // given
        PvpReq req = createPvpReq();


        // when, then
        mockMvc.perform(
                        post("/pvp")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());


    }

    @Test
    void PVP_GET() throws Exception {
            // given
            Long id = 1L;

            // when, then
            mockMvc.perform(
                            get("/pvp/{id}", id))
                    .andExpect(status().isOk());
        }

    PvpReq createPvpReq(){
        return new PvpReq();
    }
}