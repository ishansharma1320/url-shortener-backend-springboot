package com.ishans.dev.URLShortenerApplication.collection;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import com.ishans.dev.URLShortenerApplication.util.TimestampUtil;

@Data
@Builder
public class URLHistory {
	private String ipAddress;
	private String city;
	private String region;
	private String country;
	private String timezone;
	@Builder.Default
	private String accessDate = TimestampUtil.getCurrentTimestampAsString();
}
