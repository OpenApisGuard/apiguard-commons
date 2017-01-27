package org.apiguard.commons.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apiguard.valueobject.ApiVo;
import org.springframework.util.StringUtils;

public class ApiGuardHttpClient {
	private HashMap<String, WebClient> webClients = new HashMap<String, WebClient>();
	
	public ApiGuardHttpClient(List<ApiVo> endpoints) {
		if (endpoints == null || endpoints.isEmpty()) {
			return;
		}
		
		for(ApiVo a: endpoints) {
			String downStreamUri = a.getFwdUri();
			addWebClient(downStreamUri);
		}
	}

	public void addWebClient(String downStreamUri) {
		JAXRSClientFactoryBean bean = new JAXRSClientFactoryBean();
		bean.setAddress(downStreamUri);
		bean.setThreadSafe(true);
		webClients.put(downStreamUri, bean.createWebClient());
	}

	public void deleteWebClient(String downStreamUri) {
		webClients.remove(downStreamUri);
	}

	public Response callService(String webServiceUrl) throws HttpClientException {
		return callService(webServiceUrl, null, null, null);
	}

	public Response callService(String webServiceUrl, String acceptType) throws HttpClientException {
		return callService(webServiceUrl, null, null, acceptType);
	}

	public Response callService(String webServiceUrl, HashMap<String, String> headers, String acceptType) throws HttpClientException {
		return callService(webServiceUrl, headers, null, acceptType);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Response callService(String webServiceUrl, HashMap<String, String> headers,
			HashMap<String, Object> properties, String acceptType, Interceptor... interceptors) throws HttpClientException {

		WebClient wc = webClients.get(webServiceUrl);
		if (wc == null) {
			throw new HttpClientException("Http client is not configured for url: " + webServiceUrl);
		}

		// properties.put("ws-security.signature.username", "client");
		// properties.put("ws-security.signature.properties",
		// "client_nonexistantfile.properties");

		// http signatures can be added through in/out interceptors
		if (interceptors != null) {
			WebClient.getConfig(wc).getOutInterceptors().addAll(new ArrayList(Arrays.asList(interceptors)));
		}
		
		if (StringUtils.isEmpty(acceptType)) {
			wc.accept(acceptType);
		}
		
		Response response = wc.get();
		return response;
	}
}
