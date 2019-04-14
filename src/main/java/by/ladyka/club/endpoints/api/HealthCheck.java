package by.ladyka.club.endpoints.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@ApiIgnore
public class HealthCheck {

	private final ApplicationContext applicationContext;

	@Autowired
	public HealthCheck(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@RequestMapping("/api")
	@ResponseBody
	public ResponseEntity<Map> healthCheck() {
		Map<String, Object> map = new HashMap<>();
		map.put("start", new Date(applicationContext.getStartupDate()).toString());
		map.put("current", new Date().toString());
		return ResponseEntity.ok(map);
	}

	@GetMapping(value = "/")
	public ModelAndView redirect(ModelMap model) {
		model.addAttribute("attribute", "redirectWithRedirectPrefix");
		return new ModelAndView("redirect:/swagger-ui.html", model);
	}

}


