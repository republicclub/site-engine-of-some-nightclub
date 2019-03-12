package by.ladyka.club.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Configuration
public class CustomSettings {
    @Value("${club.files.directory}")
    private String filesDirectory;

    @Value("${club.site.domain}")
    private String siteDomain;

    @Value("${club.site.email.admin}")
    private String emailAdmin;

    @Value("${bepaid.payment.store.id}")
    private Long bePaidPaymentStoreId;

    @Value("${bepaid.payment.store.key}")
    private String bePaidPaymentStoreKey;

    @Value("${bepaid.payment.test}")
    private Boolean bePaidPaymentTest;

}