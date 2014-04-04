package org.fiteagle.api.usermanagement;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.fiteagle.api.usermanagement.User.PublicKeyNotFoundException;
import org.fiteagle.api.usermanagement.User.Role;

public interface UserManager {
  
  public abstract void add(User user);
  
  public abstract User get(User user) ;
  
  public abstract User get(String username);
  
  public abstract void delete(User user);
  
  public abstract void delete(String username);
  
  public abstract void update(String username, String firstName, String lastName, String email, String affiliation,
      String password, List<UserPublicKey> publicKeys);
  
  public abstract void setRole(String username, Role role);
  
  public abstract void addKey(String username, UserPublicKey publicKey);
  
  public abstract void deleteKey(String username, String description);
  
  public abstract void renameKey(String username, String description, String newDescription);
  
  public abstract String createUserKeyPairAndCertificate(String username, String passphrase) throws Exception;
  
  public abstract String createUserCertificateForPublicKey(String username, String description) throws Exception, PublicKeyNotFoundException;
  
  public abstract boolean verifyCredentials(String username, String password) throws NoSuchAlgorithmException, IOException, UserNotFoundException;
  
  public abstract List<User> getAllUsers();
  
  public static class UserNotFoundException extends RuntimeException {    
    private static final long serialVersionUID = 2315125279537534064L;
    
    public UserNotFoundException(){
      super("no user with this username could be found in the database");
    }
  }
  

  public static class DuplicateUsernameException extends RuntimeException {
    private static final long serialVersionUID = -7242105025265481986L;   
    
    public DuplicateUsernameException(){
      super("another user with the same username already exists in the database");
    }
  }
  
  public static class DuplicateEmailException extends RuntimeException {
    private static final long serialVersionUID = 5986984055945876422L;
    
    public DuplicateEmailException(){
      super("another user with the same email already exists in the database");
    }
  }
  
  public static class DuplicatePublicKeyException extends RuntimeException {
    private static final long serialVersionUID = -8863826365649086008L; 
    
    public DuplicatePublicKeyException(){
      super("either this public key already exists or another public key with the same description already exists for this user");
    }
  }
  
  
}