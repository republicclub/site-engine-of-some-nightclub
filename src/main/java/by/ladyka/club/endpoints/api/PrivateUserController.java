package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.UserPersonalDto;
import by.ladyka.club.service.UserService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/private")
@AllArgsConstructor
@Api(description = "User Private Data", tags = "User")
public class PrivateUserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public UserPersonalDto user(Principal principal) {
		return userService.getUserFull(principal.getName());
	}

	@PostMapping("/user")
	public UserPersonalDto user(@RequestBody UserPersonalDto dto, Principal principal) {
		return userService.saveUserFull(dto, principal.getName());
	}
}
