package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.NewsDto;
import by.ladyka.club.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping(value = "api/news/")
@RequiredArgsConstructor
public class NewsController {
	private final NewsService newsService;

	@RequestMapping(value = "summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity summary(Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(newsService.summary(), HttpStatus.OK);
	}

	@RequestMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity byIdByPath(@PathVariable(value = "id") Long id, Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(newsService.byId(id), HttpStatus.OK);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity byId(@RequestParam(value = "id") Long id, Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(newsService.byId(id), HttpStatus.OK);
	}

	@PostMapping
	public @ResponseBody ResponseEntity save(@RequestBody NewsDto newsDto, Principal principal) {
		return new ResponseEntity<>(newsService.save(newsDto, principal.getName()), HttpStatus.OK);
	}
}
