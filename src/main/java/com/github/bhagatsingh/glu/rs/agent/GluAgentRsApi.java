package com.github.bhagatsingh.glu.rs.agent;
/**
 * 
 * @author Bhagat Singh
 *
 */
public interface GluAgentRsApi {
   
    /**
     * Description: Get process for an agent
     * URI: /process 
     * HTTP Method: GET
     * @return Response String
     */
	public String getProcess();
	
	/**
	 * URI: /<<mountpoint>>
	 * * HTTP Method: PUT
	 * @return
	 */
	public String installScript(String mountPoint, String payload);
	
}
