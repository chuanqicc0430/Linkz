package cn.net.cvtt.http.container.core;

import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

/**
 * uriMapper，记录application的path，protocal等
 * 
 * @author 
 *
 */
public class UriHandlerMapper {

	private String uri;
	private HttpMethod httpMethod;
	private HttpVersion httpVersion;

	public UriHandlerMapper(String uri, HttpVersion httpVersion, HttpMethod httpMethod) {
		this.uri = uri;
		this.httpVersion = httpVersion;
		this.httpMethod = httpMethod;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((httpVersion == null) ? 0 : httpVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UriHandlerMapper other = (UriHandlerMapper) obj;
		if (httpMethod == null) {
			if (other.httpMethod != null) {
				return false;
			}
		} else if (!httpMethod.equals(other.httpMethod)) {
			return false;
		}
		if (uri == null) {
			if (other.uri != null) {
				return false;
			}
		} else if (!uri.equals(other.uri)) {
			return false;
		}
		if (httpVersion == null) {
			if (other.httpVersion != null) {
				return false;
			}
		} else if (!httpVersion.equals(other.httpVersion)) {
			return false;
		}
		return true;
	}

}
