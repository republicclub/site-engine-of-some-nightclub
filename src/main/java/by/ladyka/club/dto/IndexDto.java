package by.ladyka.club.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexDto {
	private String url;
	private String name;
	private String description;
	private String coverUri;

	public IndexDto(){}
	public IndexDto(String url, String name, String description, String coverUri) {
		this.url = url;
		this.name = name;
		this.description = description;
		this.coverUri = coverUri;
	}
}
