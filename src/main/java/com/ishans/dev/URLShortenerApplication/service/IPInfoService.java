package com.ishans.dev.URLShortenerApplication.service;

import io.ipinfo.api.errors.RateLimitedException;
import io.ipinfo.api.model.IPResponse;

public interface IPInfoService {

	IPResponse getIPDetails(String ip) throws RateLimitedException;

}