package by.ladyka.club.service;

import by.ladyka.club.dto.UserDto;
import by.ladyka.club.dto.UserPersonalDto;
import by.ladyka.club.entity.UserEntity;

public interface UserService {
	UserDto getUser(String name);
	UserEntity getUserEntity(String username);
	Object singIn(UserDto user);
	void confirm(String code);
	String getRole(UserEntity userEntity);
	UserPersonalDto getUserFull(String username);
	UserPersonalDto saveUserFull(UserPersonalDto dto, String name);
	void sendNewPasswordRequest(String usernameOrEmail);
}
