package com.ishans.dev.URLShortenerApplication.collection;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection="URLObject")
public class URLObject {
	private String longURL;
	@Id
	private String uniqueId;
	private String createdDate;
	private Integer clicks;
	private List<URLHistory> history;
}
