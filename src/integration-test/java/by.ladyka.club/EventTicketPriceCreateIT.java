package by.ladyka.club;

import by.ladyka.club.config.ClubRole;
import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.entity.EventTicketPriceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventTicketPriceCreateIT {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void whenCreateEventTicketPrice_thenItMustCreated() throws Exception {

        EventDTO eventDTOForCreateRequest = new EventDTO();
        eventDTOForCreateRequest.setName("Тестовое Название мероприятия");
        eventDTOForCreateRequest.setDescription("<p>Тестовое Описание</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>");
        eventDTOForCreateRequest.setStartEvent(LocalDateTime.now());
        eventDTOForCreateRequest.setEndEvent(LocalDateTime.now().plusHours(5));
        eventDTOForCreateRequest.setCostText("<p>Тестовое Описание стоимости</p><p data-f-id=\"pbf\" style=\"text-align: center; font-size: 14px; margin-top: 30px; opacity: 0.65; font-family: sans-serif;\">Powered by <a href=\"https://www.froala.com/wysiwyg-editor?pb=1\" title=\"Froala Editor\">Froala Editor</a></p>");
        eventDTOForCreateRequest.setRepublicPay(true);
        eventDTOForCreateRequest.setRecommendation(false);
        String eventJsonForCreateRequest = new ObjectMapper()
                .findAndRegisterModules()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .writeValueAsString(eventDTOForCreateRequest);

        String clubEventResponseJson = mvc.perform(
                post("/api/admin/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJsonForCreateRequest))
                .andReturn().getResponse().getContentAsString();

        EventDTO clubEventResponseDTO = new ObjectMapper()
                .findAndRegisterModules()
                .readValue(clubEventResponseJson, EventDTO.class);


        ClubEventTicketPriceDTO clubEventTicketPriceDTO = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO.setType(EventTicketPriceType.dance);
        clubEventTicketPriceDTO.setCost(BigDecimal.valueOf(250L));
        clubEventTicketPriceDTO.setQuantity(100);
        clubEventTicketPriceDTO.setStartActiveTime(LocalDateTime.now());
        clubEventTicketPriceDTO.setEndActiveTime(LocalDateTime.now().plusDays(10));
        clubEventTicketPriceDTO.setEventId(clubEventResponseDTO.getId());

        String priceJsonForCreateRequest = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(clubEventTicketPriceDTO);

        mvc.perform(
                post("/api/admin/events/ticket/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceJsonForCreateRequest)
        );


        mvc.perform(
                get("/api/admin/events/ticket/price?eventId=" + clubEventResponseDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO.getQuantity())))
                .andExpect(jsonPath("$.items[0].type", is(clubEventTicketPriceDTO.getType().toString())))
                .andExpect(jsonPath("$.items[0].eventId", is(clubEventTicketPriceDTO.getEventId().intValue())))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO.getEndActiveTime().toString())))*/;
    }

}
