package by.ladyka.club.endpoints;

import by.ladyka.bepaid.BePaidApi;
import by.ladyka.bepaid.dto.*;
import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.dto.menu.MenuOrderDto;
import by.ladyka.club.service.MenuService;
import by.ladyka.club.service.order.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;

import static java.lang.Boolean.TRUE;

@RestController
@RequestMapping(PaymentController.API_ORDER_BEPAID)
@AllArgsConstructor
public class PaymentController {

	static final String API_ORDER_BEPAID = "/api/order/bepaid";
	private final CustomSettings customSettings;
	private final BePaidApi bePaidApi;
	private final MenuService menuService;
	private final OrderService orderService;

	@GetMapping(path = "/pay/{orderId}")
	public void payRedirect(Principal principal, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Long orderId) throws IOException {
		PaymentTokenDto dto = new PaymentTokenDto();
		Checkout checkout = new Checkout();
		checkout.setTest(customSettings.getBePaidPaymentTest());
		checkout.setTransactionType(TransactionType.payment);
		checkout.setAttempts(5L);
		Settings settings = new Settings();
		final MenuOrderDto menuOrder = menuService.getOrder(orderId);
		final String redirectUrl = String.format("%s" + API_ORDER_BEPAID +"/callback/%s", customSettings.getSiteDomain(), menuOrder.getUuid());
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
		order.setAmount((long) (menuService.getAmount(menuOrder).doubleValue() * 100));
		order.setDescription("Оплата заказа №" + orderId + " | RE:Public Club");
		checkout.setOrder(order);

		Customer customer = new Customer();
		customer.setEmail(menuOrder.getEmail());
		customer.setLastName(menuOrder.getName());
		customer.setPhone(menuOrder.getPhone());
		checkout.setCustomer(customer);
		dto.setCheckout(checkout);
		try {
			dto = bePaidApi.getOrderToken(dto, "MENU_ORDER_" + menuOrder.getUuid());
			httpServletResponse.sendRedirect(dto.getCheckout().getRedirectUrl());
			final String token = dto.getCheckout().getToken();
			menuService.setToken(token, orderId);
		} catch (URISyntaxException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	@GetMapping(path = "/callback/{uuid}/success")
	public void orderCallBackSuccess(@PathVariable String uuid, String status, String uid, String token, HttpServletResponse response) throws IOException {
		orderService.success(uuid, status, uid, token);
		response.sendRedirect("/order/" + uuid);
	}

	@GetMapping(path = "/callback/{uuid}/ывфы")
	public void orderCallBack222(@PathVariable String uuid, HttpServletResponse response) throws IOException {
		response.sendRedirect("/order/" + uuid);
	}

	@GetMapping(path = "/callback/{uuid}/{status}")
	public void orderCallBack(@PathVariable String uuid, HttpServletResponse response) throws IOException {
		response.sendRedirect("/order/" + uuid);
	}

	@GetMapping(path = "/pay/{orderId}/status")
	public GatewayStatus checkStatus(@PathVariable Long orderId) {
		return menuService.getStatus(orderId);
	}
}
