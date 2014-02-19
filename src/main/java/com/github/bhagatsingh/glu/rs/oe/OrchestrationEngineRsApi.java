package com.github.bhagatsingh.glu.rs.oe;

import javax.ws.rs.core.Response;

/**
 * The OrchestrationEngineRestApi class provides REST methods to access Orchestration Engine
 * @author Bhagat Singh
 *
 */
public interface OrchestrationEngineRsApi{
	
	/**
	 * Description: Returns details for fabric
	 * URI: /console/rest/v1/<fabric>
	 * HTTP Method: GET
	 * @param fabric
	 * @return String
	 */
	public String getFabric(String fabric);
	
	/**
	 * Description: Add/Update a fabric
	 * URI: /console/rest/v1/<fabric>
	 * HTTP Method: PUT
	 * @param fabric
	 * @return Response
	 */
	public Response getAddOrUpdateFabric(String fabric, String action, String message);
	
	/**
	 * Description: Delete a fabric
	 * URI: /console/rest/v1/<fabric>
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response deleteFabric(String fabric);

	/**
	 * Description: Returns the number of agents
	 * URI: /console/rest/v1/<fabric>/agents
	 * HTTP Method: HEAD
	 * @param fabric
	 * @return Response
	 */
	public Response getAgentsCount();
	
	/**
	 * Description: List all the agents
	 * URI: /console/rest/v1/<fabric>/agents
	 * HTTP Method: GET
	 * @param fabric
	 * @return String
	 */
	public String getAgentsDetails(String fabric);

	/**
	 * Description: View details about the agent
	 * URI: /console/rest/v1/<fabric>/agent/<agentName>
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public String getAgentDetails(String fabric, String agentName);
	
	/**
	 * Description: Remove all knowledge of an agent
	 * URI: /console/rest/v1/<fabric>/agent/<agentName>
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response removeAgent(String agentName);

	/**
	 * Description: Sets the fabric for the agent
	 * URI: /console/rest/v1/<fabric>/agent/<agentName>/fabric
	 * HTTP Method: PUT
	 * @param fabric
	 * @return Response
	 */
	public Response assignFabricToAnAgent(String agentName, String fabric);
	
	/**
	 * Description: Clears the fabric for the agent
	 * URI: /console/rest/v1/<fabric>/agent/<agentName>/fabric
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response removeFabricForAnAgent(String agentName, String fabric);

	/**
	 * Description: List all the agents versions
	 * URI: /console/rest/v1/<fabric>/agents/versions
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public String getAgentsVersions(String fabric);

	/**
	 * Description: Upgrade the agents
	 * URI: /console/rest/v1/<fabric>/agents/versions
	 * HTTP Method: POST
	 * @param fabric
	 * @return Response
	 */
	public Response upgradeAgents(String fabric);
	
	/**
	 * Description: List all the plans
	 * URI: /console/rest/v1/<fabric>/plans 
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public String getPlans(String fabric);
	
	/**
	 * Description: Create a plan 
	 * URI: /console/rest/v1/<fabric>/plans
	 * @param fabric
	 * @param state
	 * @param planType
	 * @param systemFilter
	 * @return String: created plan url
	 */
	public String createPlans(String fabric, String state, String planType, String systemFilter);
	
	/**
	 * Description: View the plan (as an xml document)
	 * URI: /console/rest/v1/<fabric> /plan/<planId>
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response getPlanXmlDocument(String fabric, Integer planId);
	
	/**
	 * Description: Executes the plan
	 * URI: /console/rest/v1/<fabric>/plan/<planId>/execution
	 * HTTP Method: POST
	 * @param createdPlanUrl
	 * @return String: Executed Plan Url
	 */
	public String executePlan(String createdPlanUrl);
	
	/**
	 * Description: List all the executions for a plan
	 * URI: /console/rest/v1/<fabric>/plan/<planId>/executions
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public String getAllExecutedPlanStatus(String executedPlanUrl);
	
	/**
	 * Description: Returns the status of the execution
	 * URI: /console/rest/v1/<fabric>/plan/<planId>/execution/<executionId>
	 * HTTP Method: HEAD
	 * @param executedPlanUrl
	 * @return String executed plan status
	 * 
	 */
	public String getExecutedPlanStatus(String executedPlanUrl);
	

	/**
	 * Description: Returns the status of the execution
	 * URI: /console/rest/v1/<fabric>/plan/<planId>/execution/<executionId>
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response getExecutionXmlDocument(String fabric, Integer planId, Integer executionId);

	/**
	 * Description: Aborts the execution
	 * URI: /console/rest/v1/<fabric>/plan/<planId>/execution/<executionId>
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response abortsExecution(String fabric, Integer planId, Integer executionId);
	
	/**
	 * Description: List all current deployments
	 * URI: /console/rest/v1/<fabric>/deployments/current
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response getCurrentDeployments();

	/**
	 * Description:Archive all current deployments
	 * URI: /console/rest/v1/<fabric>/deployments/current
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response getArchiveCurrentDeployments();

	/**
	 * Description: View details about the current deployment
	 * URI: /console/rest/v1/<fabric>/deployment/current/<deploymentId>
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response getCurrentDeploymentDetails(Integer deploymentId);
	
	/**
	 * Description: View info only about the current deployment
	 * URI: /console/rest/v1/<fabric>/deployment/current/<deploymentId>
	 * HTTP Method: HEAD
	 * @param fabric
	 * @return Response
	 */
	public Response getCurrentDeploymentinfo(Integer deploymentId);
	
	/**
	 * Description: Archive the current deployment
	 * URI: /console/rest/v1/<fabric>/deployments/current
	 * HTTP Method: DELETE
	 * @param fabric
	 * @return Response
	 */
	public Response archiveCurrentDeployments(Integer deploymentId);
	
	/**
	 * Description: Returns the total count of archived deployments
	 * URI: /console/rest/v1/<fabric>/deployment/archived/<deploymentId>
	 * HTTP Method: HEAD
	 * @param fabric
	 * @return Response
	 */
	public Response getArchivedDeploymentsCounts();

	/**
	 * Description: Loads the (desired) model in the console
	 * URI: /console/rest/v1/<fabric>/model/static
	 * HTTP Method: POST
	 * @param fabric
	 * @return Response
	 */
	public String loadModelInConsole(String fabric, String payload);
	

	/**
	 * Description: Retrieves the current loaded model (aka �desired� state)
	 * URI: /console/rest/v1/<fabric>/model/static
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response retrieveCurrentLoadedModel();
	
	
	/**
	 * Description: Retrieves the current live model coming from ZooKeeper (aka current state)
	 * URI: /console/rest/v1/<fabric>/model/live
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response retrieveCurrentLiveModelFromZK();
	
	/**
	 * Description: Retrieves the delta between static model and live model
	 * URI: /console/rest/v1/<fabric>/model/delta
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response retrieveDeltaBetweenStaticAndLiveModel();

	/**
	 * Description: Executes a shell command
	 * URI: /console/rest/v1/<fabric>/agent/<agentName>/commands
	 * HTTP Method: POST
	 * @param fabric
	 * @return Response
	 */
	public Response executeShellCommand(String agentName);
	
	
	/**
	 * Description: Retrieves the streams (=result) of the command execution
	 * URI: /console/rest/v1/<fabric>/command/<commandId>/streams
	 * HTTP Method: GET
	 * @param fabric
	 * @return Response
	 */
	public Response retrieveCommandExecutionResult(Integer commandId); 
	
	/**
	 * Returns the list of fabrics
	 * URI: /console/rest/v1/-
	 * HTTP Method: GET 
	 * @return Response
	 */
	public String getFabrics();
	
	/**
	 *Returns the map of associations agent -> fabric
	 * URI: /console/rest/v1/-/agents
	 * HTTP Method: GET
	 * @return Response
	 */
	public Response getFabricsAndAssociatedAgents();
	
}
