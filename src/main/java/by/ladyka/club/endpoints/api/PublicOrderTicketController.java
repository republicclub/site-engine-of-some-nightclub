package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.menu.TicketOrderDto;
import by.ladyka.club.service.MenuService;
import by.ladyka.club.service.order.OrderTicketsService;
import by.ladyka.club.service.order.report.ReportTicketService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/api/public/orders")
@AllArgsConstructor
public class PublicOrderTicketController {

	private MenuService menuService;
	private OrderTicketsService orderTicketsService;
	private ReportTicketService reportTicketService;

	@GetMapping(value = "/{uuid}")
	public TicketOrderDto order(@PathVariable String uuid) {
		try {
			return orderTicketsService.getOrder(uuid);
		} catch (RuntimeException ex) {
			return menuService.getOrder(uuid);
		}
	}

	@GetMapping(value = "/{uuid}/pdf")
	public void orderPdf(@PathVariable String uuid, HttpServletResponse httpServletResponse) throws IOException, JRException {
		reportTicketService.ticketPrivate(uuid, httpServletResponse.getOutputStream());
	}

}
