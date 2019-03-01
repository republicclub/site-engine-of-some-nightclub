package by.ladyka.club.endpoints.api;

import by.ladyka.club.config.ClubRole;
import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.shared.BaseListResultDto;
import by.ladyka.club.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/events/ticket/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClubEventTicketPriceAdminController {

    @RequestMapping(method = RequestMethod.POST)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
    public @ResponseBody
    ResponseEntity save(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ClubEventTicketPriceDTO price) {
        //TODO: mock controller, rewrite
        price.setId(5L);
        return new ResponseEntity<>(price, HttpStatus.OK);
    }

}