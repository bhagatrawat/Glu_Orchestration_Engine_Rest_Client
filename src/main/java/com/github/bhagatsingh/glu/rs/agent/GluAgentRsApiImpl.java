package com.github.bhagatsingh.glu.rs.agent;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;

import com.github.bhagatsingh.glu.rs.constants.URIConstants;
import com.github.bhagatsingh.glu.rs.utils.GluRestUtils;
/**
 * 
 * @author Bhagat Singh
 *
 */
public class GluAgentRsApiImpl implements GluAgentRsApi {

    WebClient client;

    /**
     * 
     * @param url
     * @param user
     * @param password
     */
    public GluAgentRsApiImpl(String url, String user, String password) {
        try{
            client = GluRestUtils.getWebClient(url, user, password);
        }catch(Exception exp){
            throw new RuntimeException(exp);
        }
    }

  
    public String getProcess() {
        String path = URIConstants.AGENT_PROCESS_URI;
        client.query("view", "children");
        return get(path);
    }


    public String installScript(String mountPoint, String payload) {
        return put(mountPoint, payload);
    }
  
    
    /**
     * REST get method. * @param path
     * 
     * @return
     */
    private String get(String path) {
        client.path(path).accept(MediaType.APPLICATION_JSON);
        return client.get(String.class);
    }

    private String put(String path, String payload){
        client.header("Content-Type", "text/json");
        client.path("/mountPoint").accept(MediaType.APPLICATION_JSON);
        Response response = client.put(payload);
        System.out.println("Reponse Code: "+response.getStatus());
        return "";
    }

}
