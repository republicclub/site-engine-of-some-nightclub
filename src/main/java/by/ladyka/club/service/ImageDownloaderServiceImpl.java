package by.ladyka.club.service;

import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.repository.EventRepository;
import by.ladyka.club.service.files.StorageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class ImageDownloaderServiceImpl implements ImageDownloaderService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private StorageService storageService;

    private static final Logger logger = LogManager.getLogger(ImageDownloaderServiceImpl.class);

    @Override
    public void downloadImagesFromEvents() {
        List<EventEntity> eventsWithUrl = eventRepository.findAllWhereCoverIsUrl();
        try (CloseableHttpClient httpclient =
                     HttpClientBuilder.create().build()
        ) {
            for (EventEntity event : eventsWithUrl) {
                URL coverUrl = new URL(event.getCoverUri());
                HttpGet httpGet = new HttpGet(event.getCoverUri());
                CloseableHttpResponse response = httpclient.execute(httpGet);
                String localFilePath = storageService.store(FilenameUtils.getName(coverUrl.getPath()), response.getEntity().getContent());
                event.setCoverUri(localFilePath);
                response.close();
            }
            eventRepository.saveAll(eventsWithUrl);
        } catch (MalformedURLException e) {
            logger.error("wrong url type in coverUrl field from event", e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}


