package by.ladyka.bepaid.api;

import by.ladyka.bepaid.dto.GatewayStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

/**
 * https://docs.bepaid.by/ru/checkout/query
 */
public class TransactionState extends BePaidApiRequests {

	private static final String url = "https://checkout.bepaid.by/ctp/api/checkouts/{payment_token}";

	public GatewayStatus getStatus(String paymentToken, String requestId) throws NoSuchAlgorithmException, IOException, URISyntaxException {
		String response =  executeGet(url, requestId);
		throw new RuntimeException("NOT IMPLEMENTED");
//		return GatewayStatus.successful;
	}

}