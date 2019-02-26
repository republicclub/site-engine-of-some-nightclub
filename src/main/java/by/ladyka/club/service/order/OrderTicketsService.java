package by.ladyka.club.service.order;

import by.ladyka.bepaid.dto.GatewayStatus;
import by.ladyka.club.dto.report.TicketOrderReportDto;
import by.ladyka.club.dto.tikets.EventTicketsReportDto;
import by.ladyka.club.dto.tikets.TicketTableDto;
import by.ladyka.club.dto.tikets.TicketsOrderDto;
import by.ladyka.club.dto.menu.TicketOrderDto;

import java.util.List;

public interface OrderTicketsService {
	List<TicketTableDto> getTables(Long eventId);

	String bookAndPay(TicketsOrderDto dto, String username);

	boolean updateStatus(String uuid, GatewayStatus gatewayStatus, String uid, String token);

	TicketOrderDto getOrder(String uuid);

	EventTicketsReportDto getReport(Long eventId);

	List<TicketsOrderDto> getTickets(String name, Long eventId, String filter);

	Boolean acceptTicket(String username, String uuid);

	TicketOrderReportDto getOrderReport(String uuid);
}
