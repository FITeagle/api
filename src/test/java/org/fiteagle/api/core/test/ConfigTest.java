package org.fiteagle.api.core.test;

import org.fiteagle.api.core.Config;
import org.junit.Test;

public class ConfigTest {
  
//  @Test
  public void testConfigProperties(){
    Config config = Config.getInstance();
    config.setDefaultProperty();
    config.setNewProperty("Key", "value");
    config.setNewProperty("anotherProperty", "anotherValue");
    
    System.out.println("printing all properties");
    config.getAllProperties();
    System.out.println("-------------");
    
    config.updateProperty("Key", "newValue");
    System.out.println("printing all values after update");
    config.getAllProperties();
    System.out.println("-------------");
    
    config.deleteProperty("Key");
    config.deleteProperty("anotherProperty");
    System.out.println("printing all values after delete");
    config.getAllProperties();
    System.out.println("-------------");

  }
  
}
