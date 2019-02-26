package by.ladyka.club.service;

import by.ladyka.club.entity.SettingsEntity;
import by.ladyka.club.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SettingsService {

	@Autowired
	SettingsRepository settingsRepository;

	public List<SettingsEntity> getSettings() {
		return settingsRepository.findAll();
	}

	public SettingsEntity update(String key, String value) {
		final SettingsEntity settingsEntity = settingsRepository.findByKey(key).orElse(new SettingsEntity(key, ""));
		settingsEntity.setValue(value);
		return settingsRepository.save(settingsEntity);
	}

	public List<SettingsEntity> update(Map<String, String> data) {
		data.forEach(this::update);
		return getSettings();
	}
}
