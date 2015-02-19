package org.fiteagle.api.core.usermanagement;

import java.io.IOException;

import org.fiteagle.api.core.Config;
import org.fiteagle.api.core.usermanagement.User.InValidAttributeException;
import org.fiteagle.api.core.usermanagement.User.NotEnoughAttributesException;
import org.fiteagle.api.core.usermanagement.User.Role;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserTest {
  
  private static ObjectMapper objectMapper;
  
  @BeforeClass
  public static void initSerializer() {
    objectMapper = new ObjectMapper();
  }
  
  @Test(expected=NotEnoughAttributesException.class)
    public void testCreateUserWithoutPassword() {
      new User("test1", "test1", "test1", "test1@test.de", "test1", null, "", "", null);
  }
	
  @Test
  public void testCreateDefaultUser(){
    User user = User.createDefaultUser("test1", "earohj", "eerah");
    Assert.assertEquals("default", user.getAffiliation());
  }
  
  @Test
  public void testCreateAdminUser(){
    User user = User.createAdminUser("admin", "wpakwaer", "wearho");
    Assert.assertEquals(Role.FEDERATION_ADMIN, user.getRole());
  }
  
  @Test(expected=InValidAttributeException.class)
  public void testCreateUserWithInvalidUsername(){
    new User("test!", "test1", "test1", "test1@test.de", "test1", null, "weropj", "wrohpj", null);
  }
  
  @Test(expected=InValidAttributeException.class)
  public void testCreateUserWithInvalidEmail(){
    new User("test1", "test1", "test1", "te@st1@test.de", "test1", null, "weioparhg", "wrgoj", null);
  }
  
  @Test(expected=InValidAttributeException.class)
  public void testCreateUserWithInvalidEmail2(){
    new User("test1", "test1", "test1", "test1@testde", "test1", null, "eerhzrh", "worpgj", null);
  }
  
  @Test
  public void testSerialization() throws IOException{
    User user = new User("test", "ewrg", "er", "er@fr.de", "erg", null, "erh", "erg", null);
    String json = objectMapper.writeValueAsString(user);
    User user2 = objectMapper.readValue(json, User.class);
    Assert.assertEquals(user, user2);
  }
  
  @Test
  public void testGetHostname(){
    String propertyValue = Config.getProperty("hostname");
    Assert.assertEquals("localhost", propertyValue);
  }
  
  @Test
  public void testConfigProperties(){
    Config.setProperty("Key", "value");
    String propertyValue = Config.getProperty("Key");
    Assert.assertEquals("value", propertyValue);
    Config.getAllProperties();
    Config.updateProperty("Key", "newValue");
    propertyValue = Config.getProperty("Key");
    Assert.assertEquals("newValue", propertyValue);
  }
  
}