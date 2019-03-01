package by.ladyka.club.endpoints.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.lang.Boolean.TRUE;


@RestController
@ApiIgnore
public class HealthCheck {

	private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
	private final ApplicationContext applicationContext;

	@Autowired
	public HealthCheck(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@RequestMapping("/api")
	@ResponseBody
	public ResponseEntity<Map> healthCheck() {
		Map<String, Object> map = new HashMap<>();
		map.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
		map.put("start", new Date(applicationContext.getStartupDate()).toString());
		map.put("current", new Date().toString());
		map.put("status", TRUE);
		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/")
	public ModelAndView redirect(ModelMap model){
		model.addAttribute("attribute", "redirectWithRedirectPrefix");
		return new ModelAndView("redirect:/swagger-ui.html", model);
	}

}


