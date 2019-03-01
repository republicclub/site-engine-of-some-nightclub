package by.ladyka.club.endpoints.api;

import by.ladyka.club.dto.menu.MenuCategoryDto;
import by.ladyka.club.dto.menu.MenuItemPriceDto;
import by.ladyka.club.dto.menu.TicketOrderDto;
import by.ladyka.club.service.MenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "api/menu/")
public class MenuController {

	private static final Logger logger = LogManager.getLogger(MenuController.class);
	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "summary", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity summary(Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(menuService.mainPage(), HttpStatus.OK);
	}

	@RequestMapping(value = "food", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity food(Principal principal, HttpServletRequest httpServletRequest) {
		return new ResponseEntity<>(menuService.getFood(), HttpStatus.OK);
	}

	@RequestMapping(value = "order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity order(Principal principal, HttpServletRequest httpServletRequest, @RequestBody TicketOrderDto dto) {
		return new ResponseEntity<>(menuService.order(dto), HttpStatus.OK);
	}

	@RequestMapping(value = "order", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity getOrder(Principal principal, HttpServletRequest httpServletRequest, Long orderId) {
		return new ResponseEntity<>(menuService.getOrder(orderId), HttpStatus.OK);
	}

	@RequestMapping(value = "availibletables", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity tabels(Principal principal, HttpServletRequest httpServletRequest, Long eventId) {
		return new ResponseEntity<>(menuService.getAvailableTables(eventId), HttpStatus.OK);
	}



	@RequestMapping(value = "admin/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity order(Principal principal, HttpServletRequest httpServletRequest, Long eventId) {
		return new ResponseEntity<>(menuService.orders(eventId), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "admin/category")
	public @ResponseBody
	ResponseEntity get(Principal principal, HttpServletRequest httpServletRequest, @RequestBody MenuCategoryDto dto) {
		Map<String, Object> r = new LinkedHashMap<>();

		try {
			r.put("dto", menuService.saveCategory(dto));;
			r.put("success", true);
		} catch (Exception ex) {
			r.put("success", false);
			r.put("dto", dto);
			logger.error("Error save" , dto.toString());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "admin/item")
	public @ResponseBody
	ResponseEntity adminItem(Principal principal, HttpServletRequest httpServletRequest, @RequestBody MenuItemPriceDto dto) {
		Map<String, Object> r = new LinkedHashMap<>();

		try {
			r.put("dto", menuService.saveMenuItemPrice(dto));;
			r.put("success", true);
		} catch (Exception ex) {
			r.put("success", false);
			r.put("dto", dto);
			logger.error("Error save" , dto.toString());
			ex.printStackTrace();
		}
		return new ResponseEntity<>(r, HttpStatus.OK);
	}

}
