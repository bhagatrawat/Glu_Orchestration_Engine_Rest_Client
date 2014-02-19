package com.github.bhagatsingh.glu.rs.utils;

import org.apache.cxf.jaxrs.client.WebClient;
/**
 * 
 * @author Bhagat Singh
 *
 */
public class GluRestUtils{
	private static final String AUTH_TYPE = "Basic ";
	
	/**
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public static WebClient getWebClient(String url, String user, String password) {
		if(url==null || url.isEmpty() || user == null || user.isEmpty() || password==null || password.isEmpty())
			throw new IllegalArgumentException("URL or User or Password can not be null or blank.");
		return WebClient.create(url).header("Authorization", getAuthorizationHeader(user, password));
	}
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	private static String getAuthorizationHeader(String user, String password) {
		return AUTH_TYPE + org.apache.cxf.common.util.Base64Utility.encode(getCredentials(user, password).getBytes());
	}
	
	/**
	 * 
	 * @param user
	 * @param password
	 * @return
	 */
	private static String getCredentials(String user, String password){
		return user+":"+password;
	}
}
