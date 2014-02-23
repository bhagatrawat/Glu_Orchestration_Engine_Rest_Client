package com.github.bhagatsingh.glu.proxy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Talks to Glu and asks it to do things for it.
 * @author Bhagat Singh
 */
public class GluProxy {

	private static final Logger log = LoggerFactory.getLogger(GluProxy.class);

	private final String GLU_API_BASE = "rest/v1/";
	private final String SYSTEM_MODEL = "/system/model/static";
	private final Pattern ID_PATTERN = Pattern.compile("id=(.*)");

	private final String gluUrl;
	private final String fabric;
	private final HttpClient client;
	private final PostMethod post;

	public GluProxy(String gluUrl, String fabricName, HttpClient client, PostMethod postMethod, String username, String password) {
		Assert.notNull(gluUrl, "gluUrl is null");
		Assert.notNull(fabricName, "fabric is null");
		Assert.notNull(client, "http client is null");
		Assert.notNull(postMethod, "http method is null");
		this.gluUrl = gluUrl;
		this.fabric = fabricName;
		this.client = client;
		this.post = postMethod;
		setupAuthentication(username, password);
	}

	private void setupAuthentication(String username, String password) {
		client.getState().setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
		client.getParams().setAuthenticationPreemptive(true);
		post.setDoAuthentication(true);
	}

	public String getUrl() {
		return gluUrl;
	}

	/**
	 * Loads the jsonModel string into glu
	 */
	public String loadModel(String jsonModel) throws HttpException, IOException {
		post.setURI(new URI(getPostModelUrl(), true));
		post.addRequestHeader("Content-Type", "text/json");
		post.setRequestEntity(new InputStreamRequestEntity(new ByteArrayInputStream(jsonModel.getBytes()), jsonModel.length()));
		log.info("Sending a request to glu to update model: {}", jsonModel);
		int status = client.executeMethod(post);
		log.debug("Request returned with status {}", status);
		String responseText = post.getResponseBodyAsString();
		
		if (status < 200 || status >= 300) {
			String message = "Glu returned an error for the request containing "
					+ jsonModel
					+ "\n"
					+ "Status code: "
					+ status
					+ "\n"
					+ "Response headers: "
					+ Arrays.toString(post.getResponseHeaders())
					+ "\n"
					+ "Response text: " + responseText;
			log.error(message);
			throw new HttpException(message);
		}
		
		String planId = extractPlanId(responseText);
		log.info("Model loaded successfuly to glu. The new plan ID is {}",planId);
		return planId;
	}

	private String extractPlanId(String responseText) {
		if (responseText == null) {
			return null;
		}
		Matcher m = ID_PATTERN.matcher(responseText);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	private String getPostModelUrl() {
		return gluUrl + GLU_API_BASE + fabric + SYSTEM_MODEL;
	}
}
