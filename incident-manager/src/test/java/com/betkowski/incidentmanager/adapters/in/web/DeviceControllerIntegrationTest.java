package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.TestcontainersConfiguration;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
public class DeviceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void shouldCreateDeviceAndReturn201() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name": "PR01", "address": "10.10.25.1"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("PR01"));
    }

    @Test
    void shouldNotCreateDeviceWithBadDataAndReturnBadRequest() throws Exception {
        mockMvc.perform(post("/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name": "", "address": "10.10.25.1"}
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Device name cannot be blank"));

    }

    @Test
    void shouldEnterMaintenanceAndReturn200() throws Exception {
        Device device = Device.create("PR01", "10.10.25.1");
        deviceRepository.save(device);
        UUID deviceId = device.getId();
        mockMvc.perform(post("/devices/" + deviceId + "/maintenance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("MAINTENANCE"));
    }
}
