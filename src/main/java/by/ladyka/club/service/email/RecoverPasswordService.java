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
		final RecoverPasswordData recoverPasswordData = new RecoverPasswordData();
		passwordDataMap.put(email, recoverPasswordData);
		return recoverPasswordData.token.toString();
	}

	@Data
	private class RecoverPasswordData {
		private final UUID token = UUID.randomUUID();
		private final LocalDateTime time = LocalDateTime.now();
	}
}
