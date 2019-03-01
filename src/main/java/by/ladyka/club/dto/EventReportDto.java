package by.ladyka.club.dto;

import by.ladyka.club.entity.EventReportImageEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class EventReportDto {
	private Long id;
	private LocalDateTime startEvent;
	private String name;
	private String coverUri;
	private String description;
	private Set<String> images;
}
