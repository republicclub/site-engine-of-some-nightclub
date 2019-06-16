package by.ladyka.club.repository;

import by.ladyka.club.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
	UserEntity findByUsernameOrEmail(String username, String email);
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByFatherName(String fatherName);

}
