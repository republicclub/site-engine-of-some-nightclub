package by.ladyka.club.service.email;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RecoverPasswordService {

	private Map<String, RecoverPasswordData> passwordDataMap = new HashMap<>();

	public String createToken(String email) {
		final RecoverPasswordData recoverPasswordData = new RecoverPasswordData(email);
		String token = recoverPasswordData.token.toString();
		passwordDataMap.put(token, recoverPasswordData);
		return token;
	}

	public String getEmail(String token) {
		RecoverPasswordData recoverPasswordData = passwordDataMap.get(token);
		if (recoverPasswordData == null) {
			throw new RuntimeException("Токен не валидный");
		}
		return recoverPasswordData.getEmail();
	}

	@Data
	private class RecoverPasswordData {
		private final UUID token = UUID.randomUUID();
		private final LocalDateTime time = LocalDateTime.now();
		private final String email;
	}
}
