package by.ladyka.club.dto;

import by.ladyka.club.entity.EventEntity;
import by.ladyka.club.entity.EventTicketPriceType;
import by.ladyka.club.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class ClubEventTicketPriceDTO {
    private Long id;
    private BigDecimal cost;
    private Integer quantity;
    private EventTicketPriceType type;
    private Long eventId;
    private LocalDateTime startActiveTime;
    private LocalDateTime endActiveTime;
}