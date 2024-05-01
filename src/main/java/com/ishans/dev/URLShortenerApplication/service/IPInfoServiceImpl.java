package com.ishans.dev.URLShortenerApplication.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.ipinfo.api.IPinfo;
import io.ipinfo.api.model.IPResponse;
import io.ipinfo.api.errors.RateLimitedException;

@Service
public class IPInfoServiceImpl implements IPInfoService {
	 private final IPinfo ipInfo;

	 public IPInfoServiceImpl(@Value("${ipinfo.token}") String ipInfoToken) {
	        ipInfo = new IPinfo.Builder().setToken(ipInfoToken).build();
	 }

	 public IPResponse getIPDetails(String ip) throws RateLimitedException {
	        return ipInfo.lookupIP(ip);
	 }
}
