package by.ladyka.club.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.dto.EventDTO;
import by.ladyka.club.dto.shared.BaseListResultDto;
import by.ladyka.club.service.ClubEventTicketPriceService;
import by.ladyka.club.service.EventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/events/ticket/price", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClubEventTicketPriceAdminController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClubEventTicketPriceService clubEventTicketPriceService;

    @RequestMapping(method = RequestMethod.POST)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
    public @ResponseBody
    Map<String, Object> save(Principal principal, HttpServletRequest httpServletRequest, @RequestBody ClubEventTicketPriceDTO price) {
        Map<String, Object> result = new HashMap<>();
        result.put("input", price);
        try {
            result.put("success", true);
            result.put("data", clubEventTicketPriceService.save(price, principal.getName()));
        } catch (Exception ex) {
            result.put("message", ex.getLocalizedMessage());
            result.put("success", false);
            logger.error("Error", ex);
        }
        return result;
    }

    @RequestMapping(method = RequestMethod.GET)
    @Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
    public @ResponseBody
    ResponseEntity<BaseListResultDto<ClubEventTicketPriceDTO>> get(Principal principal, HttpServletRequest httpServletRequest, @RequestParam Long eventId) {
        List<ClubEventTicketPriceDTO> prices = clubEventTicketPriceService.getAllByEventId(eventId, principal.getName());
        return new ResponseEntity<>(new BaseListResultDto<>(prices), HttpStatus.OK);
    }

}