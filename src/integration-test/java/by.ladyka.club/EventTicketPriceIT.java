package by.ladyka.club;

import by.ladyka.club.config.ClubRole;
import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.entity.EventTicketPriceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.replacers.Replacer;
import com.github.database.rider.spring.api.DBRider;
import org.dbunit.dataset.ReplacementDataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@DBUnit(replacers = EventTicketPriceIT.class)
public class EventTicketPriceIT implements Replacer {

    private static LocalDateTime _YESTERDAY_ = LocalDateTime.now().minusDays(1);
    private static LocalDateTime _PLUS_5_HOURS_ = LocalDateTime.now().plusHours(5);

    @Override
    public void addReplacements(ReplacementDataSet dataSet) {
        dataSet.addReplacementSubstring("_YESTERDAY_", Timestamp.valueOf(_YESTERDAY_).toString());
        dataSet.addReplacementSubstring("_PLUS_5_HOURS_", Timestamp.valueOf(_PLUS_5_HOURS_).toString());
    }

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/EventTicketPriceIT/event.yml"},
            cleanBefore = true
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void givenClubEventAndAdminAuth_whenCreateValidEventTicketPrice_thenItMustBeCreated() throws Exception {

        ClubEventTicketPriceDTO clubEventTicketPriceDTO = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO.setType(EventTicketPriceType.dance);
        clubEventTicketPriceDTO.setCost(BigDecimal.valueOf(250L));
        clubEventTicketPriceDTO.setQuantity(100);
        clubEventTicketPriceDTO.setStartActiveTime(LocalDateTime.now().minusHours(1));
        clubEventTicketPriceDTO.setEndActiveTime(LocalDateTime.now().plusDays(10));
        clubEventTicketPriceDTO.setEventId(1L);

        String clubEventTicketPriceJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(clubEventTicketPriceDTO);

        mvc.perform(
                post("/api/admin/events/ticket/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clubEventTicketPriceJson)
        );


        mvc.perform(
                get("/api/admin/events/ticket/price")
                        .param("eventId", "1")
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO.getQuantity())))
                .andExpect(jsonPath("$.items[0].cost", is(clubEventTicketPriceDTO.getCost().intValue())))
                .andExpect(jsonPath("$.items[0].type", is(clubEventTicketPriceDTO.getType().toString())))
                .andExpect(jsonPath("$.items[0].eventId", is(clubEventTicketPriceDTO.getEventId().intValue())))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO.getEndActiveTime().toString())))*/;
    }

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/EventTicketPriceIT/event.yml",
                    "datasets/EventTicketPriceIT/eventTicketPriceForUpdate.yml"},
            cleanBefore = true
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void givenClubEventAndAdminAuth_whenUpdateEventTicketPrice_thenCostAndTypeValueMustNotBeUpdated() throws Exception {

        ClubEventTicketPriceDTO clubEventTicketPriceDTO_forUpdate = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO_forUpdate.setId(1L);
        clubEventTicketPriceDTO_forUpdate.setEventId(1L);
        clubEventTicketPriceDTO_forUpdate.setType(EventTicketPriceType.table);
        clubEventTicketPriceDTO_forUpdate.setCost(BigDecimal.valueOf(600L));
        clubEventTicketPriceDTO_forUpdate.setQuantity(400);
        clubEventTicketPriceDTO_forUpdate.setStartActiveTime(_YESTERDAY_);
        clubEventTicketPriceDTO_forUpdate.setEndActiveTime(LocalDateTime.now().minusHours(1));

        String clubEventTicketPriceJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(clubEventTicketPriceDTO_forUpdate);

        mvc.perform(
                post("/api/admin/events/ticket/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clubEventTicketPriceJson)
        );

        mvc.perform(
                get("/api/admin/events/ticket/price")
                        .param("eventId", "1")
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO_forUpdate.getQuantity())))
                .andExpect(jsonPath("$.items[0].type", is(EventTicketPriceType.dance.toString())))
                .andExpect(jsonPath("$.items[0].cost", is(550D)))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO_forUpdate.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO_forUpdate.getEndActiveTime().toString())))*/;
    }


}
