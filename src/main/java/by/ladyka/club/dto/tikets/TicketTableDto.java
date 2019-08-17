package by.ladyka.club.dto.tikets;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketTableDto {
	private int tableNumber;
	private List<TicketPlaceDto> places = new ArrayList<>();
}
