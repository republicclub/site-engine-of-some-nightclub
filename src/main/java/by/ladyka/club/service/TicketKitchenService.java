package by.ladyka.club.service;

import by.ladyka.club.dto.tikets.TicketsOrderDto;
import by.ladyka.club.entity.menu.MenuItemPrice;
import by.ladyka.club.entity.menu.MenuItemPricesHasTicketOrder;
import by.ladyka.club.entity.order.TicketOrderEntity;
import by.ladyka.club.repository.MenuItemPricesHasTicketOrderRepository;
import by.ladyka.club.repository.menu.MenuItemPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TicketKitchenService {
	private final MenuItemPriceRepository menuItemPriceRepository;
	private final MenuItemPricesHasTicketOrderRepository menuItemPricesHasTicketOrderRepository;

	public void storeMenuItems(TicketsOrderDto order, final TicketOrderEntity finalOrderEntity) {
		List<Long> pricesId = new ArrayList<>(order.getMenuFoodOrder().getFood().keySet());
		final List<MenuItemPrice> pricesById = menuItemPriceRepository.findAllById(pricesId);
		final List<MenuItemPricesHasTicketOrder> itemPricesHasOrders = pricesById
				.stream()
				.map(price -> getMenuItemPricesHasTicketOrder(order, finalOrderEntity, price))
				.collect(toList());
		finalOrderEntity.setItemPricesHasOrders(itemPricesHasOrders);
		menuItemPricesHasTicketOrderRepository.saveAll(itemPricesHasOrders);
	}

	private MenuItemPricesHasTicketOrder getMenuItemPricesHasTicketOrder(
			TicketsOrderDto order,
			TicketOrderEntity finalOrderEntity,
			MenuItemPrice price) {
		MenuItemPricesHasTicketOrder orderFoodItem = new MenuItemPricesHasTicketOrder();
		orderFoodItem.setCount(order.getMenuFoodOrder().getFood().get(price.getId()));
		orderFoodItem.setItemPrice(price);
		orderFoodItem.setOrder(finalOrderEntity);
		return orderFoodItem;
	}

	public BigDecimal amount(TicketOrderEntity ticketOrderEntity) {
		final List<MenuItemPricesHasTicketOrder> itemPricesHasOrders = ticketOrderEntity.getItemPricesHasOrders();

		return itemPricesHasOrders.stream()
				.map(i -> i.getItemPrice().getValue().multiply(new BigDecimal(i.getCount())))
				.reduce(BigDecimal.ZERO, BigDecimal::add)
				.multiply(new BigDecimal(0.9));
	}
}
