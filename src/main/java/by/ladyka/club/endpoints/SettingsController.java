package by.ladyka.club.endpoints;

import by.ladyka.club.entity.SettingsEntity;
import by.ladyka.club.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

	@Autowired
	SettingsService settingsService;

	@GetMapping
	public List<SettingsEntity> settingsSiteDto() {
		return settingsService.getSettings();
	}

	@PostMapping
	public List<SettingsEntity> update(@RequestBody Map<String, String> data) {
		return settingsService.update(data);
	}
}
