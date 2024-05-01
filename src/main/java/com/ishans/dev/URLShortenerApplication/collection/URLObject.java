package com.ishans.dev.URLShortenerApplication.collection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ishans.dev.URLShortenerApplication.util.TimestampUtil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="URLObject")
public class URLObject {
	private String longURL;
	@Id
	private String uniqueId;
	@Builder.Default
	private String createdDate = TimestampUtil.getCurrentTimestampAsString();
	@Builder.Default
	private Integer clicks = 0;
	@Builder.Default
	private List<URLHistory> history = List.of();
	
	
}
