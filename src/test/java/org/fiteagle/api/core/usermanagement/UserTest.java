package org.fiteagle.api.core.usermanagement;

import org.fiteagle.api.core.usermanagement.User.InValidAttributeException;
import org.fiteagle.api.core.usermanagement.User.NotEnoughAttributesException;
import org.fiteagle.api.core.usermanagement.User.Role;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {
  
  @Test(expected=NotEnoughAttributesException.class)
    public void testCreateUserWithoutPassword() {
      new User("test1", "test1", "test1", "test1@test.de", "test1", "", "", null);
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
    new User("test!", "test1", "test1", "test1@test.de", "test1", "weropj", "wrohpj", null);
  }
  
  @Test(expected=InValidAttributeException.class)
  public void testCreateUserWithInvalidEmail(){
    new User("test1", "test1", "test1", "te@st1@test.de", "test1", "weioparhg", "wrgoj", null);
  }
  
  @Test(expected=InValidAttributeException.class)
  public void testCreateUserWithInvalidEmail2(){
    new User("test1", "test1", "test1", "test1@testde", "test1", "eerhzrh", "worpgj", null);
  }
  
}
