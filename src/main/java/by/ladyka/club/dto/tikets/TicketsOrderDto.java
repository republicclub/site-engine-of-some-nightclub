package by.ladyka.club.dto.tikets;

import by.ladyka.club.dto.EventDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
public class TicketsOrderDto {

	private EventDTO event;
	private Long danceFloor;
	private int placeSeats;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String description;
//	@AssertTrue
	private Boolean rulesCheck;
//	@AssertTrue
	private Boolean  offerCheck;
	private List<TicketTableDto> tables;


	private Long id;
//	private Map<Long, Integer> food = new LinkedHashMap<>();
//	private Map<Long, BigDecimal> foodPrice = new LinkedHashMap<>();
	private String uuid;
	private String payStatus;

	private LocalDateTime enterTime;
	private String acceptor;
}
