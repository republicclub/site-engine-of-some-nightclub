package by.ladyka.club.endpoints.api;


import by.ladyka.club.dto.AppUser;
import by.ladyka.club.dto.MainPageDTO;
import by.ladyka.club.service.AfishaService;
import by.ladyka.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping(value = "api/page/")
public class MainPageController {

	@Autowired
	private AfishaService afishaService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "main", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity mainPAge(Principal principal, HttpServletRequest httpServletRequest) {
		AppUser user = null;
		if (principal != null) {
			user = new AppUser(userService.getUser(principal.getName()));
		}

		MainPageDTO mainPageDTO = afishaService.mainPage(user);
		return new ResponseEntity<>(mainPageDTO, HttpStatus.OK);
	}
}
