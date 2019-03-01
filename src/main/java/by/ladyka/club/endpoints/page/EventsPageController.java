package by.ladyka.club.endpoints.page;

import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.service.ConverterEventService;
import by.ladyka.club.service.EventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/event")
public class EventsPageController {

	@Autowired
	private EventsService eventService;

	@Autowired
	private ConverterEventService converterEventService;

	@Autowired
	private CustomSettings customSettings;

	@GetMapping("/{eventId}")
	public ModelAndView get(@PathVariable long eventId) {

		EventEntity event = eventService.getEventById(eventId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		ModelAndView modelAndView = new ModelAndView("/page/index");
		modelAndView.addObject("data", converterEventService.toEventDto(event));
		modelAndView.addObject("url", customSettings.getSiteDomain() + "/event/" + eventId);
		return modelAndView;
	}
}
