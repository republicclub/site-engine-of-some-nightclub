package by.ladyka.club.service;

import by.ladyka.club.config.constant.ClubRole;
import by.ladyka.club.dto.UserDto;
import by.ladyka.club.dto.UserPersonalDto;
import by.ladyka.club.entity.AuthorityEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.repository.AuthorityRepository;
import by.ladyka.club.repository.UserEntityRepository;
import by.ladyka.club.service.email.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserEntityRepository userEntityRepository;
	@Autowired
	private EmailService emailService;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	public UserDto getUser(String username) {
		final UserEntity user = getUserEntity(username);
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setName(user.getName());
		userDto.setRole(user.getAuthorities().get(0).getAuthority());
		return userDto;
	}

	public UserEntity getUserEntity(String username) {
		return userEntityRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Object singIn(UserDto user) {
		final Optional<UserEntity> byUsername = userEntityRepository.findByUsername(user.getUsername());
		if (byUsername.isPresent()) {
			throw new RuntimeException("Пользователь с таким именем уже существует");
		}
		final Optional<UserEntity> byEmail = userEntityRepository.findByEmail(user.getUsername());
		if (byEmail.isPresent()) {
			throw new RuntimeException("Пользователь с такой почтой уже существует");
		}
		UserEntity entity = new UserEntity();
		entity.setEmail(user.getEmail());
		entity.setUsername(user.getUsername());
		entity.setPassword(user.getPassword());

		entity.setFatherName(UUID.randomUUID().toString());

		entity.setAccountNonExpired(Boolean.TRUE);
		entity.setAccountNonLocked(Boolean.TRUE);
		entity.setCredentialsNonExpired(Boolean.TRUE);
		entity.setEnabled(Boolean.FALSE);
		entity = userEntityRepository.save(entity);
		AuthorityEntity authorityEntity = new AuthorityEntity();
		authorityEntity.setAuthority(ClubRole.ROLE_USER);
		authorityEntity.setUser(entity);
		authorityEntity.setUsername(user.getUsername());
		authorityRepository.save(authorityEntity);
		emailService.sendSingInLetter(entity);
		return null;
	}

	@Override
	public void confirm(String code) {
		final UserEntity userEntity = userEntityRepository.findByFatherName(code).orElseThrow(EntityNotFoundException::new);
		userEntity.setFatherName("");
		userEntity.setEnabled(Boolean.TRUE);
		userEntityRepository.save(userEntity);
	}

	@Override
	public String getRole(UserEntity userEntity) {
		return userEntity.getAuthorities()
				.stream()
				.map(AuthorityEntity::getAuthority)
				.findFirst().orElseThrow(RuntimeException::new);
	}

	@Override
	public UserPersonalDto getUserFull(String username) {
		final UserEntity user = userEntityRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
		UserPersonalDto dto = new UserPersonalDto();
		BeanUtils.copyProperties(user, dto);
		dto.setAuthorities(user.getAuthorities().stream().map(AuthorityEntity::getAuthority).collect(Collectors.toList()));
		return dto;
	}

	@Override
	public UserPersonalDto saveUserFull(UserPersonalDto dto, String username) {
		final UserEntity user = userEntityRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
		user.setBirthday(dto.getBirthday());
		user.setName(dto.getName());
		user.setSurname(dto.getSurname());
		user.setFatherName(dto.getFatherName());
		user.setPhone(dto.getPhone());
		user.setEmail(dto.getEmail());
		userEntityRepository.save(user);
		dto = new UserPersonalDto();
		BeanUtils.copyProperties(user, dto);
		dto.setAuthorities(user.getAuthorities().stream().map(AuthorityEntity::getAuthority).collect(Collectors.toList()));
		return dto;
	}
}
