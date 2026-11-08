package com.project.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class responseDto {
	private HttpStatusCode httpCode;
	private boolean success;
	private String message;
}
