package by.ladyka.club.dto;

import by.ladyka.club.entity.EventTicketPriceType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ClubEventTicketPriceDTO {
	private Long id;
	private BigDecimal cost;
	private Long quantity;
	private EventTicketPriceType typePrice;
	private Long eventId;
	private LocalDateTime startActiveTime;
	private LocalDateTime endActiveTime;
	private String modifiedBy;
}