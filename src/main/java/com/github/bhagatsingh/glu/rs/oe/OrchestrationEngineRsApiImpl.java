package com.github.bhagatsingh.glu.rs.oe;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

import com.github.bhagatsingh.glu.rs.constants.URIConstants;
import com.github.bhagatsingh.glu.rs.utils.GluRestUtils;

/**
 * 
 * @author Bhagat Singh
 *
 */
public class OrchestrationEngineRsApiImpl implements OrchestrationEngineRsApi{

	WebClient client;
	/**
	 * 
	 * @param url
	 * @param user
	 * @param password
	 */
	public OrchestrationEngineRsApiImpl(String url, String user, String password){
		try{
		    client = GluRestUtils.getWebClient(url, user, password);
		}catch(Exception exp){
		    throw new RuntimeException(exp);
		}
	}
	
	@Override
	public String getFabric(String fabric) {
		if(fabric == null || fabric.isEmpty())
			throw new IllegalArgumentException("fabric name is missing.");
		String path = URIConstants.FABRIC_URI;
		path = path.replaceFirst("<fabric>", fabric);
		return get(path);
	}

	@Override
	public Response getAddOrUpdateFabric(String fabric, String action, String message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deleteFabric(String fabric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAgentsCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAgentsDetails(String fabric) {
		if(fabric == null || fabric.isEmpty())
			throw new IllegalArgumentException("fabric name is missing.");
		String path = URIConstants.AGENTS_URI;
		path = path.replaceFirst("<fabric>", fabric);
		return get(path);
	}

	@Override
	public String getAgentDetails(String fabric, String agentName) {
		if(fabric == null || fabric.isEmpty() || agentName == null || agentName.isEmpty() )
			throw new IllegalArgumentException("fabric and/or agent name are/is missing.");
		String path = URIConstants.AGENT_DETAILS_URI;
		path = path.replaceFirst("<fabric>", fabric);
		path = path.replaceFirst("<agentName>", agentName);
		return get(path);
	}

	@Override
	public Response removeAgent(String agentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response assignFabricToAnAgent(String agentName, String fabric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response removeFabricForAnAgent(String agentName, String fabric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAgentsVersions(String fabric) {
		if(fabric == null || fabric.isEmpty())
			throw new IllegalArgumentException("fabric name is missing.");
		String path = URIConstants.AGENTS_VERSIONS_URI;
		path = path.replaceFirst("<fabric>", fabric);
		return get(path);
	}

	@Override
	public Response upgradeAgents(String fabric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createPlans(String fabric, String state, String planType, String mountPoint) {
		Response resp = null;
	
	    if(fabric == null || fabric.isEmpty())
			throw new IllegalArgumentException("Fabric name is missing.");
	    if(mountPoint == null || mountPoint.isEmpty())
            throw new IllegalArgumentException("Mount Point is missing.");
	    
	    //encode mount point
	    String encodedMountPoint = encodeUrl("mountPoint='"+mountPoint+"'");
	    System.out.println("Encoded MountPoint: "+encodedMountPoint);
		
	    String path = URIConstants.CREATE_PLANS_URI;
		path = path.replaceFirst("<fabric>", fabric);
        client.header("Content-Type", "application/x-www-form-urlencoded");
        if(planType != null && !planType.isEmpty()){
            client.query("planType", planType);
        }
        if(state != null && !state.isEmpty()){
            client.query("state", state);
        }
        client.query("systemFilter", encodedMountPoint);
        resp =  post(path, "");
        if(resp != null){
            return extractLocationFromHeader(resp);
        }else{
            return "No response from orchestration Engine.";
        }
	}

	@Override
	public String getPlans(String fabric) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getPlanXmlDocument(String fabric, Integer planId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executePlan(String createdPlanUrl) {
        if(createdPlanUrl == null || createdPlanUrl.isEmpty())
            throw new IllegalArgumentException("Created Plan Url is missing.");
	    
	    String path = createdPlanUrl + "/execution";
	    System.out.println("Url for Plan execution: "+ path);
	    Response resp =  post(path, "");
	    if(resp != null){
            return extractLocationFromHeader(resp);
        }else{
            return "No response from orchestration Engine.";
        }
	}

	@Override
	public String getAllExecutedPlanStatus(String createdPlanUrl) {
	    if(createdPlanUrl == null || createdPlanUrl.isEmpty())
            throw new IllegalArgumentException("Created Plan Url is missing.");
        
        String path = createdPlanUrl + "/executions";
        System.out.println("Url to get all Plans for executions: "+ path);
        return get(path);
	}

	@Override
	public String getExecutedPlanStatus(String executedPlanUrl) {
	    if(executedPlanUrl == null || executedPlanUrl.isEmpty())
            throw new IllegalArgumentException("Executed Plan Url is missing.");
	   
	    Response resp =  head(executedPlanUrl);
	    if(resp!=null){
            int statusCode = resp.getStatus();
            String resultDesc = "";
            System.out.println("Status for "+executedPlanUrl+":"+ statusCode);
            switch(statusCode){
                case 200: resultDesc = "Requested was successfully.";
                    break;
                case 404: resultDesc = "There was no such execution.";
                    break;
                default:
                    resultDesc = "No response from orchestration Engine.";
            }
        return resultDesc;
        }else{
            return "No response from orchestration Engine.";
        }
	}

	@Override
	public Response getExecutionXmlDocument(String fabric, Integer planId,
			Integer executionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response abortsExecution(String fabric, Integer planId,
			Integer executionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getCurrentDeployments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getArchiveCurrentDeployments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getCurrentDeploymentDetails(Integer deploymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getCurrentDeploymentinfo(Integer deploymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response archiveCurrentDeployments(Integer deploymentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getArchivedDeploymentsCounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadModelInConsole(String fabric, String payload) {
		if(fabric == null || fabric.isEmpty() || payload == null || payload.isEmpty())
			throw new IllegalArgumentException("fabric name or payload is missing.");
		String path = URIConstants.LOAD_MODEL_URI;
		path = path.replaceFirst("<fabric>", fabric);
		Response resp = null;
		client.header("Content-Type", "text/json");
		resp =  post(path, payload);
		if(resp!=null){
			int statusCode = resp.getStatus();
			String resultDesc = "";
			System.out.println("Status for "+path+":"+ statusCode);
			switch(statusCode){
				case 201: resultDesc = "Requested Model loaded successfully.";
					break;
				case 204: resultDesc = "Requested model loaded successfully and is equal to the previous one.";
					break;
				case 400: resultDesc = "Requested model is not valid (should be a properly json formatted document).";
					break;
				case 404: resultDesc = "There was an error.";
					break;
				default:
					resultDesc = "No response from orchestration Engine.";
			}
		return resultDesc;
		}else{
			return "No response from orchestration Engine.";
		}
	}

	@Override
	public Response retrieveCurrentLoadedModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response retrieveCurrentLiveModelFromZK() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response retrieveDeltaBetweenStaticAndLiveModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response executeShellCommand(String agentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response retrieveCommandExecutionResult(Integer commandId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFabrics() {
		return get(URIConstants.FABRICS_URI);
	}

	@Override
	public Response getFabricsAndAssociatedAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * REST get method. * @param path
	 * @return
	 */
	private String get(String path){
		client.path(path).accept(MediaType.APPLICATION_JSON).query("prettyPrint", "true");
		Response resp = client.get();
		System.out.println("Status for "+path+":"+ resp.getStatus());
		return client.get(String.class);
	}
	
	/**
	 * REST POST method
	 * @param path
	 * @param payload
	 * @return
	 * @throws Throwable 
	 */
	private Response post(String path, String payload){
		client.path(path).accept(MediaType.APPLICATION_JSON).query("prettyPrint", "true");
		return client.post(payload);
	}
	
	/**
	 * REST HEAD method
	 * @param path
	 * @return
	 */
	private Response head(String path){
	    client.path(path).accept(MediaType.APPLICATION_JSON).query("prettyPrint", "true");  
	    return client.head();
	}
	
	/**
	 * Extract Location from Response Metadata Map
	 * @param response
	 * @return
	 */
	private String extractLocationFromHeader(Response response){
        MultivaluedMap<String, Object> multiMap = response.getMetadata();
        List<?> locationList = (ArrayList<?>)multiMap.get("Location");
        if(locationList != null && !locationList.isEmpty()){
           return (String)locationList.get(0);
        }else{
            return null;
        }
	}
	
	/**
	 * Encode an URL
	 * @param url
	 * @return encoded url string
	 */
	private String encodeUrl(String url){
	    try {
            String encodedUrl = URLEncoder.encode(url, "UTF-8");
            System.out.println("Encoded URL: "+ encodedUrl);
            return encodedUrl;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
	}
}
