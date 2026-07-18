package com.betkowski.incidentmanager.adapters.in.web.security;

import com.betkowski.incidentmanager.TestcontainersConfiguration;
import com.betkowski.incidentmanager.security.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class SecurityIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void protectedEndpointWithoutToken_shouldReturn401() throws Exception {
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"x\",\"address\":\"1.2.3.4\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void protectedEndpointWithToken_shouldReturn201() throws Exception {
        String loginBody = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        TokenResponse token = objectMapper.readValue(loginBody, TokenResponse.class);

        mockMvc.perform(post("/devices")
                        .header("Authorization", "Bearer " + token.token())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"sec\",\"address\":\"1.2.3.4\"}"))
                .andExpect(status().isCreated());
    }
}
