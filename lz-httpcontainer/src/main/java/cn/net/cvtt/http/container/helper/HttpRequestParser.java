package cn.net.cvtt.http.container.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpRequestParser {

	public static byte[] getRequestBody(final HttpServletRequest request) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int contentLength = request.getContentLength();
		try {
			if (contentLength > 0) {
				int sum = 0;
				byte[] buffer = new byte[contentLength];
				while (sum < contentLength) {
					int count = request.getInputStream().read(buffer);
					if (count > 0) {
						outputStream.write(buffer, 0, count);
						sum += count;
					} else if (count < 0) {
						throw new IOException(String.format("getRequestBody read return %s. contentLength:%s sum:%s ", count, contentLength, sum));
					}
				}
			}
			outputStream.flush();
			return outputStream.toByteArray();
		} finally {
			outputStream.close();
		}
	}

	public static String getRequestString(final HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s %s %s\n", request.getMethod(), request.getRequestURI(), request.getProtocol()));
		for (Enumeration<String> e = request.getHeaderNames(); e.hasMoreElements();) {
			String headName = e.nextElement();
			String headValue = request.getHeader(headName);
			sb.append(String.format("%s:%s\n", headName, headValue));
		}

		return sb.toString();
	}

	public static String getResponseString(final HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s %s %s\n", "HTTP/1.1", response.getStatus(), "XXXXX"));
		for (String name : response.getHeaderNames()) {
			String value = response.getHeader(name);
			sb.append(String.format("%s:%s\n", name, value));
		}

		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseQuery(String query) throws UnsupportedEncodingException {
		Map<String, Object> parameters = null;
		if (query != null) {
			parameters = new HashMap<String, Object>();
			String pairs[] = query.split("[&]");

			for (String pair : pairs) {
				String param[] = pair.split("[=]");

				String key = null;
				String value = null;
				if (param.length > 0) {
					key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
				}

				if (param.length > 1) {
					value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
				}

				if (parameters.containsKey(key)) {
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) {
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) {
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else {
					parameters.put(key, value);
				}
			}
		}
		return parameters;
	}
}
