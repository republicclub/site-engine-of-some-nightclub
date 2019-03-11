package by.ladyka.club.service.email;

import by.ladyka.club.dto.FeedBackDto;
import by.ladyka.club.entity.FeedBackEntity;
import by.ladyka.club.entity.UserEntity;
import by.ladyka.club.entity.menu.MenuOrder;
import by.ladyka.club.entity.order.OrderEntity;

import java.util.Map;

public interface EmailService {
	void sendOrderToOwner(MenuOrder order);

	void sendFeedBack(FeedBackEntity feedBack);

	void sendSingInLetter(UserEntity entity);

	void sendOrderToOwner(OrderEntity orderEntity);

	void sendAlertToAdmin(String message);
}
