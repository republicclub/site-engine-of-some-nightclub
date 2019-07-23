package by.ladyka.club.service.email;

import by.ladyka.club.entity.FeedBackEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuOrder;
import by.ladyka.club.entity.order.TicketOrderEntity;

public interface EmailService {
	void sendOrderToOwner(MenuOrder order);

	void sendFeedBack(FeedBackEntity feedBack);

	void sendSingInLetter(UserEntity entity);

	void sendOrderToOwner(TicketOrderEntity ticketOrderEntity);

	void sendAlertToAdmin(String message);

	void sendLinkChangePassword(String email, String name);
}
