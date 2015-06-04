package org.fiteagle.api.core.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.fiteagle.api.core.Config;
import org.fiteagle.api.core.IConfig;
import org.fiteagle.api.core.OntologyModelUtil;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {
  
  @Test
  public void testConfigProperties(){
    Config config = new Config("test");
    config.createPropertiesFile();
    config.setNewProperty("testProperty", "testValue");
    Assert.assertEquals(config.getProperty("testProperty"), "testValue");
    
    config.updateProperty("testProperty", "newValue");
    Assert.assertEquals(config.getProperty("testProperty"), "newValue");
    
    config.deleteProperty("testProperty");
    config.deletePropertiesFile();
  }
 
  @Test
  public void testCreateNestedJson(){
    File file = new File(IConfig.PROPERTIES_DIRECTORY
        + "/TestAdapter.properties");
    if (!file.exists()) {
    Config config = new Config("TestAdapter");
    
    Map<String, Object> propertiesMap = new HashMap<String, Object>();
    propertiesMap.put(IConfig.KEY_HOSTNAME, IConfig.DEFAULT_HOSTNAME);
    propertiesMap.put(IConfig.LOCAL_NAMESPACE, IConfig.LOCAL_NAMESPACE_VALUE);
    propertiesMap.put(IConfig.RESOURCE_NAMESPACE,IConfig.RESOURCE_NAMESPACE_VALUE);
    
    List<Map<String, String>> adapterInstancesList = new LinkedList<Map<String, String>>();
    Map<String, String> adapterInstnaceMap = new HashMap<String, String>();
    adapterInstnaceMap.put("componentID", OntologyModelUtil.getResourceNamespace()+ "PhysicalNodeAdapter-1");
    adapterInstnaceMap.put("username", "");
    adapterInstnaceMap.put("privateKeyPassword", "");
    adapterInstnaceMap.put("password", "");
    
    adapterInstancesList.add(adapterInstnaceMap);
    
    propertiesMap.put("adapterInstances", adapterInstancesList);
    
    Properties property = new Properties();
    property.putAll(propertiesMap);
    config.writeProperties(property);
  }
  }
  
  @Test
  public void testParseNestedJson() throws IOException, JSONException{

    Config config = new Config("TestAdapter");
    String mResponse = config.readJsonProperties();

    System.out.println("mResponse" + mResponse);
    JSONObject jsonObject = new JSONObject(mResponse);

    String hostname = jsonObject.getString("hostname");
    System.out.println("value of hostname is " + hostname);
    
    JSONArray firstarray = jsonObject.getJSONArray("adapterInstances");
    
    for (int i = 0; i < firstarray.length(); i++) {
      JSONObject jonj = firstarray.getJSONObject(i);
      String value = jonj.getString("componentID");
      System.out.println(value);
    }

  }
  
  
}
