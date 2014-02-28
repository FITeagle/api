package org.fiteagle.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public interface User extends Serializable{
  
  public abstract void updateAttributes(String firstName, String lastName, String email, String affiliation,
      String password, List<UserPublicKey> publicKeys);
  
  public enum Role {
    ADMIN, USER
  }
  
  public abstract void addPublicKey(UserPublicKey publicKey);
  
  public abstract void deletePublicKey(String description);
  
  public abstract void renamePublicKey(String description, String newDescription);
  
  public abstract UserPublicKey getPublicKey(String description);
  
  public abstract String toString();
  
  public abstract boolean equals(Object obj);
  
  public abstract String getUsername();
  
  public abstract void setUsername(String username);
  
  public abstract String getFirstName();
  
  public abstract String getLastName();
  
  public abstract String getEmail();
  
  public abstract void setEmail(String email);
  
  public abstract String getAffiliation();
  
  public abstract Role getRole();
  
  public abstract void setRole(Role role);
  
  public abstract Date getCreated();
  
  public abstract Date getLastModified();
  
  public abstract String getPasswordHash();
  
  public abstract String getPasswordSalt();
  
  public abstract List<UserPublicKey> getPublicKeys();
  
  public abstract boolean hasKeyWithDescription(String description);
  
  public class PublicKeyNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4906415519200726744L;  
    
    public PublicKeyNotFoundException(){
      super("no public key with this description could be found in the database");
    }
  }

  public static class NotEnoughAttributesException extends RuntimeException {
    private static final long serialVersionUID = -8279867183643310351L;
    
    public NotEnoughAttributesException(){
      super();
    }
    
    public NotEnoughAttributesException(String message){
      super(message);
    }
  }

  public static class InValidAttributeException extends RuntimeException {
    private static final long serialVersionUID = -1299121776233955847L;
    
    public InValidAttributeException(){
      super();
    }
    
    public InValidAttributeException(String message){
      super(message);      
    }
  }
  
}