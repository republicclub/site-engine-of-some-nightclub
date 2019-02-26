package by.ladyka.club.repository;

import by.ladyka.club.entity.SettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<SettingsEntity, Long> {
	Optional<SettingsEntity> findByKey(String key);
}
