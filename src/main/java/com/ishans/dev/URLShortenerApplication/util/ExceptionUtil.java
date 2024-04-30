package com.ishans.dev.URLShortenerApplication.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

	public static String getStackTraceAsString(Exception exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}
}
