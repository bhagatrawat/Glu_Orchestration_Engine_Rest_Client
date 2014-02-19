/**
 * 
 */
package com.github.bhagatsingh.glu.rs.agent;


import org.junit.Test;

import com.github.bhagatsingh.glu.rs.agent.GluAgentRsApi;
import com.github.bhagatsingh.glu.rs.agent.GluAgentRsApiImpl;

/**
 * @author Bhagat Singh
 *
 */
public class GluAgentRsApiTest {

    @Test
    public void test() {
        //fail("Not yet implemented");
        try{
            GluAgentRsApi agent = new GluAgentRsApiImpl("http://localhost:2181/","admin","admin");
            String rsp = agent.getProcess();
            System.out.println(rsp);
        }catch(Exception exp){
            exp.printStackTrace();
        }
        
    }

}
