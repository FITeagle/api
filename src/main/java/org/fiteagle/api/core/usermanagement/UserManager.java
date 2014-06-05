package org.fiteagle.api.core.usermanagement;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.fiteagle.api.core.IMessageBus;
import org.fiteagle.api.core.usermanagement.User.PublicKeyNotFoundException;
import org.fiteagle.api.core.usermanagement.User.Role;

public interface UserManager {
  
  public static final String GET_ALL_USERS = "getAllUsers";
  public static final String GET_USER = "getUser";
  public static final String MESSAGE_FILTER = IMessageBus.TYPE_REQUEST + " != '" + null + "'";
  
  public abstract void add(User user);
  
  public abstract User getUser(User user) ;
  
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
  
  public static class DuplicateClassException extends RuntimeException {
	private static final long serialVersionUID = 7701360611614483760L;

	public DuplicateClassException(){
      super("this class has been already added to the user");
    }
  }
  
  public abstract Class addClass(String ownerUsername, Class targetClass);
  
  public abstract Class get(Class targetClass);
  
  public abstract Class get(long id);
  
  public abstract void delete(Class targetClass);
  
  public abstract void delete(long id);
  
  public abstract void addParticipant(long id, String username);
  
  public abstract void removeParticipant(long id, String username);

  public abstract List<Class> getAllClassesFromUser(String username);

  public abstract List<Class> getAllClassesOwnedByUser(String username);
  
  public abstract List<Class> getAllClasses();
  
  public static class CourseNotFoundException extends RuntimeException {    
    
    private static final long serialVersionUID = 5952413074712514371L;

    public CourseNotFoundException(){
      super("no course with this id could be found in the database");
    }
  }

  public abstract void deleteAllEntries();
  
}