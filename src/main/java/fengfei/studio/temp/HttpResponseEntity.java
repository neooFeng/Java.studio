package fengfei.studio.temp;

import org.apache.http.Header;

public class HttpResponseEntity {
	public static final int STATUS_SUCCESS = 200;
	private String body;
	private Header[] headers;
	private Integer statusCode;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Header[] getHeaders() {
		return headers;
	}
	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public boolean isSuccess() {
		return this.statusCode != null && this.statusCode == STATUS_SUCCESS;
	}
}
