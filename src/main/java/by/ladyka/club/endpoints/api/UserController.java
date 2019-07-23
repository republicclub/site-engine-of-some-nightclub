package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.ResponseEntityDto;
import by.ladyka.club.dto.UserDto;
import by.ladyka.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity get(Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(getUserDto(principal), HttpStatus.OK);
	}

	private UserDto getUserDto(Principal principal) {
		return (principal != null) ? userService.getUser(principal.getName()) : new UserDto();
	}

	@PostMapping(value = "/singin")
	public @ResponseBody
	ResponseEntity singin(Principal principal, HttpServletRequest httpServletRequest, @RequestBody UserDto user) {
		Map<String, Object> map = new TreeMap<>();
		if (principal != null) {
			map.put("success", false);
			map.put("message", "Вы уже авторизованы, повторная регистрация запрещена!");
		} else {
			try {
				map.put("success", true);
				map.put("data" , userService.singIn(user));
			} catch (Exception ex) {
				map.put("success", false);
				map.put("message", ex.getLocalizedMessage());
			}
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(value = "/confirm/{code}")
	public @ResponseBody String confirm(@PathVariable String code) {
		try {
			userService.confirm(code);
			return "Учетная запись подтверждена";
		} catch (Exception ex) {
			return "Код не верен, обратитесь в службу поддержки";
		}
	}

	@PostMapping(value = "password/token/request")
	public ResponseEntity<Boolean> sendNewPasswordRequest(String usernameOrEmail) {
		userService.sendNewPasswordRequest(usernameOrEmail);
		return ResponseEntity.ok(true);
	}

	@PostMapping(value = "password/token/check")
	public @ResponseBody ResponseEntityDto<String> checkPasswordByToken(String token) {
		try {
			return new ResponseEntityDto<>(
					true,
					userService.getUserNameByRecoverToken(token),
					null);
		} catch (Exception ex) {
			return new ResponseEntityDto<>(
					false,
					null,
					ex.getLocalizedMessage());
		}
	}

	@PostMapping(value = "password/token/update")
	public @ResponseBody ResponseEntityDto<String>  updatePasswordToken(String token, String password) {
		userService.updatePasswordByToken(token, password);
		return new ResponseEntityDto<>(true, "Пароль изменен!","");
	}
}
