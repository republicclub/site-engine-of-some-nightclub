package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.EventReportDto;
import by.ladyka.club.dto.EventReportFileDto;
import by.ladyka.club.service.EventReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/event/report")
public class EventReportController {

	@Autowired
	EventReportService eventReportService;

	@GetMapping
	public ResponseEntity<EventReportDto> getEventReport(@RequestParam(name = "id") Long id) {
		return new ResponseEntity<EventReportDto>(eventReportService.getEventReport(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Map<String, Object>> save(@RequestBody EventReportDto dto) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("success", true);
		r.put("data", eventReportService.save(dto));
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@PostMapping(value = "/file")
	public ResponseEntity addFileToReport(@RequestBody EventReportFileDto dto) {
		Map<String, Object> r = new LinkedHashMap<>();
		r.put("success", eventReportService.addFileToReport(dto.getReportId(), "/files/" + dto.getFilePath()));
		return new ResponseEntity<>(r, HttpStatus.OK);
	}
}
