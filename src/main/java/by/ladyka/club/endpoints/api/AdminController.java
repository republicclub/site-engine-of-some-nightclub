package by.ladyka.club.endpoints.api;

import by.ladyka.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping(value = "/api/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity get(Principal principal, HttpServletRequest httpServletRequest) {
		if (principal != null) {
			userService.getUser(principal.getName());
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
