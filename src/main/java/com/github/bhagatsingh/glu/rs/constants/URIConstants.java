package com.github.bhagatsingh.glu.rs.constants;
/**
 * 
 * @author Bhagat Singh
 *
 */
public interface URIConstants {
	public static final String SERVER_URL="http://localhost:8080";
	public static final String FABRIC_URI="/console/rest/v1/<fabric>";
	public static final String AGENTS_URI="/console/rest/v1/<fabric>/agents";
	public static final String AGENT_URI="/agent/<agentName>";
	public static final String AGENT_DETAILS_URI="/console/rest/v1/<fabric>/agent/<agentName>";
	public static final String AGENTS_VERSIONS_URI="/console/rest/v1/<fabric>/agents/versions";
	public static final String CREATE_PLANS_URI = "/console/rest/v1/<fabric>/plans";
	public static final String FABRICS_URI = "/console/rest/v1/-";
	public static final String LOAD_MODEL_URI = "/console/rest/v1/<fabric>/model/static";
	public static final String AGENT_PROCESS_URI = "/process";

}
