package by.ladyka.club.service.disk;

import by.ladyka.club.service.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Service
public class SpaceDiskServiceImpl implements SpaceDiskService {
	private static final double CRITICAL_RATIO = 0.3;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private EmailService emailService;

	@Override
	@Scheduled(cron = "0 0 * * * ?")
	public void check() {
		Map<String, Double> diskRatios = new HashMap<>();
		for (Path rootName : FileSystems.getDefault().getRootDirectories()) {
			try {
				FileStore store = Files.getFileStore(rootName);
				diskRatios.put(rootName.toString(), (double) store.getUsableSpace() / store.getTotalSpace());
			} catch (IOException e) {
				logger.error("error querying space in " + rootName, e);
			}
		}
		List<String> collect = diskRatios.entrySet().stream()
				.filter((f) -> f.getValue() < CRITICAL_RATIO)
				.map((f) -> "\'" + f.getKey() + "\' коэффициент=" + f.getValue())
				.collect(toList());
		if (!collect.isEmpty()) {
			String message = "Коэффициент свободного пространства ниже критического порога на следующих дисках: "
					+ collect.stream().reduce("\n", (acc, x) -> acc + x + "\n");
			emailService.sendAlertToAdmin(message);
		}

	}
}
