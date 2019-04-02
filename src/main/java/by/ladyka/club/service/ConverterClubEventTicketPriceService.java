package by.ladyka.club.service;

import by.ladyka.club.dto.ClubEventTicketPriceDTO;
import by.ladyka.club.entity.ClubEventTicketPrice;

public interface ConverterClubEventTicketPriceService {
    ClubEventTicketPriceDTO toDto(ClubEventTicketPrice entity);
    ClubEventTicketPrice toEntity(ClubEventTicketPriceDTO dto, ClubEventTicketPrice targetEntity, String[] ignoreProperties);
}
