package cn.net.cvtt.lian.common.http.message;

public enum HttpMethod {
	GET("GET"), POST("POST"), KNOWN("KNOWN");

	private String value;

	private HttpMethod(String value) {
		this.value = value;
	}

	public static HttpMethod valueof(String value) {
		for (HttpMethod method : values())
			if (method.toString().equalsIgnoreCase(value))
				return method;
		return KNOWN;
	}

	public String toString() {
		return this.value;
	}
}
