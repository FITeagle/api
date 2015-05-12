package org.fiteagle.api.core.test;

import org.fiteagle.api.core.Config;
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
  
}
