package by.ladyka.bepaid;

import by.ladyka.bepaid.api.PaymentTokenService;
import by.ladyka.bepaid.api.TransactionState;
import by.ladyka.bepaid.dto.*;
import by.ladyka.bepaid.logger.LoggerRequests;
import lombok.*;
import org.apache.http.ParseException;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BePaidApi {

	private static BePaidApiConfiguration configuration;
	private static LoggerRequests logger;

	private PaymentTokenService paymentTokenService = new PaymentTokenService();
	private TransactionState transactionState = new TransactionState();

	public static BePaidApi getApi(Long storeId, String key, LoggerRequests l) {
		BePaidApi bePaidApi = new BePaidApi();
		if (configuration == null) {
			configuration = new BePaidApiConfiguration(storeId, key);
			logger = l;
		} else {
			throw new RuntimeException("Configuration is already set!");
		}
		return bePaidApi;
	}

	public static LoggerRequests getLogger() {
		return logger;
	}

	public static BePaidApiConfiguration getConfiguration() {
		return configuration;
	}

	public PaymentTokenDto getOrderToken(PaymentTokenDto dto, String requestId) throws ParseException, IOException, URISyntaxException, NoSuchAlgorithmException {
		return paymentTokenService.getToken(dto, requestId);
	}

	public GatewayStatus getTransactionState(String token, String requestId) throws NoSuchAlgorithmException, IOException, URISyntaxException {
		return transactionState.getStatus(token, requestId);
	}

	@Getter
	@AllArgsConstructor
	public static class BePaidApiConfiguration {
		private Long storeId;
		private String key;
	}

	public PaymentTokenDto getPaymentTokenDto(BigDecimal amount, String email, String surname, String name, String phone, String redirectUrl, boolean test, String descriptionOrder) {
		PaymentTokenDto dto = new PaymentTokenDto();
		Checkout checkout = new Checkout();
		checkout.setTest(test);
		checkout.setTransactionType(TransactionType.payment);
		checkout.setAttempts(5L);
		Settings settings = new Settings();

		settings.setSuccessUrl(redirectUrl + "/success");
		settings.setDeclineUrl(redirectUrl + "/decline");
		settings.setFailUrl(redirectUrl + "/fail");
		settings.setCancelUrl(redirectUrl + "/cancel");
		settings.setNotificationUrl(redirectUrl + "/notification");
		settings.setLanguage(Language.ru.name());
		CustomerFields customerFields = new CustomerFields();
		customerFields.setVisible(Arrays.asList("first_name", "last_name"));
		customerFields.setReadOnly(Collections.singletonList("email"));
		settings.setCustomerFields(customerFields);
		checkout.setSettings(settings);
		OrderDto order = new OrderDto();
		order.setCurrency("BYN");
		order.setAmount(amount.multiply(new BigDecimal(100)).longValue());
		order.setDescription(descriptionOrder);
		checkout.setOrder(order);

		Customer customer = new Customer();
		customer.setEmail(email);
		customer.setLastName(surname);
		customer.setFirstName(name);
		customer.setPhone(phone);
		checkout.setCustomer(customer);
		dto.setCheckout(checkout);
		return dto;
	}
}
