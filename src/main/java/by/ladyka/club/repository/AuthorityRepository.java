package by.ladyka.club.repository;

import by.ladyka.club.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
}