package by.ladyka.club;

import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.tikets.TicketPlaceDto;
import by.ladyka.club.dto.tikets.TicketPlaceStatus;
import by.ladyka.club.dto.tikets.TicketTableDto;
import by.ladyka.club.dto.tikets.TicketsOrderDto;
import by.ladyka.club.entity.EventTicketPriceType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
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
import java.util.*;

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
@DBUnit(replacers = ClubEventTicketPriceIT.class)
public class ClubEventTicketPriceIT implements Replacer {

    private static LocalDateTime _YESTERDAY_ = LocalDateTime.now().minusDays(1);
    private static LocalDateTime _PLUS_5_HOURS_ = LocalDateTime.now().plusHours(5);
    private static LocalDateTime _MINUS_8_HOURS_ = LocalDateTime.now().minusHours(8);
    private static LocalDateTime _TOMORROW_ = LocalDateTime.now().plusDays(1);

    @Override
    public void addReplacements(ReplacementDataSet dataSet) {
        dataSet.addReplacementSubstring("_YESTERDAY_", Timestamp.valueOf(_YESTERDAY_).toString());
        dataSet.addReplacementSubstring("_PLUS_5_HOURS_", Timestamp.valueOf(_PLUS_5_HOURS_).toString());
        dataSet.addReplacementSubstring("_MINUS_8_HOURS_", Timestamp.valueOf(_MINUS_8_HOURS_).toString());
        dataSet.addReplacementSubstring("_TOMORROW_", Timestamp.valueOf(_TOMORROW_).toString());
    }

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEvent.yml"},
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "CLUB_EVENT"}
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void whenCreateValidEventTicketPrice_thenItMustBeCreated() throws Exception {

        ClubEventTicketPriceDTO clubEventTicketPriceDTO = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO.setTypePrice(EventTicketPriceType.dance);
        clubEventTicketPriceDTO.setCost(BigDecimal.valueOf(250L));
        clubEventTicketPriceDTO.setQuantity(100L);
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
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO.getQuantity().intValue())))
                .andExpect(jsonPath("$.items[0].cost", is(clubEventTicketPriceDTO.getCost().intValue())))
                .andExpect(jsonPath("$.items[0].typePrice", is(clubEventTicketPriceDTO.getTypePrice().toString())))
                .andExpect(jsonPath("$.items[0].eventId", is(clubEventTicketPriceDTO.getEventId().intValue())))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO.getEndActiveTime().toString())))*/;
    }

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEvent.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEventTicketPriceForUpdate.yml"},
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "CLUB_EVENT", "CLUB_EVENT_TICKET_PRICE"}
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void givenTicketPriceWithoutAnyOrders_whenUpdateEventTicketPrice_allFieldsMayBeUpdated() throws Exception {

        ClubEventTicketPriceDTO clubEventTicketPriceDTO_forUpdate = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO_forUpdate.setId(1L);
        clubEventTicketPriceDTO_forUpdate.setEventId(1L);
        clubEventTicketPriceDTO_forUpdate.setTypePrice(EventTicketPriceType.table);
        clubEventTicketPriceDTO_forUpdate.setCost(BigDecimal.valueOf(600L));
        clubEventTicketPriceDTO_forUpdate.setQuantity(400L);
        clubEventTicketPriceDTO_forUpdate.setStartActiveTime(_YESTERDAY_);
        clubEventTicketPriceDTO_forUpdate.setEndActiveTime(LocalDateTime.now().plusHours(10L));

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
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO_forUpdate.getQuantity().intValue())))
                .andExpect(jsonPath("$.items[0].typePrice", is(clubEventTicketPriceDTO_forUpdate.getTypePrice().toString())))
                .andExpect(jsonPath("$.items[0].cost", is(clubEventTicketPriceDTO_forUpdate.getCost().intValue())))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO_forUpdate.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO_forUpdate.getEndActiveTime().toString())))*/;
    }

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEvent.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEventTicketPriceForUpdate.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/oneTicketOrder.yml"},
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "CLUB_EVENT", "CLUB_EVENT_TICKET_PRICE", "TICKET_ORDER"}
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void givenAtLeastOneOrderBindToTicketPrice_whenUpdateEventTicketPrice_thenCostAndTypeValueMustNotBeUpdated() throws Exception {

        ClubEventTicketPriceDTO clubEventTicketPriceDTO_forUpdate = new ClubEventTicketPriceDTO();
        clubEventTicketPriceDTO_forUpdate.setId(1L);
        clubEventTicketPriceDTO_forUpdate.setEventId(1L);
        clubEventTicketPriceDTO_forUpdate.setTypePrice(EventTicketPriceType.table);
        clubEventTicketPriceDTO_forUpdate.setCost(BigDecimal.valueOf(600L));
        clubEventTicketPriceDTO_forUpdate.setQuantity(400L);
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
                .andExpect(jsonPath("$.items[0].quantity", is(clubEventTicketPriceDTO_forUpdate.getQuantity().intValue())))
                .andExpect(jsonPath("$.items[0].typePrice", is(EventTicketPriceType.dance.toString())))
                .andExpect(jsonPath("$.items[0].cost", is(550D)))
                //TODO: resolve problem with serialized LocalDataTime format
                /*.andExpect(jsonPath("$.items[0].startActiveTime", is(clubEventTicketPriceDTO_forUpdate.getStartActiveTime().toString())))
                .andExpect(jsonPath("$.items[0].endActiveTime", is(clubEventTicketPriceDTO_forUpdate.getEndActiveTime().toString())))*/;
    }

    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEvent.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEventTicketPrices.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/ticketOrders.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/ticketOrderItems.yml"},
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "CLUB_EVENT", "CLUB_EVENT_TICKET_PRICE", "TICKET_ORDER", "TICKET_ORDER_ITEM"}
    )
    public void givenClubEventsAndItsPricesAndTicketOrders_whenGetClubEvent_thenItMustReturnActiveAndLowestAndAvailablePrice() throws Exception{
        mvc.perform(
                get("/api/events")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON)

        )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.costDance", is(150D)))
                .andExpect(jsonPath("$.costTablePlace", is(100D)));
        System.out.println();
    }


    @Test
    @Transactional
    @DataSet(
            value = {"datasets/common/users.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEvent.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/clubEventTicketPrices.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/ticketOrders.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/ticketOrderItems.yml"},
            cleanBefore = true,
            tableOrdering = {"USERS", "AUTHORITIES", "CLUB_EVENT", "CLUB_EVENT_TICKET_PRICE", "TICKET_ORDER", "TICKET_ORDER_ITEM"}
    )
    @ExpectedDataSet(
            value = {
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/expectedTicketOrder.yml",
                    "datasets/by/ladyka/club/ClubEventTicketPriceIT/expectedTicketOrderItems.yml"
            },
            compareOperation = CompareOperation.CONTAINS
    )
    @WithMockUser(username = "admin", authorities = ClubRole.ROLE_ADMIN)
    public void givenAvailablePriceForEvent_whenBuyTicket_thenOrderMustBeCreated() throws Exception{
        TicketsOrderDto ticketsOrderDto = new TicketsOrderDto();
        ticketsOrderDto.setDanceFloor(3L);
        TicketTableDto ticketTableDto = new TicketTableDto();
        TicketPlaceDto ticketPlaceDto = new TicketPlaceDto();
        ticketPlaceDto.setPlaceNumber(10);
        ticketPlaceDto.setStatus(TicketPlaceStatus.BOOKING);
        ticketTableDto.setTableNumber(15);
        ticketTableDto.setPlaces(Collections.singletonList(ticketPlaceDto));
        ticketsOrderDto.setTables(Collections.singletonList(ticketTableDto));
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setCostDance(BigDecimal.valueOf(150));
        eventDTO.setCostTablePlace(BigDecimal.valueOf(100));
        ticketsOrderDto.setEvent(eventDTO);
        ticketsOrderDto.setPayStatus(GatewayStatus.incomplete.name());

        String ticketsOrderJson = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .findAndRegisterModules()
                .writeValueAsString(ticketsOrderDto);

        mvc.perform(
                post("/api/tickets/bookandpay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketsOrderJson)
        );
    }


}
