package by.ladyka.club.endpoints.api;

import by.ladyka.club.config.constant.ClubRole;
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
@RequestMapping(value = "/api/admin/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventsAdminController {

	@Autowired
	EventsService eventsService;

	@RequestMapping(method = RequestMethod.GET)
	@Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
	public @ResponseBody
	ResponseEntity<BaseListResultDto<EventDTO>> get(Principal principal, HttpServletRequest httpServletRequest, @RequestParam(required = false) String sort, String order, Integer page, Integer size, @RequestParam(required = false) String filter) {
		final List<EventDTO> events = eventsService.getEvents(sort, order, page, size, filter, principal.getName());
		long total = eventsService.getTotalEvents(filter, principal.getName());
		return new ResponseEntity<BaseListResultDto<EventDTO>>(new BaseListResultDto<>(events, total), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	@Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
	public @ResponseBody
	ResponseEntity save(Principal principal, HttpServletRequest httpServletRequest, @RequestBody EventDTO event) {
		return new ResponseEntity<EventDTO>(eventsService.save(event, principal.getName()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@Secured(value = {ClubRole.ROLE_ADMIN, ClubRole.ROLE_CONCERT})
	public @ResponseBody
	ResponseEntity delete(Principal principal, HttpServletRequest httpServletRequest, Long id) {
		Map<String, Object> r = new LinkedHashMap<>();
		try {
			eventsService.delete(id);
			r.put("success", true);
		} catch (Exception ex) {
			r.put("success", false);
			r.put("data", ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
}
