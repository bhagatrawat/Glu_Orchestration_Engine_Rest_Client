package com.github.bhagatsingh.glu.rs.agent;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
//import java.net.URLEncoder;
import java.util.Properties;

import org.junit.Test;

import com.github.bhagatsingh.glu.rs.oe.OrchestrationEngineRsApi;
import com.github.bhagatsingh.glu.rs.oe.OrchestrationEngineRsApiImpl;

/**
 * @author Bhagat Singh
 *
 */
public class OrchestrationEngineRsApiTest{
	OrchestrationEngineRsApi orchEngine;
	String fabricName;
	String gluUser;
	String gluPassword;
	String domainUrl;
	String modelFileName;
	String modelFileLocation;
	String gluAgents;
	public OrchestrationEngineRsApiTest(){
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("glu-properties.properties");
		
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}

		gluUser = prop.getProperty("glu_user");
		gluPassword = prop.getProperty("glu_password");
		domainUrl = prop.getProperty("domain_url");
		fabricName = prop.getProperty("glu_fabric_name");
		modelFileLocation = prop.getProperty("glu_model_file_location");
	    modelFileName = prop.getProperty("glu_model_file_name");
	    gluAgents = prop.getProperty("glu_agents");
		if(modelFileLocation == null || modelFileLocation.isEmpty() || modelFileName==null || modelFileName.isEmpty()||
                domainUrl == null || domainUrl.isEmpty()  ||
                gluUser== null || gluUser.isEmpty() || gluPassword==null || gluPassword.isEmpty() || fabricName == null || fabricName.isEmpty() || gluAgents == null || gluAgents.isEmpty()){
            throw new IllegalArgumentException("The following value should be available in glu-properties.properties file to invoke Orchestration REST API:  [Model File Location, Model File Name, Domain URL, GLU User, GLU Password, Fabric Name, Agent Name]");
        }
		
		orchEngine = new OrchestrationEngineRsApiImpl(domainUrl, gluUser, gluPassword);
	}
	
	/*@Test
	public void testEncode(){
    	 try {
    	     String str4encode ="mountPoint='/sample/i001'";
             System.out.println("Apach encode: "+org.apache.cxf.common.util.Base64Utility.encode(str4encode.getBytes()));
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
	}*/
	
	
	@Test
    public void testFabric() {
        System.out.println("###################### FABRIC DETAILS ###################################");
        String fabricDetails = orchEngine.getFabric(fabricName);
        System.out.println("Fabric Details:\n" + fabricDetails);
    }
	
	
	@Test
    public void test() {
        System.out.println("###################### FABRICS DETAILS ###################################");
        String fabricDetails = orchEngine.getFabrics();
        System.out.println("Fabric Details:\n" + fabricDetails);
    }
	

    @Test
    public void testAgent() {
        System.out.println("################## Available Agents in Fabric########################## ");
        String agentsDetail = orchEngine.getAgentsDetails(fabricName);
        System.out.println("Agents Details:\n" + agentsDetail);
    }
    
	
	@Test
	public void testAgentDetails(){
		String agents[] = gluAgents.split(",");
		for(String agent: agents){
		    System.out.println("################## Agent "+agent+ " Detail ########################## ");
		    String agentsDetail = orchEngine.getAgentDetails(fabricName, agent);
    		System.out.println("Agent Details:\n" + agentsDetail);
		}
	}
	
	
	@Test
	public void testAgentsVersions(){
		System.out.println("################## Agents Versions ########################## ");
		String agentsDetail = orchEngine.getAgentsVersions(fabricName);
		System.out.println("Agents Versions:\n" + agentsDetail);
	}
	
	
	@Test
	public void testCreatePlan(){
	    
	    String state = null;//"running" //"stopped"
	    String planType = "bounce"; //"transition"
	    String mountPoint = "/sample/i001";
	    
	   // 1-Create Plan
	    System.out.println("################## Create Plans ########################## ");
	    String createdPlanUrl = orchEngine.createPlans(fabricName, state, planType, mountPoint);
		System.out.println("Create Plan Url:" + createdPlanUrl);
	
		
		// 2- Execute Plan
		System.out.println("################## Execute Plans ########################## ");
		String executedPlanUrl = orchEngine.executePlan(createdPlanUrl);
		System.out.println("Executed Plan Url:" + executedPlanUrl);
		
		
		if(planType.equalsIgnoreCase("bounce")){
            // Check Created Plans
            System.out.println("################## Check Executed Plan Status ########################## ");
            String createdPlans = orchEngine.getAllExecutedPlanStatus(createdPlanUrl);
            System.out.println("Execution Status: "+ createdPlans);
        }
		
		
		 // 3- Check Executed Plan Status
        System.out.println("################## Check Executed Plan Status ########################## ");
        String executionStatus = orchEngine.getExecutedPlanStatus(executedPlanUrl);
        System.out.println("Execution Status: "+ executionStatus); 
        
	}

	  //############################ un-comment it only when you want to load new model in console. ############################//

	   @Test
	    public void testLoadModel() {
	        String payload = getPayload();
	        System.out.println("##################### Model Payload ##########################");
	        System.out.println(payload);
	        System.out.println("###########################################################");
	        String agentsDetail = orchEngine.loadModelInConsole(fabricName, payload);
	        System.out.println("Load Current Model Response:\n" + agentsDetail);
	    }

	    /**
	     * getPayload method reads model from json file and return it as String.
	     * @return
	     */
	    private String getPayload() {
	        BufferedReader reader = null;
	        try {
	            File modelFile = new File(modelFileLocation+File.separator+modelFileName);
	            reader = new BufferedReader(new FileReader(modelFile));
	            String line = null;
	            StringBuilder stringBuilder = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                stringBuilder.append(line);
	            }
	            return stringBuilder.toString();
	        } catch (Throwable thr) {
	            thr.printStackTrace();
	            throw new RuntimeException(thr);
	        }finally{
	            if(reader!=null)
	                try {
	                    reader.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	        }

	    }
}
