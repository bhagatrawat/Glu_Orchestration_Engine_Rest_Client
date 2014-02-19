package com.github.bhagatsingh.glu.rs.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;


import com.github.bhagatsingh.glu.proxy.GluProxy;

public class GluProxyTest {
    GluProxy proxy;

    public GluProxyTest() {
        proxy = new GluProxy(
                "http://localhost:8080/console/",
                "development", new HttpClient(), new PostMethod(), "admin","admin");
    }

    @Test
    public void testModel(){
		try {
		    String modelPayload = getPayload();
			String response = proxy.loadModel(modelPayload);
			System.out.println("Response: "+ response);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * getPayload method reads model from json file and return it as String.
     * @return
     */
    private String getPayload() {
        BufferedReader reader = null;
        try {
            File modelFile = new File("//nasprodpm/home/bsingh/glu/glu-5.4.1/models/glu-rest-test-model-bhagat.json");
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
