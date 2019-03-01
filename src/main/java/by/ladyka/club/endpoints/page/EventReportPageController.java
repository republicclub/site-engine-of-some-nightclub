package by.ladyka.club.endpoints.page;

import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.dto.EventReportDto;
import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.service.ConverterEventService;
import by.ladyka.club.service.EventReportService;
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
@RequestMapping(value = "/event-reports")
public class EventReportPageController {

	@Autowired
	private EventReportService eventReportService;

	@Autowired
	private CustomSettings customSettings;

	@GetMapping("/{reportId}")
	public ModelAndView get(@PathVariable long reportId) {
		final EventReportDto eventReport = eventReportService.getEventReport(reportId);
		ModelAndView modelAndView = new ModelAndView("/page/index");
		modelAndView.addObject("data", eventReport);
		modelAndView.addObject("url", customSettings.getSiteDomain() + "/event-reports/" + reportId);
		return modelAndView;
	}
}
