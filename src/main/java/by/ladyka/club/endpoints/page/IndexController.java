package by.ladyka.club.endpoints.page;

import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.dto.IndexDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class IndexController {

	@Autowired
	CustomSettings customSettings;

	@GetMapping
	public ModelAndView get(Principal principal, HttpServletRequest httpServletRequest) {
		ModelAndView modelAndView = new ModelAndView("/page/index");
		IndexDto dto = new IndexDto(customSettings.getSiteDomain(), "Re:Public", "Re:PUBLIC club", "/assets/img/logo.svg");
		modelAndView.addObject("data", dto);
		modelAndView.addObject("url", dto.getUrl());
		return modelAndView;
	}

}
