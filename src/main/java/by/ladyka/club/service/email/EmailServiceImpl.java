package by.ladyka.club.service.email;


import by.ladyka.club.config.CustomSettings;
import by.ladyka.club.entity.FeedBackEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuItemPricesHasOrders;
import by.ladyka.club.entity.menu.MenuOrder;
import by.ladyka.club.entity.order.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

	public static final String ORDER_EMAIL = "orders@republic-club.by";
	private static final String CLUB_NAME = "REPUBLIC CLUB";

	@Qualifier("republic")
	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private CustomSettings settings;
	@Autowired
	private RecoverPasswordService recoverPasswordService;

	@Value("${spring.profiles.active}")
	private String profile;

	@Override
	public void sendOrderToOwner(MenuOrder order) {
		String subject = CLUB_NAME + " Заказ № " + order.getId();
		sendMessage(subject, order.getEmail(), ORDER_EMAIL, buildOrderText(order));

	}

	@Override
	public void sendFeedBack(FeedBackEntity feedBack) {
		String subject = CLUB_NAME + " Отзыв № " + feedBack.getId();
		try {
			MimeMessage message = emailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true, "utf-8");
			if ("prod".equals(profile)) {
				helper.setBcc("kra160462@gmail.com");
				helper.setTo("feedback@republic-club.by");
			} else {
				helper.setTo("qa@republic-club.by");
			}
			helper.setCc(feedBack.getEmail());
			helper.setText(buildFeedbackText(feedBack), true);
			new Thread(() -> emailSender.send(message)).start();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void sendSingInLetter(UserEntity entity) {
		String subject = String.format(CLUB_NAME + ". Подтверждение регистрации %s.", entity.getUsername());
		try {
			MimeMessage message = emailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setTo(entity.getEmail());
			helper.setText(buildSingInText(entity), true);
			new Thread(() -> emailSender.send(message)).start();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void sendOrderToOwner(OrderEntity order) {
		String subject = CLUB_NAME + " Заказ № " + order.getId();
		String to = order.getEmail();
		String cc = ORDER_EMAIL;
		String text = buildOrderText(order);
		sendMessage(subject, to, cc, text);
	}

	@Override
	public void sendAlertToAdmin(String message) {
		sendMessage(CLUB_NAME + " Проблемы с дисковым пространством", settings.getEmailAdmin(), null, message);
	}

	@Override
	public void sendLinkChangePassword(String email, String name) {
		sendMessage(CLUB_NAME + " Изменение пароля от учетной записи.", email, buildChangePasswordAccountBody(email, name));
	}

	private void sendMessage(String subject, String to, String text) {
		sendMessage(subject, to, null, text);
	}

	private void sendMessage(String subject, String to, String cc, String text) {
		try {
			MimeMessage message = emailSender.createMimeMessage();
			message.setSubject(subject);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setTo(to);
			if (cc != null) {
				helper.setCc(cc);
			}
			helper.setText(text, true);
			new Thread(() -> emailSender.send(message)).start();
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	private String buildOrderText(OrderEntity order) {
		final Context ctx = new Context(new Locale.Builder().build());
		ctx.setVariable("order", order);
		final List<MenuItemPricesHasOrders> itemPricesHasOrders = order.getItemPricesHasOrders();
		ctx.setVariable("food", itemPricesHasOrders);
		ctx.setVariable("domain", settings.getSiteDomain());
		return templateEngine.process("email/ticket_order_customer.html", ctx);
	}

	private String buildSingInText(UserEntity entity) {
		final Locale.Builder builder = new Locale.Builder();
		final Context ctx = new Context(builder.build());
		ctx.setVariable("code", entity.getFatherName());
		ctx.setVariable("siteUrl", settings.getSiteDomain());
		ctx.setVariable("username", entity.getUsername());

		return templateEngine.process("email/singin.html", ctx);
	}

	private String buildFeedbackText(FeedBackEntity feedBack) {
		final Locale.Builder builder = new Locale.Builder();
		final Context ctx = new Context(builder.build());
		ctx.setVariable("feedback", feedBack);
		return templateEngine.process("email/feedback.html", ctx);
	}

	private String buildOrderText(MenuOrder order) {
		final Locale.Builder builder = new Locale.Builder();
		final Context ctx = new Context(builder.build());
		ctx.setVariable("order", order);
		final List<MenuItemPricesHasOrders> itemPricesHasOrders = order.getItemPricesHasOrders();
		ctx.setVariable("food", itemPricesHasOrders);

		return templateEngine.process("email/orderCustomer.html", ctx);
	}

	private String buildChangePasswordAccountBody(String email, String name) {
		final Locale.Builder builder = new Locale.Builder();
		final Context ctx = new Context(builder.build());
		ctx.setVariable("token", recoverPasswordService.createToken(email));
		ctx.setVariable("email", email);
		ctx.setVariable("name", name);
		ctx.setVariable("domain", settings.getSiteDomain());
		return templateEngine.process("email/recover_password_url.html", ctx);
	}

}
