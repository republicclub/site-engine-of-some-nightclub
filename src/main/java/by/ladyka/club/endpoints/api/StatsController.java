package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.UserDataDto;
import by.ladyka.club.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/statistic")
public class StatsController {

	@Autowired
	private StatisticService statisticService;

	@PostMapping
	public Object record(@RequestBody UserDataDto dto, HttpServletRequest request) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("success", true);
		r.put("data", statisticService.save(
				dto,
				request.getLocalAddr(),
				request.getRemoteAddr(),
				request.getProtocol()
		));
		return new ResponseEntity<>(r, HttpStatus.OK);
	}


}
