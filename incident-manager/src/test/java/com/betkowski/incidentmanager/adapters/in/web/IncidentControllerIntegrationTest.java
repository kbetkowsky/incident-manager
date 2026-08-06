package com.betkowski.incidentmanager.adapters.in.web;

import com.betkowski.incidentmanager.TestcontainersConfiguration;
import com.betkowski.incidentmanager.domain.model.Device;
import com.betkowski.incidentmanager.domain.model.EventType;
import com.betkowski.incidentmanager.domain.model.Incident;
import com.betkowski.incidentmanager.domain.port.DeviceRepository;
import com.betkowski.incidentmanager.domain.port.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import(TestcontainersConfiguration.class)
public class IncidentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IncidentRepository incidentRepository;

     private Incident createOpenIncident() {
         Device device = Device.create("PR01", "10.10.25.1");
         deviceRepository.save(device);
         Incident incident = Incident.create(device.getId(), device.getName(), EventType.UNRESPONSIVE);
         incidentRepository.save(incident);
         return incident;
     }

     @Test
    void shouldAcknowlegdeIncidentAndReturn200() throws Exception {
         Incident incident = createOpenIncident();

         mockMvc.perform(post("/incidents/" + incident.getId() + "/acknowledge"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.status").value("ACKNOWLEDGED"));
     }

     @Test
    void shouldResolveIncidentAndReturn200() throws Exception {
         Incident incident = createOpenIncident();

         mockMvc.perform(post("/incidents/" + incident.getId() + "/resolve"))
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.status").value("RESOLVED"));
     }

     @Test
    void shouldReturn409WhenAcknowledgingAlreadyAcknowledgeIncident() throws Exception {
         Incident incident = createOpenIncident();
         incident.acknowledge();
         incidentRepository.save(incident);

         mockMvc.perform(post("/incidents/" + incident.getId() + "/acknowledge"))
                 .andDo(print())
                 .andExpect(status().isConflict());
     }
}
