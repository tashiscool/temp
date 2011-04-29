/**
 * 
 */
package com.pearson.ed.lp.ws;

import static com.pearson.ed.lp.LicensedProductTestHelper.unmarshal;

import java.io.IOException;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.test.server.ResponseMatcher;

/**
 * @author ULLOYNI
 *
 */
public class UnmarshallingResponseCollector implements ResponseMatcher {
	
	private Object response;

	/**
	 * Default constructor.
	 */
	public UnmarshallingResponseCollector() {
	}

	/**
	 * @see org.springframework.ws.test.server.ResponseMatcher#match(org.springframework.ws.WebServiceMessage, org.springframework.ws.WebServiceMessage)
	 */
	@Override
	public void match(WebServiceMessage request, WebServiceMessage response) throws IOException, AssertionError {
		this.response = unmarshal(response.getPayloadSource());
	}
	
	public Object getResponse() {
		return response;
	}

}
