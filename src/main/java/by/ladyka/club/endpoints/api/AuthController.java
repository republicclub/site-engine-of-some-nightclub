package by.ladyka.club.endpoints.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "api/login")
@ApiIgnore
public class AuthController {

	@RequestMapping("fail")
	public @ResponseBody
	ResponseEntity fail(Principal principal, HttpServletRequest httpServletRequest) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("success", false);
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@RequestMapping("success")
	public @ResponseBody
	ResponseEntity success(Principal principal, HttpServletRequest httpServletRequest) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("success", true);
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

}
