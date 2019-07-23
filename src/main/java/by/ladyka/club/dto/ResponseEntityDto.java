package by.ladyka.club.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ResponseEntityDto<T> {
	private final boolean success;
	private final T data;
	private final String message;
}

